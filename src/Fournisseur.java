import java.util.*;
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

    private Map<Agent, Double> avantDerniereOffre;
    private Map<Agent, Double> derniereOffre;
    private Map<Agent, Double> derniereSoumission;
    private Map<Agent, Double> propositionFinale;

    private Long startCountdown;

    List<Negociant> negociantList;

    public Fournisseur() {
        negociants = new ArrayList<>();
        batNegociants = BoiteAuxLettres.getBatNegociant();
        batFournisseurs = BoiteAuxLettres.getBatFournisseur();
        avantDerniereOffre = new HashMap<>();
        derniereOffre = new HashMap<>();
        derniereSoumission = new HashMap<>();
        negociantList = new ArrayList<>();
        propositionFinale = new HashMap<>();
    }

    @Override
    public void run() {
        proposeOffre();
        while (billet != null) {
            recupererCourrier();
            if (propositionFinale.size() > 0) {
                decisionFinale();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fournisseur terminé");
    }

    public void proposeOffre() {
        List<Negociant> negociantsInterresses = negociants.stream()
                .filter(negociant ->
                        negociant.getDestinationSouhaitee().equals(billet.getLieuArrivee())
                                && negociant.getDepartSouhaite().equals(billet.getLieuDepart())
                                && negociant.getDateAchatAuPlusTard().before(dateVenteAuPlusTard))
                .collect(Collectors.toList());

        negociantList = negociantsInterresses;
        negociantsInterresses.stream().forEach(negociant -> {
            Message message = new Message();
            message.setAgentDestinataire(negociant);
            message.setAgentEmetteur(this);

            Performatif performatif = new Performatif();
            performatif.setAction(Action.OFFRE);
            billet.setPrix(valeurDepart);
            derniereSoumission.put(negociant, valeurDepart);
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
            billet = message.getPerformatif().getBillet();

            Agent negociant = message.getAgentEmetteur();

            switch (message.getPerformatif().getAction()) {
                case ACCEPT:
                    //performatif.setBillet(billet);
                    if (derniereSoumission.get(negociant) == billet.getPrix()) {
                        propositionFinale.put(negociant, billet.getPrix());
                        /*performatif.setAction(Action.VALIDER);
                        reponse.setPerformatif(performatif);
                        batNegociants.poster(negociant, reponse);
                        this.billet = null;
                        negociantList.stream().filter(n -> !n.equals(negociant)).forEach(n -> {
                            Message refus = new Message();
                            refus.setAgentEmetteur(this);
                            refus.setAgentDestinataire(n);
                            Performatif p = new Performatif();
                            p.setAction(Action.REFUSE);
                            p.setDeadLine(Utils.datePlusDays(10));
                            p.setBillet(billet);
                            refus.setPerformatif(p);
                            batNegociants.poster(n, refus);
                        });*/
                    }
                    break;
                case CONTRE_OFFRE:
                    avantDerniereOffre.put(negociant, derniereOffre.get(negociant));
                    derniereOffre.put(negociant, billet.getPrix());
                    derniereSoumission.put(negociant, calculerPrixRetour(negociant, billet.getPrix()));
                    billet.setPrix(derniereSoumission.get(negociant));
                    performatif.setBillet(billet);
                    performatif.setAction(Action.OFFRE);
                    reponse.setPerformatif(performatif);
                    batNegociants.poster(message.getAgentEmetteur(), reponse);
                    break;
                case REFUSE:
                    break;
            }
        }
    }

    public Double calculerPrixRetour(Agent negociant, Double prixRecu) {
        if (avantDerniereOffre.get(negociant) == null) {
            return (valeurDepart - prixRecu) * 0.9;
        } else {
            Double ecart = derniereOffre.get(negociant) - avantDerniereOffre.get(negociant);
            if (prixMin < derniereSoumission.get(negociant) - ecart) {
                return derniereSoumission.get(negociant) - ecart;
            } else {
                return prixMin;
            }
        }
    }

    public void decisionFinale() {
        if (startCountdown == null) {
            startCountdown = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - startCountdown > 2000) {
            // Choix
            Map<Agent,Double> pTrie = propositionFinale.entrySet().stream().sorted(Map.Entry.comparingByValue()).
            Negociant meilleur = pTrie.get(pTrie.size() - 1);

            Message valid = new Message();
            valid.setAgentEmetteur(this);
            valid.setAgentDestinataire(propositionFinale.get());

            Performatif performatif = new Performatif();
            performatif.setDeadLine(Utils.datePlusDays(10));
            billet = message.getPerformatif().getBillet();

            Agent negociant = message.getAgentEmetteur();
            performatif.setBillet(billet);
            performatif.setAction(Action.VALIDER);
            reponse.setPerformatif(performatif);
            batNegociants.poster(message.getAgentEmetteur(), reponse);
            this.billet = null;
            derniereOffre.keySet().stream().filter(n -> !n.equals(negociant)).forEach(n -> {
                Message refus = new Message();
                refus.setAgentEmetteur(this);
                refus.setAgentDestinataire(n);
                Performatif p = new Performatif();
                p.setAction(Action.REFUSE);
                p.setDeadLine(Utils.datePlusDays(10));
                p.setBillet(billet);
                refus.setPerformatif(p);
                batNegociants.poster(n, refus);
            });
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

    public Map<Agent, Double> getAvantDerniereOffre() {
        return avantDerniereOffre;
    }

    public void setAvantDerniereOffre(Map<Agent, Double> avantDerniereOffre) {
        this.avantDerniereOffre = avantDerniereOffre;
    }

    public Map<Agent, Double> getDerniereOffre() {
        return derniereOffre;
    }

    public void setDerniereOffre(Map<Agent, Double> derniereOffre) {
        this.derniereOffre = derniereOffre;
    }

    public Map<Agent, Double> getDerniereSoumission() {
        return derniereSoumission;
    }

    public void setDerniereSoumission(Map<Agent, Double> derniereSoumission) {
        this.derniereSoumission = derniereSoumission;
    }
}
