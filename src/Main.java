import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Negociant negociant = new Negociant();
        negociant.setDestinationSouhaitee(Lieu.MARSEILLE);
        negociant.setBudgetSouhaitee(110);
        negociant.setDateAchatAuPlusTard(sdf.parse("20/01/2019"));

        Fournisseur fournisseur = new Fournisseur();

        Billet billet = new Billet();
        billet.setLieuDepart(Lieu.PARIS);
        billet.setLieuArrivee(Lieu.MARSEILLE);

        billet.setDateDepart(sdf.parse("29/01/2019"));
        billet.setDateArrivee(sdf.parse("30/01/2019"));
        billet.setPrix(100);

        fournisseur.setBillet(billet);
        fournisseur.proposeOffre();
    }
}
