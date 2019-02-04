public class Message {
    private Agent agentEmetteur;
    private Agent agentDestinataire;
    private Performatif performatif;

    public Agent getAgentEmetteur() {
        return agentEmetteur;
    }

    public void setAgentEmetteur(Agent agentEmetteur) {
        this.agentEmetteur = agentEmetteur;
    }

    public Agent getAgentDestinataire() {
        return agentDestinataire;
    }

    public void setAgentDestinataire(Agent agentDestinataire) {
        this.agentDestinataire = agentDestinataire;
    }

    public Performatif getPerformatif() {
        return performatif;
    }

    public void setPerformatif(Performatif performatif) {
        this.performatif = performatif;
    }

    @Override
    public String toString() {
        return "" +
                agentEmetteur.getName() + " à " + agentDestinataire.getName()
                + ", " + performatif.getBillet().getLieuDepart()
                + " à " + performatif.getBillet().getLieuArrivee()
                + ", " + performatif.getAction()
                + ", " + performatif.getBillet().getPrix();
    }
}
