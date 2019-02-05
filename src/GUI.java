import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUI extends Application {

    private Map<Fournisseur, Map<Negociant, GridPane>> mapMessages;

    private List<Fournisseur> fournisseurs;
    private List<Negociant> negociants;

//      index:
//      - 2: Premier échange 1 Négociant, 1 Fournisseur
//      - 3: Deuxieme échange 2 Négociants, 1 Fournisseur
//      - 5: Troisieme échange 2 Négociants, 2 Fournisseurs
//      - 7: Troisieme échange 3 Négociants, 3 Fournisseurs
    private final static int INDEX = 7;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("SMA Négociant");
        mapMessages = new HashMap<>();

        fillList();

        GridPane gridPane = new GridPane();
        int i = 0;
        for (Fournisseur fournisseur : fournisseurs) {
            mapMessages.put(fournisseur, new HashMap<>());
            BorderPane borderPane = new BorderPane();
            borderPane.setTop(new Label(fournisseur.getName()));
            GridPane gridPaneNegociant = new GridPane();

            int j = 0;
            for (Negociant negociant : negociants) {
                BorderPane borderPaneNegociant = new BorderPane();
                borderPaneNegociant.setTop(new Label(negociant.getName()));
                GridPane gridPaneMessage = new GridPane();
                mapMessages.get(fournisseur).put(negociant, gridPaneMessage);
                gridPaneMessage.setGridLinesVisible(true);
                borderPaneNegociant.setCenter(gridPaneMessage);
                gridPaneNegociant.add(borderPaneNegociant, j, 0);
                j++;
            }
            gridPaneNegociant.setGridLinesVisible(true);

            borderPane.setCenter(gridPaneNegociant);
            borderPane.setBottom(new Label(fournisseur.getBillet().getLieuDepart()
                    + " - " + fournisseur.getBillet().getLieuArrivee()));
            gridPane.add(borderPane, i, 0);
            i++;
        }
        gridPane.setGridLinesVisible(true);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                fournisseurs.stream().forEach(f -> new Thread(f).start());
                negociants.stream().forEach(n -> new Thread(n).start());
                return null;
            }
        };
        Thread th = new Thread(task);
        th.start();
    }

    public void update(Message message) {
        Platform.runLater(() -> {
            Agent agent1 = message.getAgentEmetteur();
            Agent agent2 = message.getAgentDestinataire();
            Fournisseur fournisseur = null;
            Negociant negociant = null;

            if (agent1.getType().equals("negociant")) negociant = (Negociant) agent1;
            if (agent1.getType().equals("fournisseur")) fournisseur = (Fournisseur) agent1;
            if (agent2.getType().equals("negociant")) negociant = (Negociant) agent2;
            if (agent2.getType().equals("fournisseur")) fournisseur = (Fournisseur) agent2;

            if (fournisseur != null && negociant != null) {
                GridPane gridPane = mapMessages.get(fournisseur).get(negociant);
                String line = "";
                if (agent1 == fournisseur) line += " -> ";
                else if (agent1 == negociant) line += " <- ";
                line += message.getPerformatif().getAction() + " ";
                line += message.getPerformatif().getBillet().getPrix();
                gridPane.add(new Label(line), 0, gridPane.getChildren().size());
            }
        });
    }

    private void fillList() {
        Pair<ArrayList<Negociant>,ArrayList<Fournisseur>> list = Main.lists(INDEX);

        fournisseurs = list.getValue();
        negociants = list.getKey();
        System.out.println(list.getValue());
        System.out.println(list.getKey());

        fournisseurs.stream().forEach(f -> f.setGui(this));
        negociants.stream().forEach(n -> n.setGui(this));
    }
}
