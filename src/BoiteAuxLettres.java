import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoiteAuxLettres<T> {

    private Map<T, List<String>> mapMessages;

    private static BoiteAuxLettres<Fournisseur> batFournisseur = new BoiteAuxLettres<>();
    private static BoiteAuxLettres<Negociant> batNegociant = new BoiteAuxLettres<>();

    public BoiteAuxLettres() {
        mapMessages = new HashMap<>();
    }

    public void poster(T destinataire, String message) {
        if (mapMessages.get(destinataire) == null) mapMessages.put(destinataire, new ArrayList<>());
        mapMessages.get(destinataire).add(message);
    }

    public String recuperer(T proprietaire) {
        if (mapMessages.get(proprietaire) == null) return null;
        String courrier = mapMessages.get(proprietaire).get(0);
        mapMessages.get(proprietaire).remove(0);
        return courrier;
    }

    public Map<T, List<String>> getMapMessages() {
        return mapMessages;
    }

    public void setMapMessages(Map<T, List<String>> mapMessages) {
        this.mapMessages = mapMessages;
    }

    public static BoiteAuxLettres<Fournisseur> getBatFournisseur() {
        return batFournisseur;
    }
    public static BoiteAuxLettres<Negociant> getBatNegociant() {
        return batNegociant;
    }
}
