import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Negociant extends Agent implements Runnable {

    private List<Fournisseur> fournisseurs;
    private BoiteAuxLettres batFournisseurs;
    private BoiteAuxLettres batNegociants;

    private Lieu destinationSouhaitee;
    private Double budgetSouhaiteeMax;
    private Double budgetSouhaiteeMin;
    private Date dateAchatAuPlusTard;

    private Double valeurDepart;
    private Integer nbSoumission;
    private Integer nbSoumissionMax;
    private Double pourcentCroissance;
    private Double avantDerniereOffre;
    private Double derniereOffre;
    private Double derniereSoumission;

    private Billet billetAchete;

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
                            || billet.getPrix() > budgetSouhaiteeMax) {
                        performatif.setAction(Action.REFUSE);
                    } else {
                        avantDerniereOffre = derniereOffre;
                        derniereOffre = billet.getPrix();
                        derniereSoumission = calculerPrixRetour(billet.getPrix());
                        if (derniereSoumission < budgetSouhaiteeMin) derniereSoumission = budgetSouhaiteeMin;
                        if (billet.getPrix() <= derniereSoumission) {
                            performatif.setAction(Action.ACCEPT);
                        } else {
                            billet.setPrix(derniereSoumission);
                            nbSoumission++;
                            performatif.setAction(Action.CONTRE_OFFRE);
                        }
                    }
                    performatif.setBillet(billet);
                    reponse.setPerformatif(performatif);
                    System.out.println(reponse);
                    batFournisseurs.poster(message.getAgentEmetteur(), reponse);
                    break;
                case VALIDER:
                    billetAchete = message.getPerformatif().getBillet();
                    break;
            }
        }
    }

    public Double calculerPrixRetour(Double prixRecu) {
        if (avantDerniereOffre == 0) {
            return valeurDepart;
        } else {
            Double ecart = avantDerniereOffre - derniereOffre;
            if (ecart > pourcentCroissance) {
                return prixRecu + derniereOffre * pourcentCroissance;
            } else {
                return derniereSoumission + ecart;
            }
        }
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

    public Lieu getDestinationSouhaitee() {
        return destinationSouhaitee;
    }

    public void setDestinationSouhaitee(Lieu destinationSouhaitee) {
        this.destinationSouhaitee = destinationSouhaitee;
    }

    public Double getBudgetSouhaiteeMin() {
        return budgetSouhaiteeMin;
    }

    public void setBudgetSouhaiteeMin(Double budgetSouhaitee) {
        this.budgetSouhaiteeMin = budgetSouhaitee;
    }

    public Double getBudgetSouhaiteeMax() {
        return budgetSouhaiteeMax;
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

    public Double getValeurDepart() {
        return valeurDepart;
    }

    public void setValeurDepart(Double valeurDepart) {
        this.valeurDepart = valeurDepart;
    }

    public BoiteAuxLettres getBatNegociants() {
        return batNegociants;
    }

    public void setBatNegociants(BoiteAuxLettres batNegociants) {
        this.batNegociants = batNegociants;
    }
}
