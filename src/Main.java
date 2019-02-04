import javafx.util.Pair;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // index:
        //      - 2: Premier échange 1 Négociant, 1 Fournisseur
        //      - 3: Deuxieme échange 2 Négociants, 1 Fournisseur
        //      - 5: Troisieme échange 2 Négociants, 2 Fournisseur
        //      - 7: Troisieme échange 3 Négociants, 3 Fournisseur
        Pair<ArrayList<Negociant>,ArrayList<Fournisseur>> list = lists(3);

        runThreads(list.getValue(), list.getKey());}

    private static void runThreads(ArrayList<Fournisseur> listFournisseurs, ArrayList<Negociant> listNegociants) {
        for (Fournisseur fournisseur: listFournisseurs) {
            new Thread(fournisseur).start();
        }
        for (Negociant negociant: listNegociants) {
            new Thread(negociant).start();
        }
    }

    public static Pair<ArrayList<Negociant>,ArrayList<Fournisseur>> lists(int index){
        // index:
        //      - 2: Premier échange 1 Négociant, 1 Fournisseur
        //      - 3: Deuxieme échange 2 Négociants, 1 Fournisseur
        //      - 5: Troisieme échange 2 Négociants, 2 Fournisseur
        //      - 7: Troisieme échange 3 Négociants, 3 Fournisseur

        ArrayList<Fournisseur> listFournisseurs = new ArrayList<>();
        ArrayList<Negociant> listNegociants= new ArrayList<>();

        if ((index % 2) == 0) {
            System.out.println("Premier échange 1 Négociant, 1 Fournisseur");

            Negociant negociant_1_1 = new Negociant();
            negociant_1_1.setNbSoumissionMax(5);
            negociant_1_1.setPourcentCroissance(0.3);
            negociant_1_1.setDepartSouhaite(Lieu.PARIS);
            negociant_1_1.setDestinationSouhaitee(Lieu.MARSEILLE);
            negociant_1_1.setBudgetSouhaiteeMin(110.0);
            negociant_1_1.setBudgetSouhaiteeMax(500.0);
            negociant_1_1.setDateAchatAuPlusTard(Utils.datePlusDays(5)); // dans 5 jours
            negociant_1_1.setName("Négociant 1");


            Billet billet_1_1 = new Billet();
            billet_1_1.setLieuDepart(Lieu.PARIS);
            billet_1_1.setLieuArrivee(Lieu.MARSEILLE);
            billet_1_1.setDateDepart(Utils.datePlusDays(10));
            billet_1_1.setDateArrivee(Utils.datePlusDays(11));

            Fournisseur fournisseur_1_1 = new Fournisseur();
            fournisseur_1_1.setValeurDepart(400.0);
            fournisseur_1_1.setPrixMin(100.0);
            fournisseur_1_1.setBillet(billet_1_1);
            fournisseur_1_1.setDateVenteAuPlusTard(Utils.datePlusDays(9));
            fournisseur_1_1.setName("Fournisseur 1");


            listFournisseurs.add(fournisseur_1_1);
            listNegociants.add(negociant_1_1);

            System.out.println("\n\n");
        }

        if ((index % 3) == 0) {

            System.out.println("Deuxieme échange 2 Négociants, 1 Fournisseur ");
            Negociant negociant_2_1 = new Negociant();
            negociant_2_1.setNbSoumissionMax(5);
            negociant_2_1.setPourcentCroissance(0.3);
            negociant_2_1.setDepartSouhaite(Lieu.PARIS);
            negociant_2_1.setDestinationSouhaitee(Lieu.MARSEILLE);
            negociant_2_1.setBudgetSouhaiteeMin(110.0);
            negociant_2_1.setBudgetSouhaiteeMax(500.0);
            negociant_2_1.setDateAchatAuPlusTard(Utils.datePlusDays(5)); // dans 5 jours
            negociant_2_1.setName("Négociant 1");

            Negociant negociant_2_2 = new Negociant();
            negociant_2_2.setNbSoumissionMax(6);
            negociant_2_2.setPourcentCroissance(0.25);
            negociant_2_2.setDepartSouhaite(Lieu.PARIS);
            negociant_2_2.setDestinationSouhaitee(Lieu.MARSEILLE);
            negociant_2_2.setBudgetSouhaiteeMin(150.0);
            negociant_2_2.setBudgetSouhaiteeMax(600.0);
            negociant_2_2.setDateAchatAuPlusTard(Utils.datePlusDays(6));
            negociant_2_2.setName("Négociant 2");


            Billet billet_2_1 = new Billet();
            billet_2_1.setLieuDepart(Lieu.PARIS);
            billet_2_1.setLieuArrivee(Lieu.MARSEILLE);
            billet_2_1.setDateDepart(Utils.datePlusDays(10));
            billet_2_1.setDateArrivee(Utils.datePlusDays(11));

            Fournisseur fournisseur_2_1 = new Fournisseur();
            fournisseur_2_1.setValeurDepart(400.0);
            fournisseur_2_1.setPrixMin(100.0);
            fournisseur_2_1.setBillet(billet_2_1);
            fournisseur_2_1.setDateVenteAuPlusTard(Utils.datePlusDays(9));
            fournisseur_2_1.setName("Fournisseur 1");

            listFournisseurs.add(fournisseur_2_1);
            listNegociants.add(negociant_2_1);
            listNegociants.add(negociant_2_2);

            System.out.println("\n\n");
        }


        if ((index % 5) == 0) {
//        Premier échange 2 Négociant, 2 Fournisseur
            System.out.println("Troisieme échange 2 Négociants, 2 Fournisseurs ");
            Negociant negociant_3_1 = new Negociant();
            negociant_3_1.setNbSoumissionMax(5);
            negociant_3_1.setPourcentCroissance(0.3);
            negociant_3_1.setDepartSouhaite(Lieu.PARIS);
            negociant_3_1.setDestinationSouhaitee(Lieu.MARSEILLE);
            negociant_3_1.setBudgetSouhaiteeMin(110.0);
            negociant_3_1.setBudgetSouhaiteeMax(500.0);
            negociant_3_1.setDateAchatAuPlusTard(Utils.datePlusDays(5)); // dans 5 jours
            negociant_3_1.setName("Négociant 1");

            Negociant negociant_3_2 = new Negociant();
            negociant_3_2.setNbSoumissionMax(6);
            negociant_3_2.setPourcentCroissance(0.25);
            negociant_3_2.setDepartSouhaite(Lieu.PARIS);
            negociant_3_2.setDestinationSouhaitee(Lieu.MARSEILLE);
            negociant_3_2.setBudgetSouhaiteeMin(150.0);
            negociant_3_2.setBudgetSouhaiteeMax(600.0);
            negociant_3_2.setDateAchatAuPlusTard(Utils.datePlusDays(6));
            negociant_3_2.setName("Négociant 2");


            Billet billet_3_1 = new Billet();
            billet_3_1.setLieuDepart(Lieu.PARIS);
            billet_3_1.setLieuArrivee(Lieu.MARSEILLE);
            billet_3_1.setDateDepart(Utils.datePlusDays(10));
            billet_3_1.setDateArrivee(Utils.datePlusDays(11));

            Billet billet_3_2 = new Billet();
            billet_3_2.setLieuDepart(Lieu.PARIS);
            billet_3_2.setLieuArrivee(Lieu.MARSEILLE);
            billet_3_2.setDateDepart(Utils.datePlusDays(10));
            billet_3_2.setDateArrivee(Utils.datePlusDays(11));


            Fournisseur fournisseur_3_1 = new Fournisseur();
            fournisseur_3_1.setValeurDepart(400.0);
            fournisseur_3_1.setPrixMin(100.0);
            fournisseur_3_1.setBillet(billet_3_1);
            fournisseur_3_1.setDateVenteAuPlusTard(Utils.datePlusDays(9));
            fournisseur_3_1.setName("Fournisseur 1");

            Fournisseur fournisseur_3_2 = new Fournisseur();
            fournisseur_3_2.setValeurDepart(400.0);
            fournisseur_3_2.setPrixMin(100.0);
            fournisseur_3_2.setBillet(billet_3_2);
            fournisseur_3_2.setDateVenteAuPlusTard(Utils.datePlusDays(9));
            fournisseur_3_2.setName("Fournisseur 2");


            listFournisseurs.add(fournisseur_3_1);
            listFournisseurs.add(fournisseur_3_2);
            listNegociants.add(negociant_3_1);
            listNegociants.add(negociant_3_2);

            System.out.println("\n\n");
        }


        if ((index % 7) == 0) {
//        Premier échange 3 Négociant, 3 Fournisseur
            System.out.println("Troisieme échange 3 Négociants, 3 Fournisseurs \n\n");
            Negociant negociant_4_1 = new Negociant();
            negociant_4_1.setNbSoumissionMax(5);
            negociant_4_1.setPourcentCroissance(0.3);
            negociant_4_1.setDepartSouhaite(Lieu.PARIS);
            negociant_4_1.setDestinationSouhaitee(Lieu.MARSEILLE);
            negociant_4_1.setBudgetSouhaiteeMin(110.0);
            negociant_4_1.setBudgetSouhaiteeMax(500.0);
            negociant_4_1.setDateAchatAuPlusTard(Utils.datePlusDays(5)); // dans 5 jours
            negociant_4_1.setName("Négociant 1");

            Negociant negociant_4_2 = new Negociant();
            negociant_4_2.setNbSoumissionMax(6);
            negociant_4_2.setPourcentCroissance(0.25);
            negociant_4_2.setDepartSouhaite(Lieu.PARIS);
            negociant_4_2.setDestinationSouhaitee(Lieu.MARSEILLE);
            negociant_4_2.setBudgetSouhaiteeMin(150.0);
            negociant_4_2.setBudgetSouhaiteeMax(600.0);
            negociant_4_2.setDateAchatAuPlusTard(Utils.datePlusDays(6));
            negociant_4_2.setName("Négociant 2");

            Negociant negociant_4_3 = new Negociant();
            negociant_4_3.setNbSoumissionMax(6);
            negociant_4_3.setPourcentCroissance(0.3);
            negociant_4_3.setDepartSouhaite(Lieu.LYON);
            negociant_4_3.setDestinationSouhaitee(Lieu.SAINT_ETIENNE);
            negociant_4_3.setBudgetSouhaiteeMin(20.0);
            negociant_4_3.setBudgetSouhaiteeMax(300.0);
            negociant_4_3.setDateAchatAuPlusTard(Utils.datePlusDays(6));
            negociant_4_3.setName("Négociant 3");


            Billet billet_4_1 = new Billet();
            billet_4_1.setLieuDepart(Lieu.PARIS);
            billet_4_1.setLieuArrivee(Lieu.MARSEILLE);
            billet_4_1.setDateDepart(Utils.datePlusDays(10));
            billet_4_1.setDateArrivee(Utils.datePlusDays(11));

            Billet billet_4_2 = new Billet();
            billet_4_2.setLieuDepart(Lieu.PARIS);
            billet_4_2.setLieuArrivee(Lieu.MARSEILLE);
            billet_4_2.setDateDepart(Utils.datePlusDays(10));
            billet_4_2.setDateArrivee(Utils.datePlusDays(11));

            Billet billet_4_3 = new Billet();
            billet_4_3.setLieuDepart(Lieu.LYON);
            billet_4_3.setLieuArrivee(Lieu.SAINT_ETIENNE);
            billet_4_3.setDateDepart(Utils.datePlusDays(10));
            billet_4_3.setDateArrivee(Utils.datePlusDays(11));

            Fournisseur fournisseur_4_1 = new Fournisseur();
            fournisseur_4_1.setValeurDepart(400.0);
            fournisseur_4_1.setPrixMin(100.0);
            fournisseur_4_1.setBillet(billet_4_1);
            fournisseur_4_1.setDateVenteAuPlusTard(Utils.datePlusDays(9));
            fournisseur_4_1.setName("Fournisseur 1");

            Fournisseur fournisseur_4_2 = new Fournisseur();
            fournisseur_4_2.setValeurDepart(400.0);
            fournisseur_4_2.setPrixMin(100.0);
            fournisseur_4_2.setBillet(billet_4_2);
            fournisseur_4_2.setDateVenteAuPlusTard(Utils.datePlusDays(9));
            fournisseur_4_2.setName("Fournisseur 2");


            Fournisseur fournisseur_4_3 = new Fournisseur();
            fournisseur_4_3.setValeurDepart(400.0);
            fournisseur_4_3.setPrixMin(100.0);
            fournisseur_4_3.setBillet(billet_4_3);
            fournisseur_4_3.setDateVenteAuPlusTard(Utils.datePlusDays(9));
            fournisseur_4_3.setName("Fournisseur 3");


            listFournisseurs.add(fournisseur_4_1);
            listFournisseurs.add(fournisseur_4_2);
            listFournisseurs.add(fournisseur_4_3);
            listNegociants.add(negociant_4_1);
            listNegociants.add(negociant_4_2);
            listNegociants.add(negociant_4_3);
            System.out.println("\n\n");
        }

        for (Negociant negociant: listNegociants) {
            for (Fournisseur fournisseur: listFournisseurs) {
                negociant.getFournisseurs().add(fournisseur);
            }
        }
        for (Fournisseur fournisseur: listFournisseurs) {
            for (Negociant negociant: listNegociants) {
                fournisseur.getNegociants().add(negociant);
            }
        }

        return new Pair<>(listNegociants,listFournisseurs);
    }
}
