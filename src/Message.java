public class Message {
    private String agentEmetteur;
    private String agentDestinataire;
    private Performatif performatif;

    public Message() {
        this.agentEmetteur = new String();
        this.agentDestinataire = new String();
        this.performatif = new Performatif();
    }

    public String getAgentEmetteur() {
        return agentEmetteur;
    }

    public void setAgentEmetteur(String agentEmetteur) {
        this.agentEmetteur = agentEmetteur;
    }

    public String getAgentDestinataire() {
        return agentDestinataire;
    }

    public void setAgentDestinataire(String agentDestinataire) {
        this.agentDestinataire = agentDestinataire;
    }

    public Performatif getPerformatif() {
        return performatif;
    }

    public void setPerformatif(Performatif performatif) {
        this.performatif = performatif;
    }
}
