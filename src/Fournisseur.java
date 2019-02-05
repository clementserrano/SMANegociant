import java.util.*;

public class Fournisseur extends Agent implements Runnable {

    private List<Negociant> negociants;
    private BoiteAuxLettres batNegociants;
    private BoiteAuxLettres batFournisseurs;
    private Billet billet;
    private Date dateVenteAuPlusTard;
    private Date dateVenteSouhaitee;
    private Double valeurDepart;
    private Double prixMin;

    private Map<Agent, Double> avantDerniereOffre;
    private Map<Agent, Double> derniereOffre;
    private Map<Agent, Billet> derniereOffreBillet;
    private Map<Agent, Double> derniereSoumission;
    private Map<Agent, Double> propositionFinale;

    private Long startCountdown;

    List<Negociant> negociantList;

    public Fournisseur() {
        this.type = "fournisseur";
        negociants = new ArrayList<>();
        batNegociants = BoiteAuxLettres.getBatNegociant();
        batFournisseurs = BoiteAuxLettres.getBatFournisseur();
        avantDerniereOffre = new HashMap<>();
        derniereOffre = new HashMap<>();
        derniereSoumission = new HashMap<>();
        negociantList = new ArrayList<>();
        propositionFinale = new HashMap<>();
        derniereOffreBillet = new HashMap<>();
    }

    @Override
    public void run() {
        proposeOffre();
        while (billet != null) {
            recupererCourrier();
            if (propositionFinale.size() > 0) {
                decisionFinale();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(getName() + " terminé");
    }

    public void proposeOffre() {
        List<Negociant> negociants = new ArrayList<>(this.negociants);

        negociantList = negociants;
        negociants.stream().forEach(negociant -> {
            Message message = new Message();
            message.setAgentDestinataire(negociant);
            message.setAgentEmetteur(this);

            Performatif performatif = new Performatif();
            performatif.setAction(Action.OFFRE);
            billet = new Billet(billet, valeurDepart);
            derniereSoumission.put(negociant, valeurDepart);
            performatif.setBillet(new Billet(billet));
            performatif.setDeadLine(Utils.datePlusDays(10));

            message.setPerformatif(performatif);
            batNegociants.poster(negociant, message, true);
        });

    }

    public void recupererCourrier() {
        Message message = batFournisseurs.recuperer(this);
        if (message != null) {
            Billet billet = message.getPerformatif().getBillet();
            Agent negociant = message.getAgentEmetteur();

            switch (message.getPerformatif().getAction()) {
                case ACCEPT:
                    if (derniereSoumission.get(negociant).equals(billet.getPrix())) {
                        propositionFinale.put(negociant, billet.getPrix());
                        derniereOffreBillet.put(negociant, billet);
                    }
                    break;
                case CONTRE_OFFRE:
                    Message reponse = new Message();
                    reponse.setAgentEmetteur(this);
                    reponse.setAgentDestinataire(message.getAgentEmetteur());

                    Performatif performatif = new Performatif();
                    performatif.setDeadLine(Utils.datePlusDays(10));
                    avantDerniereOffre.put(negociant, derniereOffre.get(negociant));
                    derniereOffre.put(negociant, billet.getPrix());
                    derniereOffreBillet.put(negociant, new Billet(billet));
                    derniereSoumission.put(negociant, calculerPrixRetour(negociant, billet.getPrix()));
                    billet = new Billet(billet, derniereSoumission.get(negociant));
                    performatif.setBillet(billet);
                    performatif.setAction(Action.OFFRE);
                    reponse.setPerformatif(performatif);
                    batNegociants.poster(negociant, reponse, true);
                    break;
                case REFUSE:
                    derniereOffre.remove(negociant);
                    break;
            }
        }
    }

    public Double calculerPrixRetour(Agent negociant, Double prixRecu) {
        if (avantDerniereOffre.get(negociant) == null) {
            return (valeurDepart - prixRecu) * 0.9;
        } else {
            Double ecart = derniereOffre.get(negociant) - avantDerniereOffre.get(negociant);
            if (prixMin < derniereSoumission.get(negociant) - ecart) {
                return derniereSoumission.get(negociant) - ecart;
            } else {
                return prixMin;
            }
        }
    }

    public void decisionFinale() {
        if (startCountdown == null) {
            startCountdown = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - startCountdown > 2000) {
            // Choix
            Agent meilleur = propositionFinale.entrySet().stream()
                    .max(Map.Entry.comparingByValue()).get().getKey();

            Message valid = new Message();
            valid.setAgentEmetteur(this);
            valid.setAgentDestinataire(meilleur);

            Performatif performatif = new Performatif();
            performatif.setDeadLine(Utils.datePlusDays(10));
            performatif.setBillet(new Billet(derniereOffreBillet.get(meilleur)));
            performatif.setAction(Action.VALIDER);
            valid.setPerformatif(performatif);
            batNegociants.poster(valid.getAgentDestinataire(), valid, true);

            derniereOffre.keySet().stream().filter(n -> !n.equals(meilleur)).forEach(n -> {
                Message refus = new Message();
                refus.setAgentEmetteur(this);
                refus.setAgentDestinataire(n);
                Performatif p = new Performatif();
                p.setAction(Action.REFUSE);
                p.setDeadLine(Utils.datePlusDays(10));
                p.setBillet(new Billet(derniereOffreBillet.get(n)));
                refus.setPerformatif(p);
                batNegociants.poster(n, refus, true);
            });

            this.billet = null;
        }
    }

    public List<Negociant> getNegociants() {
        return negociants;
    }

    public void setBillet(Billet billet) {
        this.billet = billet;
    }

    public void setDateVenteAuPlusTard(Date dateVenteAuPlusTard) {
        this.dateVenteAuPlusTard = dateVenteAuPlusTard;
    }

    public void setValeurDepart(Double valeurDepart) {
        this.valeurDepart = valeurDepart;
    }

    public void setPrixMin(Double prixMin) {
        this.prixMin = prixMin;
    }

    public void setNegociants(List<Negociant> negociants) {
        this.negociants = negociants;
    }

    public BoiteAuxLettres getBatNegociants() {
        return batNegociants;
    }

    public void setBatNegociants(BoiteAuxLettres batNegociants) {
        this.batNegociants = batNegociants;
    }

    public BoiteAuxLettres getBatFournisseurs() {
        return batFournisseurs;
    }

    public void setBatFournisseurs(BoiteAuxLettres batFournisseurs) {
        this.batFournisseurs = batFournisseurs;
    }

    public Billet getBillet() {
        return billet;
    }

    public Date getDateVenteAuPlusTard() {
        return dateVenteAuPlusTard;
    }

    public Date getDateVenteSouhaitee() {
        return dateVenteSouhaitee;
    }

    public void setDateVenteSouhaitee(Date dateVenteSouhaitee) {
        this.dateVenteSouhaitee = dateVenteSouhaitee;
    }

    public Double getValeurDepart() {
        return valeurDepart;
    }

    public Double getPrixMin() {
        return prixMin;
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
}
