import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Negociant extends Agent implements Runnable {

    private List<Fournisseur> fournisseurs;
    private BoiteAuxLettres batFournisseurs;
    private BoiteAuxLettres batNegociants;

    private Lieu departSouhaite;
    private Lieu destinationSouhaitee;
    private Double budgetSouhaiteeMax;
    private Double budgetSouhaiteeMin;
    private Date dateAchatAuPlusTard;

    private Integer nbSoumission;
    private Integer nbSoumissionMax;
    private Double pourcentCroissance;
    private Double avantDerniereOffre;
    private Double derniereOffre;
    private Double derniereSoumission;

    private Billet billetAchete;

    public Negociant() {
        fournisseurs = new ArrayList<>();
        nbSoumission = 0;
        batFournisseurs = BoiteAuxLettres.getBatFournisseur();
        batNegociants = BoiteAuxLettres.getBatNegociant();
    }

    @Override
    public void run() {
        while (billetAchete == null) {
            recupererCourrier();
        }
    }

    public void recupererCourrier() {
        Message message = batNegociants.recuperer(this);

        if (message != null) {
            Message reponse = new Message();
            reponse.setAgentEmetteur(this);
            reponse.setAgentDestinataire(message.getAgentEmetteur());

            Performatif performatif = new Performatif();
            performatif.setDeadLine(Utils.datePlusDays(10));

            switch (message.getPerformatif().getAction()) {
                case CFP:
                    Billet billet = message.getPerformatif().getBillet();
                    if (!billet.getLieuArrivee().equals(destinationSouhaitee)
                            || billet.getPrix() > budgetSouhaiteeMax
                            || nbSoumission > nbSoumissionMax) {
                        performatif.setAction(Action.REFUSE);
                    } else {
                        avantDerniereOffre = derniereOffre;
                        derniereOffre = billet.getPrix();
                        derniereSoumission = calculerPrixRetour(billet.getPrix());
                        if (derniereSoumission < budgetSouhaiteeMin) derniereSoumission = budgetSouhaiteeMin;
                        if (billet.getPrix() <= derniereSoumission) {
                            derniereSoumission = billet.getPrix();
                            performatif.setAction(Action.ACCEPT);
                        } else {
                            billet.setPrix(derniereSoumission);
                            nbSoumission++;
                            performatif.setAction(Action.CONTRE_OFFRE);
                        }
                    }
                    performatif.setBillet(billet);
                    reponse.setPerformatif(performatif);
                    batFournisseurs.poster(message.getAgentEmetteur(), reponse);
                    break;
                case VALIDER:
                    billetAchete = message.getPerformatif().getBillet();
                    break;
            }
        }
    }

    public Double calculerPrixRetour(Double prixRecu) {
        if (avantDerniereOffre == null) {
            return budgetSouhaiteeMin;
        } else {
            if (prixRecu < derniereSoumission) return derniereSoumission;
            Double ecart = avantDerniereOffre - derniereOffre;
            if (ecart > derniereSoumission * pourcentCroissance) {
                return derniereSoumission + derniereSoumission * pourcentCroissance;
            } else {
                return derniereSoumission + ecart;
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

    public Integer getNbSoumission() {
        return nbSoumission;
    }

    public void setNbSoumission(Integer nbSoumission) {
        this.nbSoumission = nbSoumission;
    }

    public Integer getNbSoumissionMax() {
        return nbSoumissionMax;
    }

    public Double getPourcentCroissance() {
        return pourcentCroissance;
    }

    public Double getAvantDerniereOffre() {
        return avantDerniereOffre;
    }

    public void setAvantDerniereOffre(Double avantDerniereOffre) {
        this.avantDerniereOffre = avantDerniereOffre;
    }

    public Double getDerniereOffre() {
        return derniereOffre;
    }

    public void setDerniereOffre(Double derniereOffre) {
        this.derniereOffre = derniereOffre;
    }

    public Double getDerniereSoumission() {
        return derniereSoumission;
    }

    public void setDerniereSoumission(Double derniereSoumission) {
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
