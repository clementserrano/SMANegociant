import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Fournisseur extends Agent implements Runnable {

    private List<Negociant> negociants;
    private BoiteAuxLettres<Negociant> batNegociants;
    private BoiteAuxLettres<Fournisseur> batFournisseurs;
    private Billet billet;
    private Date dateVenteAuPlusTard;
    private Date dateVenteSouhaitee;
    private Double valeurDepart;
    private Double prixMin;
    private Double avantDerniereOffre;
    private Double derniereOffre;
    private Double derniereSoumission;

    public Fournisseur() {
        negociants = new ArrayList<>();
        batNegociants = BoiteAuxLettres.getBatNegociant();
        batFournisseurs = BoiteAuxLettres.getBatFournisseur();
    }

    @Override
    public void run() {
        while(billet != null){
            proposeOffre();
        }
    }

    public void proposeOffre() {
        List<Negociant> negociantsInterresses = negociants.stream()
                .filter(negociant ->
                        negociant.getDestinationSouhaitee().equals(billet.getLieuArrivee())
                                && negociant.getDateAchatAuPlusTard().before(dateVenteAuPlusTard))
                .sorted(Comparator.comparing(Negociant::getBudgetSouhaitee))
                .collect(Collectors.toList());

        negociantsInterresses.stream().map(negociant -> {
            Message message = new Message();
            message.setAgentDestinataire(negociant);
            message.setAgentEmetteur(this);

            Performatif performatif = new Performatif();
            performatif.setAction(Action.CFP);
            billet.setPrix(valeurDepart);
            performatif.setBillet(billet);
            performatif.setDeadLine(Utils.datePlusDays(10));

            message.setPerformatif(performatif);
            batNegociants.poster(negociant, message);
            return null;
        });

    }

    public Double calculerPrixRetour(Double prixRecu) {
        if (avantDerniereOffre == 0) {
            return (valeurDepart - prixRecu) * 0.9;
        } else {
            Double ecart = avantDerniereOffre - derniereOffre;
            if (prixMin > derniereOffre - ecart) {
                return derniereOffre - ecart;
            } else {
                return prixMin;
            }
        }
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

    public Billet getBillet() {
        return billet;
    }

    public void setBillet(Billet billet) {
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

    public BoiteAuxLettres<Fournisseur> getBatFournisseurs() {
        return batFournisseurs;
    }

    public void setBatFournisseurs(BoiteAuxLettres<Fournisseur> batFournisseurs) {
        this.batFournisseurs = batFournisseurs;
    }

    public Double getValeurDepart() {
        return valeurDepart;
    }

    public void setValeurDepart(Double valeurDepart) {
        this.valeurDepart = valeurDepart;
    }

    public Double getPrixMin() {
        return prixMin;
    }

    public void setPrixMin(Double prixMin) {
        this.prixMin = prixMin;
    }
}
