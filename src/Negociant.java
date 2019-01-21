import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Negociant extends Agent {

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
    }

    public void recupererCourrier() {

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

    public Integer getValeurDepart() {
        return valeurDepart;
    }

    public void setValeurDepart(Integer valeurDepart) {
        this.valeurDepart = valeurDepart;
    }

    public Integer getFrequenceSoumission() {
        return frequenceSoumission;
    }

    public void setFrequenceSoumission(Integer frequenceSoumission) {
        this.frequenceSoumission = frequenceSoumission;
    }

    public BoiteAuxLettres<Negociant> getBatNegociants() {
        return batNegociants;
    }

    public void setBatNegociants(BoiteAuxLettres<Negociant> batNegociants) {
        this.batNegociants = batNegociants;
    }
}
