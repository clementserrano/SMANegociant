import java.util.*;

public class Negociant extends Agent implements Runnable {

    private List<Fournisseur> fournisseurs;
    private BoiteAuxLettres batFournisseurs;
    private BoiteAuxLettres batNegociants;

    private Lieu departSouhaite;
    private Lieu destinationSouhaitee;
    private Double budgetSouhaiteeMax;
    private Double budgetSouhaiteeMin;
    private Date dateAchatAuPlusTard;

    private Integer nbSoumissionMax;
    private Double pourcentCroissance;

    private Map<Agent, Integer> nbSoumission;
    private Map<Agent, Double> avantDerniereOffre;
    private Map<Agent, Double> derniereOffre;
    private Map<Agent, Double> derniereSoumission;

    private Map<Agent, Billet> dernierBillet;

    private Billet billetAchete;

    public Negociant() {
        this.type = "negociant";
        fournisseurs = new ArrayList<>();
        nbSoumission = new HashMap<>();
        avantDerniereOffre = new HashMap<>();
        derniereOffre = new HashMap<>();
        derniereSoumission = new HashMap<>();
        dernierBillet = new HashMap<>();
        batFournisseurs = BoiteAuxLettres.getBatFournisseur();
        batNegociants = BoiteAuxLettres.getBatNegociant();
    }

    @Override
    public void run() {
        while (billetAchete == null) {
            recupererCourrier();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(getName() + " terminé");
    }

    public void recupererCourrier() {
        Message message = batNegociants.recuperer(this);
        if (message != null) {
            Agent fournisseur = message.getAgentEmetteur();

            if (nbSoumission.get(fournisseur) == null) nbSoumission.put(fournisseur, 0);
            if (avantDerniereOffre.get(fournisseur) == null) avantDerniereOffre.put(fournisseur, null);
            if (derniereOffre.get(fournisseur) == null) derniereOffre.put(fournisseur, null);
            if (derniereSoumission.get(fournisseur) == null) derniereSoumission.put(fournisseur, null);
            if (dernierBillet.get(fournisseur) == null) derniereSoumission.put(fournisseur, null);

            switch (message.getPerformatif().getAction()) {
                case OFFRE:
                    Message reponse = new Message();
                    reponse.setAgentEmetteur(this);
                    reponse.setAgentDestinataire(fournisseur);

                    Performatif performatif = new Performatif();
                    performatif.setDeadLine(Utils.datePlusDays(10));

                    Billet billet = message.getPerformatif().getBillet();
                    dernierBillet.put(fournisseur, billet);

                    if (!billet.getLieuArrivee().equals(destinationSouhaitee)
                            || nbSoumission.get(fournisseur) > nbSoumissionMax) {
                        performatif.setAction(Action.REFUSE);
                    } else {
                        avantDerniereOffre.put(fournisseur, derniereOffre.get(fournisseur));
                        derniereOffre.put(fournisseur, billet.getPrix());
                        derniereSoumission.put(fournisseur, calculerPrixRetour(billet.getPrix(), fournisseur));
                        if (derniereSoumission.get(fournisseur) < budgetSouhaiteeMin)
                            derniereSoumission.put(fournisseur, budgetSouhaiteeMin);
                        if (billet.getPrix() <= derniereSoumission.get(fournisseur)) {
                            derniereSoumission.put(fournisseur, billet.getPrix());
                            performatif.setAction(Action.ACCEPT);
                        } else {
                            billet.setPrix(derniereSoumission.get(fournisseur));
                            nbSoumission.put(fournisseur, nbSoumission.get(fournisseur) + 1);
                            performatif.setAction(Action.CONTRE_OFFRE);
                        }
                    }
                    performatif.setBillet(billet);
                    reponse.setPerformatif(performatif);
                    batFournisseurs.poster(fournisseur, reponse);
                    break;
                case VALIDER:
                    billetAchete = message.getPerformatif().getBillet();
                    derniereOffre.keySet().stream().filter(n -> !n.equals(message.getAgentEmetteur())).forEach(n -> {
                        Message refus = new Message();
                        refus.setAgentEmetteur(this);
                        refus.setAgentDestinataire(n);
                        Performatif p = new Performatif();
                        p.setAction(Action.REFUSE);
                        p.setDeadLine(Utils.datePlusDays(10));
                        p.setBillet(dernierBillet.get(n));
                        refus.setPerformatif(p);
                        batNegociants.poster(n, refus);
                    });
                    break;
                case REFUSE:
                    nbSoumission.put(fournisseur, 0);
                    avantDerniereOffre.put(fournisseur, null);
                    derniereOffre.put(fournisseur, null);
                    derniereSoumission.put(fournisseur, null);
                    break;
            }
        }
    }

    public Double calculerPrixRetour(Double prixRecu, Agent fournisseur) {
        if (prixRecu > budgetSouhaiteeMax) {
            return budgetSouhaiteeMax;
        }
        if (avantDerniereOffre.get(fournisseur) == null) {
            return budgetSouhaiteeMin;
        } else {
            Double lastSoumission = derniereSoumission.get(fournisseur);
            if (prixRecu < lastSoumission) return lastSoumission;
            Double ecart = avantDerniereOffre.get(fournisseur) - derniereOffre.get(fournisseur);
            if (ecart > lastSoumission * pourcentCroissance) {
                return lastSoumission + lastSoumission * pourcentCroissance;
            } else {
                return lastSoumission + ecart;
            }
        }
    }

    public Lieu getDestinationSouhaitee() {
        return destinationSouhaitee;
    }

    public void setDestinationSouhaitee(Lieu destinationSouhaitee) {
        this.destinationSouhaitee = destinationSouhaitee;
    }

    public void setBudgetSouhaiteeMin(Double budgetSouhaitee) {
        this.budgetSouhaiteeMin = budgetSouhaitee;
    }

    public void setBudgetSouhaiteeMax(Double budgetSouhaitee) {
        this.budgetSouhaiteeMax = budgetSouhaitee;
    }

    public Date getDateAchatAuPlusTard() {
        return dateAchatAuPlusTard;
    }

    public void setDateAchatAuPlusTard(Date dateAchatAuPlusTard) {
        this.dateAchatAuPlusTard = dateAchatAuPlusTard;
    }

    public void setNbSoumissionMax(Integer nbSoumissionMax) {
        this.nbSoumissionMax = nbSoumissionMax;
    }

    public void setPourcentCroissance(Double pourcentCroissance) {
        this.pourcentCroissance = pourcentCroissance;
    }

    public List<Fournisseur> getFournisseurs() {
        return fournisseurs;
    }

    public void setFournisseurs(List<Fournisseur> fournisseurs) {
        this.fournisseurs = fournisseurs;
    }

    public BoiteAuxLettres getBatFournisseurs() {
        return batFournisseurs;
    }

    public void setBatFournisseurs(BoiteAuxLettres batFournisseurs) {
        this.batFournisseurs = batFournisseurs;
    }

    public BoiteAuxLettres getBatNegociants() {
        return batNegociants;
    }

    public void setBatNegociants(BoiteAuxLettres batNegociants) {
        this.batNegociants = batNegociants;
    }

    public Double getBudgetSouhaiteeMax() {
        return budgetSouhaiteeMax;
    }

    public Double getBudgetSouhaiteeMin() {
        return budgetSouhaiteeMin;
    }

    public Integer getNbSoumissionMax() {
        return nbSoumissionMax;
    }

    public Double getPourcentCroissance() {
        return pourcentCroissance;
    }

    public Map<Agent, Integer> getNbSoumission() {
        return nbSoumission;
    }

    public void setNbSoumission(Map<Agent, Integer> nbSoumission) {
        this.nbSoumission = nbSoumission;
    }

    public Map<Agent, Double> getAvantDerniereOffre() {
        return avantDerniereOffre;
    }

    public void setAvantDerniereOffre(Map<Agent, Double> avantDerniereOffre) {
        this.avantDerniereOffre = avantDerniereOffre;
    }

    public Map<Agent, Double> getDerniereOffre() {
        return derniereOffre;
    }

    public void setDerniereOffre(Map<Agent, Double> derniereOffre) {
        this.derniereOffre = derniereOffre;
    }

    public Map<Agent, Double> getDerniereSoumission() {
        return derniereSoumission;
    }

    public void setDerniereSoumission(Map<Agent, Double> derniereSoumission) {
        this.derniereSoumission = derniereSoumission;
    }

    public Billet getBilletAchete() {
        return billetAchete;
    }

    public void setBilletAchete(Billet billetAchete) {
        this.billetAchete = billetAchete;
    }

    public Lieu getDepartSouhaite() {
        return departSouhaite;
    }

    public void setDepartSouhaite(Lieu departSouhaite) {
        this.departSouhaite = departSouhaite;
    }
}
