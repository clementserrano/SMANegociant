import java.util.*;
import java.util.stream.Collectors;

public class Fournisseur extends Agent {

    private List<Negociant> negociants;
    private BoiteAuxLettres<Negociant> batNegociants;
    private Billet billet;
    private Date dateVenteAuPlusTard;
    private Date dateVenteSouhaitee;

    public Fournisseur() {
        negociants = new ArrayList<>();
        batNegociants = BoiteAuxLettres.getBatNegociant();
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
            performatif.setBillet(this.billet);

            Date deadline = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(deadline);
            calendar.add(Calendar.DATE, 10); // 10 jours
            deadline = calendar.getTime();

            performatif.setDeadLine(deadline);

            message.setPerformatif(performatif);

            batNegociants.poster(negociant, message);
            return null;
        });

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
}
