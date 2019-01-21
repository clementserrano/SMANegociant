import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Negociant extends Agent implements Runnable {

    private List<Fournisseur> fournisseurs;
    private BoiteAuxLettres<Fournisseur> batFournisseurs;
    private BoiteAuxLettres<Negociant> batNegociants;

    private Lieu destinationSouhaitee;
    private Integer budgetSouhaitee;
    private Date dateAchatAuPlusTard;

    private Double valeurDepart;
    private Integer nbSoumission;
    private Integer nbSoumissionMax;
    private Double pourcentCroissance;
    private Double avantDerniereOffre;
    private Double derniereOffre;
    private Double derniereSoumission;

    public Negociant(Double valeurDepart, Integer nbSoumissionMax, Double pourcentCroissance) {
        fournisseurs = new ArrayList<>();
        batFournisseurs = BoiteAuxLettres.getBatFournisseur();
        nbSoumission = 0;
        this.nbSoumissionMax = nbSoumissionMax;
        this.pourcentCroissance = pourcentCroissance;
        derniereOffre = 0.0;
        avantDerniereOffre = 0.0;
        derniereSoumission = 0.0;
        this.valeurDepart = valeurDepart;
        batNegociants = BoiteAuxLettres.getBatNegociant();
    }

    @Override
    public void run() {

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
                            || billet.getPrix() > budgetSouhaitee) { // REFUSER
                        performatif.setAction(Action.REFUSE);
                    }

                    billet.setPrix(billet.getPrix() + 10);
                        performatif.setAction(Action.ACCEPT);
                    performatif.setBillet(billet);

                    batFournisseurs.poster((Fournisseur) message.getAgentEmetteur(), reponse);
                    break;
            }
        }
    }

    public Double calculerPrixRetour(Double price) {
        if (avantDerniereOffre == 0) {
            return valeurDepart;
        } else {
            Double ecart = avantDerniereOffre - derniereOffre;
            if (ecart > pourcentCroissance) {
                return price + derniereOffre * pourcentCroissance;
            } else {
                return
            }
        }
    }

    public List<Fournisseur> getFournisseurs() {
        return fournisseurs;
    }

    public void setFournisseurs(List<Fournisseur> fournisseurs) {
        this.fournisseurs = fournisseurs;
    }

    public BoiteAuxLettres<Fournisseur> getBatFournisseurs() {
        return batFournisseurs;
    }

    public void setBatFournisseurs(BoiteAuxLettres<Fournisseur> batFournisseurs) {
        this.batFournisseurs = batFournisseurs;
    }

    public Lieu getDestinationSouhaitee() {
        return destinationSouhaitee;
    }

    public void setDestinationSouhaitee(Lieu destinationSouhaitee) {
        this.destinationSouhaitee = destinationSouhaitee;
    }

    public Integer getBudgetSouhaitee() {
        return budgetSouhaitee;
    }

    public void setBudgetSouhaitee(Integer budgetSouhaitee) {
        this.budgetSouhaitee = budgetSouhaitee;
    }

    public Date getDateAchatAuPlusTard() {
        return dateAchatAuPlusTard;
    }

    public void setDateAchatAuPlusTard(Date dateAchatAuPlusTard) {
        this.dateAchatAuPlusTard = dateAchatAuPlusTard;
    }

    public Double getValeurDepart() {
        return valeurDepart;
    }

    public void setValeurDepart(Double valeurDepart) {
        this.valeurDepart = valeurDepart;
    }

    public BoiteAuxLettres<Negociant> getBatNegociants() {
        return batNegociants;
    }

    public void setBatNegociants(BoiteAuxLettres<Negociant> batNegociants) {
        this.batNegociants = batNegociants;
    }
}