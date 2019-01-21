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

    private Integer valeurDepart;
    private Integer frequenceSoumission;

    public Negociant() {
        fournisseurs = new ArrayList<>();
        batFournisseurs = BoiteAuxLettres.getBatFournisseur();
    }

    public void recupererCourrier() {

    }

    public void calculerFrequenceSoumission() {

    }

    public void calculerCroissanceOffre() {

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
