import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Negociant extends Agent {

    private List<Fournisseur> fournisseurs;
    private BoiteAuxLettres<Fournisseur> batFournisseurs;

    private Lieu destinationSouhaitee;
    private int budgetSouhaitee;
    private Date dateAchatAuPlusTard;
    private int valeurDepart;

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

    public int getBudgetSouhaitee() {
        return budgetSouhaitee;
    }

    public void setBudgetSouhaitee(int budgetSouhaitee) {
        this.budgetSouhaitee = budgetSouhaitee;
    }

    public Date getDateAchatAuPlusTard() {
        return dateAchatAuPlusTard;
    }

    public void setDateAchatAuPlusTard(Date dateAchatAuPlusTard) {
        this.dateAchatAuPlusTard = dateAchatAuPlusTard;
    }

    public int getValeurDepart() {
        return valeurDepart;
    }

    public void setValeurDepart(int valeurDepart) {
        this.valeurDepart = valeurDepart;
    }

    public int getFrequenceSoumission() {
        return frequenceSoumission;
    }

    public void setFrequenceSoumission(int frequenceSoumission) {
        this.frequenceSoumission = frequenceSoumission;
    }

    private int frequenceSoumission;

    public Negociant() {
        fournisseurs = new ArrayList<>();
        batFournisseurs = new BoiteAuxLettres<>();
    }

    public void calculerFrequenceSoumission() {

    }

    public void calculerCroissanceOffre() {

    }
}
