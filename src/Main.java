public class Main {

    public static void main(String[] args) {
        Negociant negociant = new Negociant();
        negociant.setNbSoumissionMax(5);
        negociant.setPourcentCroissance(0.3);
        negociant.setDepartSouhaite(Lieu.PARIS);
        negociant.setDestinationSouhaitee(Lieu.MARSEILLE);
        negociant.setBudgetSouhaiteeMin(110.0);
        negociant.setBudgetSouhaiteeMax(500.0);
        negociant.setDateAchatAuPlusTard(Utils.datePlusDays(5)); // dans 5 jours
        negociant.setName("Négociant 1");

        Negociant negociant2 = new Negociant();
        negociant2.setNbSoumissionMax(6);
        negociant2.setPourcentCroissance(0.25);
        negociant2.setDepartSouhaite(Lieu.PARIS);
        negociant2.setDestinationSouhaitee(Lieu.MARSEILLE);
        negociant2.setBudgetSouhaiteeMin(150.0);
        negociant2.setBudgetSouhaiteeMax(600.0);
        negociant2.setDateAchatAuPlusTard(Utils.datePlusDays(6));
        negociant2.setName("Négociant 2");

        Fournisseur fournisseur = new Fournisseur();

        Billet billet = new Billet();
        billet.setLieuDepart(Lieu.PARIS);
        billet.setLieuArrivee(Lieu.MARSEILLE);

        billet.setDateDepart(Utils.datePlusDays(10));
        billet.setDateArrivee(Utils.datePlusDays(11));

        fournisseur.setValeurDepart(400.0);
        fournisseur.setPrixMin(100.0);
        fournisseur.setBillet(billet);
        fournisseur.setDateVenteAuPlusTard(Utils.datePlusDays(9));

        fournisseur.getNegociants().add(negociant);
        fournisseur.getNegociants().add(negociant2);
        fournisseur.setName("Fournisseur 1");

        Fournisseur fournisseur2 = new Fournisseur();

        Billet billet2 = new Billet();
        billet2.setLieuDepart(Lieu.PARIS);
        billet2.setLieuArrivee(Lieu.MARSEILLE);

        billet2.setDateDepart(Utils.datePlusDays(10));
        billet2.setDateArrivee(Utils.datePlusDays(11));

        fournisseur2.setValeurDepart(400.0);
        fournisseur2.setPrixMin(100.0);
        fournisseur2.setBillet(billet2);
        fournisseur2.setDateVenteAuPlusTard(Utils.datePlusDays(9));

        fournisseur2.getNegociants().add(negociant);
        fournisseur2.getNegociants().add(negociant2);
        fournisseur2.setName("Fournisseur 2");

        negociant.getFournisseurs().add(fournisseur);
        negociant.getFournisseurs().add(fournisseur2);
        negociant2.getFournisseurs().add(fournisseur);
        negociant2.getFournisseurs().add(fournisseur2);

        new Thread(fournisseur).start();
        new Thread(fournisseur2).start();
        new Thread(negociant).start();
        new Thread(negociant2).start();
    }
}
