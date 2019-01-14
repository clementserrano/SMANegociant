import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fournisseur extends Agent {

    private List<Negociant> negociants;
    private BoiteAuxLettres<Negociant> batNegociants;
    private String billet;
    private Date dateVenteAuPlusTard;
    private Date dateVenteSouhaitee;

    public Fournisseur() {
        negociants = new ArrayList<>();
        batNegociants = new BoiteAuxLettres<>();
    }

    public List<Negociant> getNegociants() {
        return negociants;
    }

    public void setNegociants(List<Negociant> negociants) {
        this.negociants = negociants;
    }

    public BoiteAuxLettres<Negociant> getBatNegociants() {
        return batNegociants;
    }

    public void setBatNegociants(BoiteAuxLettres<Negociant> batNegociants) {
        this.batNegociants = batNegociants;
    }

    public String getBillet() {
        return billet;
    }

    public void setBillet(String billet) {
        this.billet = billet;
    }

    public Date getDateVenteAuPlusTard() {
        return dateVenteAuPlusTard;
    }

    public void setDateVenteAuPlusTard(Date dateVenteAuPlusTard) {
        this.dateVenteAuPlusTard = dateVenteAuPlusTard;
    }

    public Date getDateVenteSouhaitee() {
        return dateVenteSouhaitee;
    }

    public void setDateVenteSouhaitee(Date dateVenteSouhaitee) {
        this.dateVenteSouhaitee = dateVenteSouhaitee;
    }
}
