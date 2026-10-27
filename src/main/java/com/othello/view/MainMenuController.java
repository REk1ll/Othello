package com.othello.view;

import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML private BorderPane root;
    @FXML private ImageView logo;
    @FXML private VBox menuBox;

    @FXML
    private void initialize() {
        Font.loadFont(
                getClass().getResourceAsStream("fonts/FreeSerifBold.ttf"),
                12
        );

        logo.fitWidthProperty().bind(root.widthProperty().multiply(0.60));
        menuBox.prefWidthProperty().bind(root.widthProperty().multiply(0.26));

        root.styleProperty().bind(Bindings.createStringBinding(
                () -> "-fx-font-size: "
                        + Math.round(Math.max(20, root.getHeight() * 0.045))
                        + "px;",
                root.heightProperty()
        ));
    }
    @FXML
    private void onBackToMenu(ActionEvent event) {
        try {
            Parent pagina = FXMLLoader.load(
                    getClass().getResource(
                            "/com/othello/view/MainMenu.fxml"
                    )
            );

            Stage finestra = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene()
                    .getWindow();

            finestra.setScene(new Scene(pagina));
            finestra.setTitle("Othello Game");
            finestra.show();

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            System.err.println("Impossibile caricare MainMenu.fxml");
        }
    }

    @FXML
    private void onPlay() {
        // Avvia nuova partita
    }

    @FXML
    private void onReplays() {
        // Apri lista replay
    }

    @FXML
    private void onCredits(ActionEvent event) {
        try {
            Parent pagina = FXMLLoader.load(
                    getClass().getResource(
                            "/com/othello/view/CreditsPage.fxml"
                    )
            );

            Stage finestra = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene()
                    .getWindow();

            finestra.setScene(new Scene(pagina));
            finestra.setTitle("Crediti");
            finestra.show();

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            System.err.println("Impossibile caricare CreditsPage.fxml");
        }
    }

    @FXML
    private void onSettings() {
        // Apri impostazioni
    }

    @FXML
    private void onExit() {
        Platform.exit();
    }
}
