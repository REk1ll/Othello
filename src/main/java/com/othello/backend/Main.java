package com.othello.backend;

public class Main {

    // Matrice logica 8x8 per rappresentare il tavolo da gioco.
    // 0 = Casella Vuota
    // 1 = Pedina Nera
    // 2 = Pedina Bianca
    private int[][] grigliaLogica;
    
    // Variabile per gestire il turno corrente (1 per Nero, 2 per Bianco)
    // In Othello il Nero muove sempre per primo
    private int turnoCorrente; 

    // Costruttore: inizializza lo stato iniziale del gioco Othello
    public Main() {
        grigliaLogica = new int[8][8];
        turnoCorrente = 1; // Inizia il giocatore Nero
        
        // Configurazione iniziale standard dell'Othello al centro del tabellone:
        // Due pedine bianche e due nere alternate nei quattro quadrati centrali
        grigliaLogica[3][3] = 2; // Bianco
        grigliaLogica[3][4] = 1; // Nero
        grigliaLogica[4][3] = 1; // Nero
        grigliaLogica[4][4] = 2; // Bianco
    }

    // Metodo per recuperare il valore di una cella specifica
    public int getStatoCella(int riga, int colonna) {
        return grigliaLogica[riga][colonna];
    }

    // Metodo per cambiare il turno del giocatore alla fine di una mossa
    public void cambiaTurno() {
        if (turnoCorrente == 1) {
            turnoCorrente = 2;
        } else {
            turnoCorrente = 1;
        }
    }

    // Metodo per ottenere il giocatore di turno
    public int getTurnoCorrente() {
        return turnoCorrente;
    }
}
