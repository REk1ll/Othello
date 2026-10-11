package com.othello.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Frontend extends Application {

    private static final int DIMENSIONE_CASELLA = 60; 

    @Override
    public void start(Stage primaryStage) {
        GridPane scacchieraGrafica = new GridPane();

        // Generiamo il tabellone 8x8
        for (int riga = 0; riga < 8; riga++) {
            for (int colonna = 0; colonna < 8; colonna++) {
                
                Rectangle casella = new Rectangle(DIMENSIONE_CASELLA, DIMENSIONE_CASELLA);
                
                // Colore alternato Othello (solitamente verde chiaro e verde scuro)
                if ((riga + colonna) % 2 == 0) {
                    casella.setFill(Color.web("#2e8b57")); // SeaGreen
                } else {
                    casella.setFill(Color.web("#228b22")); // ForestGreen
                }
                casella.setStroke(Color.web("#1a5235")); // Bordino scuro
                
                // --- IL PUNTO FORTE: GESTIONE DEL CLIC ---
                // Diciamo alla casella cosa fare quando viene cliccata dal mouse
                casella.setOnMouseClicked(event -> {
                    // Recuperiamo la posizione di questa casella all'interno del GridPane
                    Integer col = GridPane.getColumnIndex(casella);
                    Integer rig = GridPane.getRowIndex(casella);
                    
                    // Stampiamo le coordinate nel terminale in tempo reale
                    System.out.println("Hai cliccato la casella nelle coordinate logiche: [Riga: " + rig + ", Colonna: " + col + "]");
                });

                // Aggiungiamo la casella alla griglia visiva
                scacchieraGrafica.add(casella, colonna, riga);
            }
        }

        int dimensioneFinestra = 8 * DIMENSIONE_CASELLA;
        Scene scene = new Scene(scacchieraGrafica, dimensioneFinestra, dimensioneFinestra);

        primaryStage.setTitle("Othello - Gestione Clic");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); 
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
