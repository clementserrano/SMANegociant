import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Fournisseur extends Agent implements Runnable {

    private List<Negociant> negociants;
    private BoiteAuxLettres batNegociants;
    private BoiteAuxLettres batFournisseurs;
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
        proposeOffre();
        while (billet != null) {
            recupererCourrier();
        }
    }

    public void proposeOffre() {
        List<Negociant> negociantsInterresses = negociants.stream()
                .filter(negociant ->
                        negociant.getDestinationSouhaitee().equals(billet.getLieuArrivee())
                                && negociant.getDepartSouhaite().equals(billet.getLieuDepart())
                                && negociant.getDateAchatAuPlusTard().before(dateVenteAuPlusTard))
                .collect(Collectors.toList());

        negociantsInterresses.stream().forEach(negociant -> {
            Message message = new Message();
            message.setAgentDestinataire(negociant);
            message.setAgentEmetteur(this);

            Performatif performatif = new Performatif();
            performatif.setAction(Action.CFP);
            billet.setPrix(valeurDepart);
            derniereSoumission = valeurDepart;
            performatif.setBillet(billet);
            performatif.setDeadLine(Utils.datePlusDays(10));

            message.setPerformatif(performatif);
            batNegociants.poster(negociant, message);
        });

    }

    public void recupererCourrier() {
        Message message = batFournisseurs.recuperer(this);
        if (message != null) {
            Message reponse = new Message();
            reponse.setAgentEmetteur(this);
            reponse.setAgentDestinataire(message.getAgentEmetteur());

            Performatif performatif = new Performatif();
            performatif.setDeadLine(Utils.datePlusDays(10));
            Billet billet = message.getPerformatif().getBillet();

            switch (message.getPerformatif().getAction()) {
                case ACCEPT:
                    performatif.setBillet(billet);
                    if (derniereOffre == billet.getPrix()) {
                        performatif.setAction(Action.VALIDER);
                        batNegociants.poster(message.getAgentEmetteur(), reponse);
                        this.billet = null;
                    }
                    break;
                case CONTRE_OFFRE:
                    avantDerniereOffre = derniereOffre;
                    derniereOffre = billet.getPrix();
                    derniereSoumission = calculerPrixRetour(billet.getPrix());
                    billet.setPrix(derniereSoumission);
                    performatif.setBillet(billet);
                    performatif.setAction(Action.CFP);
                    reponse.setPerformatif(performatif);
                    batNegociants.poster(message.getAgentEmetteur(), reponse);
                    break;
                case REFUSE:
                    break;
            }
        }
    }

    public Double calculerPrixRetour(Double prixRecu) {
        if (avantDerniereOffre == null) {
            return (valeurDepart - prixRecu) * 0.9;
        } else {
            Double ecart = derniereOffre - avantDerniereOffre;
            if (prixMin < derniereSoumission - ecart) {
                return derniereSoumission - ecart;
            } else {
                return prixMin;
            }
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
}
