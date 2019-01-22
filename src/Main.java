import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Negociant negociant = new Negociant(100.0, 5, 0.30);
        negociant.setDestinationSouhaitee(Lieu.MARSEILLE);
        negociant.setBudgetSouhaiteeMin(110.0);
        negociant.setBudgetSouhaiteeMax(500.0);
        negociant.setDateAchatAuPlusTard(sdf.parse("20/01/2019"));

        Fournisseur fournisseur = new Fournisseur();

        Billet billet = new Billet();
        billet.setLieuDepart(Lieu.PARIS);
        billet.setLieuArrivee(Lieu.MARSEILLE);

        billet.setDateDepart(sdf.parse("29/01/2019"));
        billet.setDateArrivee(sdf.parse("30/01/2019"));

        fournisseur.setValeurDepart(400.0);
        fournisseur.setPrixMin(100.0);
        fournisseur.setBillet(billet);
        fournisseur.setDateVenteAuPlusTard(sdf.parse("25/01/2019"));
        fournisseur.getNegociants().add(negociant);

        new Thread(fournisseur).start();
        new Thread(negociant).start();
    }
}
