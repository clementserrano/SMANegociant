import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoiteAuxLettres {

    private Map<Agent, List<Message>> mapMessages;

    private static BoiteAuxLettres batFournisseur = new BoiteAuxLettres();
    private static BoiteAuxLettres batNegociant = new BoiteAuxLettres();

    private BoiteAuxLettres() {
        mapMessages = new HashMap<>();
    }

    public synchronized void poster(Agent destinataire, Message message, boolean display) {
        if (mapMessages.get(destinataire) == null) mapMessages.put(destinataire, new ArrayList<>());
        mapMessages.get(destinataire).add(message);
        if (display) {
            System.out.println(message);
            if (destinataire.getGui() != null) {
                destinataire.getGui().update(message);
            }
        }
    }

    public Message recuperer(Agent proprietaire) {
        if (mapMessages.get(proprietaire) == null || mapMessages.get(proprietaire).size() == 0) return null;
        Message courrier = mapMessages.get(proprietaire).get(0);
        mapMessages.get(proprietaire).remove(0);
        return courrier;
    }

    public static BoiteAuxLettres getBatFournisseur() {
        return batFournisseur;
    }

    public static BoiteAuxLettres getBatNegociant() {
        return batNegociant;
    }
}
