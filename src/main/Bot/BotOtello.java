import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OthelloGame {
    private static final char EMPTY = ' ';
    private static final char BLACK = 'O'; // Giocatore Nero
    private static final char WHITE = 'X'; // Giocatore Bianco

    private char[][] board;

    public OthelloGame() {
        board = new char[8][8];
        initializeBoard();
    }

    // Inizializza la scacchiera secondo la configurazione iniziale (Figura 1)
    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = EMPTY;
            }
        }
        // D4 = X (3,3), E4 = O (3,4), D5 = O (4,3), E5 = X (4,4)
        board[3][3] = 'X';
        board[3][4] = 'O';
        board[4][3] = 'O';
        board[4][4] = 'X';
    }

    // Stampa la scacchiera con interfaccia testuale e coordinate ASCII
    public void printBoard() {
        System.out.println("   A   B   C   D   E   F   G   H");
        System.out.println(" +---+---+---+---+---+---+---+---+");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < 8; j++) {
                System.out.print(" " + board[i][j] + " |");
            }
            System.out.println(" " + (i + 1));
            System.out.println(" +---+---+---+---+---+---+---+---+");
        }
        System.out.println("   A   B   C   D   E   F   G   H");
    }

    // Le 8 direzioni possibili: (riga, colonna)
    private static final int[][] DIRECTIONS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        { 0, -1},          { 0, 1},
        { 1, -1}, { 1, 0}, { 1, 1}
    };

    // Valuta la mossa e, se apply = true, effettua anche il capovolgimento delle pedine
    private int evaluateAndExecuteMove(int row, int col, char player, boolean apply) {
        if (row < 0 || row >= 8 || col < 0 || col >= 8 || board[row][col] != EMPTY) {
            return 0;
        }

        char opponent = (player == BLACK) ? WHITE : BLACK;
        int totalFlipped = 0;
        List<int[]> flipsToExecute = new ArrayList<>();

        for (int[] dir : DIRECTIONS) {
            int r = row + dir[0];
            int c = col + dir[1];
            List<int[]> currentDirectionFlips = new ArrayList<>();

            // Avanza finché incontra pedine avversarie
            while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == opponent) {
                currentDirectionFlips.add(new int[]{r, c});
                r += dir[0];
                c += dir[1];
            }

            // Se la sequenza è chiusa da una pedina dello stesso giocatore, la mossa è valida in questa direzione
            if (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] == player && !currentDirectionFlips.isEmpty()) {
                totalFlipped += currentDirectionFlips.size();
                flipsToExecute.addAll(currentDirectionFlips);
            }
        }

        // Se richiesto e la mossa è valida, applica le modifiche sulla scacchiera
        if (apply && totalFlipped > 0) {
            board[row][col] = player;
            for (int[] pos : flipsToExecute) {
                board[pos[0]][pos[1]] = player;
            }
        }

        return totalFlipped;
    }

    // Verifica se una mossa è legale
    public boolean isValidMove(int row, int col, char player) {
        return evaluateAndExecuteMove(row, col, player, false) > 0;
    }

    // Controlla se il giocatore ha almeno una mossa legale disponibile
    public boolean hasValidMoves(char player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == EMPTY && isValidMove(i, j, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Logica del Bot: itera su tutte le mosse disponibili e sceglie quella che cattura più pedine
    public boolean botMove(char botPlayer) {
        int bestScore = -1;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == EMPTY) {
                    int flipped = evaluateAndExecuteMove(i, j, botPlayer, false);
                    if (flipped > bestScore) {
                        bestScore = flipped;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        if (bestScore > 0) {
            evaluateAndExecuteMove(bestRow, bestCol, botPlayer, true);
            char colChar = (char) ('A' + bestCol);
            System.out.println("Il Bot (" + botPlayer + ") ha giocato in: " + colChar + (bestRow + 1) + " (pedine catturate: " + bestScore + ")");
            return true;
        }
        return false;
    }

    // Gestisce la mossa del giocatore fisico con validazione dell'input
    public boolean playerMove(char playerSymbol, Scanner scanner) {
        if (!hasValidMoves(playerSymbol)) {
            System.out.println("Nessuna mossa legale disponibile per te. Turno saltato.");
            return false;
        }

        while (true) {
            System.out.print("Il tuo turno (" + playerSymbol + "). Inserisci la mossa (es. D3): ");
            String input = scanner.nextLine().trim().toUpperCase();
            
            if (input.length() != 2) {
                System.out.println("Formato non valido. Inserisci una lettera da A a H seguita da un numero da 1 a 8 (es. D3).");
                continue;
            }

            int col = input.charAt(0) - 'A';
            int row = input.charAt(1) - '1';

            if (row < 0 || row >= 8 || col < 0 || col >= 8) {
                System.out.println("Coordinate fuori dalla scacchiera. Riprova.");
                continue;
            }

            if (board[row][col] != EMPTY) {
                System.out.println("Casella già occupata. Scegline una vuota.");
                continue;
            }

            int flipped = evaluateAndExecuteMove(row, col, playerSymbol, true);
            if (flipped > 0) {
                System.out.println("Mossa valida! Girate " + flipped + " pedine avversarie.");
                return true;
            } else {
                System.out.println("Mossa non valida: devi imprigionare almeno una pedina avversaria. Riprova.");
            }
        }
    }

    // Condizione di fine partita: nessuno dei due giocatori ha mosse legali
    public boolean isGameOver() {
        return !hasValidMoves(BLACK) && !hasValidMoves(WHITE);
    }

    // Calcola il punteggio finale e dichiara il vincitore
    public void printWinner() {
        int blackCount = 0;
        int whiteCount = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == BLACK) blackCount++;
                else if (board[i][j] == WHITE) whiteCount++;
            }
        }

        System.out.println("\n=========================================");
        System.out.println("           FINE DELLA PARTITA            ");
        System.out.println("=========================================");
        System.out.println("Pedine Nere (O): " + blackCount);
        System.out.println("Pedine Bianche (X): " + whiteCount);

        if (blackCount > whiteCount) {
            System.out.println("Vince il Nero (O)!");
        } else if (whiteCount > blackCount) {
            System.out.println("Vince il Bianco (X)!");
        } else {
            System.out.println("Pareggio!");
        }
    }

    // Metodo principale per l'esecuzione del programma
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OthelloGame game = new OthelloGame();

        System.out.println("Benvenuto a Othello!");
        System.out.println("Regola: Il Nero ('O') inizia sempre per primo.");
        System.out.print("Vuoi iniziare tu per primo? (s/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        char humanSymbol, botSymbol;
        if (choice.startsWith("s")) {
            humanSymbol = BLACK; // Nero (inizia)
            botSymbol = WHITE;
            System.out.println("\nHai scelto di iniziare. Tu sei il Nero ('O'), il Bot è il Bianco ('X').");
        } else {
            botSymbol = BLACK; // Nero (inizia)
            humanSymbol = WHITE;
            System.out.println("\nIl Bot inizia per primo. Tu sei il Bianco ('X'), il Bot è il Nero ('O').");
        }

        char turn = BLACK; // Il gioco comincia sempre con il Nero

        while (!game.isGameOver()) {
            System.out.println("\n-----------------------------------------");
            game.printBoard();

            // Salta il turno se il giocatore corrente non ha mosse legali
            if (!game.hasValidMoves(turn)) {
                System.out.println("Avviso: Nessuna mossa legale disponibile per " + (turn == BLACK ? "Nero (O)" : "Bianco (X)") + ". Turno saltato.");
                turn = (turn == BLACK) ? WHITE : BLACK;
                continue;
            }

            if (turn == botSymbol) {
                System.out.println("\nTurno del Bot in corso...");
                game.botMove(botSymbol);
            } else {
                System.out.println("\nÈ il tuo turno!");
                game.playerMove(humanSymbol, scanner);
            }

            // Inverte il turno
            turn = (turn == BLACK) ? WHITE : BLACK;
        }

        game.printBoard();
        game.printWinner();
        scanner.close();
    }
}