import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class Game extends JPanel implements MouseListener, ActionListener {
    public static int tile = 150;
    public static final int winCon = 4;
    public static int size = 5;
    private static int mouseUsed = 0;
    public static Queen wQ;
    public static Queen toWQ;
    public static Queen bQ;
    public static Queen toBQ;
    private static boolean PvE = false;
    private static int diff = 0;
    public static Queen[] AI_B = new Queen[size + 1];
    public static Queen[] AI_W = new Queen[size + 1];
    public static Queen[] redQueens = new Queen[1];
    AI ai = new AI(this);
    private static int aiCountB = 0;
    private static int aiCountW = 0;
    boolean win = false;
    public static JFrame gameTextFrame = new JFrame();
    public static JLabel gameText = new JLabel("choose a white queen");
    Queen[][] board = new Queen[size][size];
    JFrame gameWindow;
    private JButton restartB;


    public Game() { // a build function that fills the board with the colors White, Gray, Black and adds a mouse to use in the gui.
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                this.board[0][i] = new Queen(0, i, "W", aiCountW);
                AI_W[aiCountW++] = this.board[0][i];
                this.board[size - 1][i] = new Queen(size - 1, i, "B", aiCountB);
                AI_B[aiCountB++] = this.board[size - 1][i];
            } else {
                this.board[0][i] = new Queen(0, i, "B", aiCountB);
                AI_B[aiCountB++] = this.board[0][i];
                this.board[size - 1][i] = new Queen(size - 1, i, "W", aiCountW);
                AI_W[aiCountW++] = this.board[size - 1][i];
            }
        }

        this.board[size / 2][0] = new Queen(size / 2, 0, "W",aiCountW);
        AI_W[aiCountW++] = board[size / 2][0];
        this.board[size / 2][size - 1] = new Queen(size / 2, size - 1, "B", aiCountB);
        AI_B[aiCountB++] = board[size / 2][size - 1];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.board[i][j] == null) {
                    this.board[i][j] = new Queen(i, j, "G", 100);
                }
            }
        }

        addMouseListener(this);
        gameTextFrame.setBounds(0, 0, 3 * tile, tile - tile / 8);
        gameTextFrame.setTitle("Turns");
        gameTextFrame.setResizable(false);
        gameTextFrame.setVisible(true);
        gameTextFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container con = gameTextFrame.getContentPane();
        con.setLayout(null);
        gameText.setBounds(0, 0, 3 * tile, 30);
        con.add(gameText);

        restartB = new JButton("Restart");
        con.add(restartB);
        restartB.setMnemonic(KeyEvent.VK_Z);
        restartB.setBounds(0, tile / 2 - tile / 6, tile, 20);
        restartB.addActionListener(this);
    }

    public static void printArr(Queen[] arr) {
        for (int i = 0; i < size + 1; i++)
            System.out.print(arr[i].row + "," + arr[i].col + "," + arr[i].color + "|");
        System.out.println();
    }

    public static void printBoard(Queen[][] board) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].getColor().equals("B")) {
                    System.out.print("[" + "B" + "]");
                } else if (board[i][j].getColor().equals("W")) {
                    System.out.print("[" + "W" + "]");
                } else {
                    System.out.print("[" + "G" + "]");
                }
            }
            System.out.println();
        }
    }

    public static void showMove() {
        for (int i = 0; i < redQueens.length; i++) {
            if (redQueens[i].color.equals("R"))
                redQueens[i].color = "G";
            else if (redQueens[i].color.equals("G"))
                redQueens[i].color = "R";
        }
    }

    /**
     * a function that gets the board that is played on and one of the game pieces
     * its check with the function validQueenMove if the game piece can move to that spot in the board
     * if it does adds it to the array to save all its available moves
     */
    public static Queen[] allMoves(Queen[][] board, Queen q) {
        int len = 0;
        Queen[] allMoves = new Queen[len];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (q.validQueenMove(board, board[i][j])) {
                    allMoves = Arrays.copyOf(allMoves, len + 1);
                    Queen copy = new Queen(board[i][j].row ,board[i][j].col ,board[i][j].color ,board[i][j].id);
                    allMoves[len] = copy;
                    len++;
                }
            }
        }
        return allMoves;
    }
    public static Queen[] getRedQueens(Queen[][] board, Queen q) {
        int len = 0;
        Queen[] allMoves = new Queen[len];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (q.validQueenMove(board, board[i][j])) {
                    allMoves = Arrays.copyOf(allMoves, len + 1);
                    allMoves[len] = board[i][j];
                    len++;
                }
            }
        }
        return allMoves;
    }

    /**
     * a function that gets the board that is played on
     * check through out all the board if there is a win condition on board (sequence of the colors white or black *(winCon)* number of times
     */
    public static boolean winGame(Queen[][] board) {

        // horizontalCheck
        for (int j = 0; j < size - 3; j++)
            for (int i = 0; i < size; i++)
                if (board[i][j].color.equals(board[i][j + 1].color) && board[i][j].color.equals(board[i][j + 2].color) && board[i][j].color.equals(board[i][j + 3].color) && !board[i][j].color.equals("G") && !board[i][j].color.equals("R"))
                    return true;

        // verticalCheck
        for (int i = 0; i < size - 3; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j].color.equals(board[i + 1][j].color) && board[i][j].color.equals(board[i + 2][j].color) && board[i][j].color.equals(board[i + 3][j].color) && !board[i][j].color.equals("G") && !board[i][j].color.equals("R"))
                    return true;

        // ascendingDiagonalCheck
        for (int i = 3; i < size; i++)
            for (int j = 0; j < size - 3; j++)
                if (board[i][j].color.equals(board[i - 1][j + 1].color) && board[i][j].color.equals(board[i - 2][j + 2].color) && board[i][j].color.equals(board[i - 3][j + 3].color) && !board[i][j].color.equals("G") && !board[i][j].color.equals("R"))
                    return true;

        // descendingDiagonalCheck
        for (int i = 3; i < size; i++)
            for (int j = 3; j < size; j++)
                if (board[i][j].color.equals(board[i - 1][j - 1].color) && board[i][j].color.equals(board[i - 2][j - 2].color) && board[i][j].color.equals(board[i - 3][j - 3].color) && !board[i][j].color.equals("G") && !board[i][j].color.equals("R"))
                    return true;

        return false;

    }

    public void startPvP() {
        gameWindow.setVisible(true);
        gameWindow.paintComponents(gameWindow.getGraphics());

        if (redQueens.length == 0)
            gameText.setText("doesn't have any moves, choose another queen");

        else if (mouseUsed % 4 == 0)
            gameText.setText("Choose a White Queen");

        else if (mouseUsed % 4 == 1)
            gameText.setText("Choose Where to move it (click again on the same queen to cancel)");

        else if (mouseUsed % 4 == 2)
            gameText.setText("Choose a Black Queen");

        else if (mouseUsed % 4 == 3)
            gameText.setText("Choose Where to move it (click again on the same queen to cancel)");

        if (win) {
            removeMouseListener(this);
            if (mouseUsed % 4 - 1 == 0 || mouseUsed % 4 - 1 == 1)
                gameText.setText("white wins");
            else
                gameText.setText("black wins");
        }
    }

    public void startPvE(int diff) {
        //printBoard(this.board);

        this.diff = diff;
        PvE = true;
        gameWindow.setVisible(true);
        gameWindow.paintComponents(gameWindow.getGraphics());

        if (redQueens.length == 0)
            gameText.setText("doesn't have any moves, choose another queen");
        else if (mouseUsed % 2 == 0)
            gameText.setText("Choose a White Queen");
        else if (mouseUsed % 2 == 1)
            gameText.setText("Choose Where to move it (click again on thee same queen to cancel)");

        if (win) {
            removeMouseListener(this);
            gameText.setText("Win");

        }
    }

    @Override
    public void paintComponent(Graphics gp) {
        super.paintComponent(gp);
        int Oval_size = tile / 2;
        gp.setColor(Color.DARK_GRAY);
        gp.fillRect(0, 0, size * tile, size * tile);
        int count = 0;
        int j = 0;
        for (int x = 0; x < size * tile; x += tile) {
            int i = 0;
            for (int y = 0; y < size * tile; y += tile) {
                if (count % 2 == 0) {
                    gp.setColor(Color.gray);
                    gp.fillRect(x, y, tile, tile);
                }
                if (this.board[i][j].getColor().equals("W")) {
                    gp.setColor(Color.WHITE);
                    gp.fillOval(x + tile / 2 - Oval_size / 2, y + tile / 2 - Oval_size / 2, Oval_size, Oval_size);

                }
                if (this.board[i][j].getColor().equals("B")) {
                    gp.setColor(Color.BLACK);
                    gp.fillOval(x + tile / 2 - Oval_size / 2, y + tile / 2 - Oval_size / 2, Oval_size, Oval_size);

                }
                if (this.board[i][j].getColor().equals("R")) {
                    gp.setColor(Color.RED);
                    gp.fillOval(x + tile / 2 - Oval_size / 2, y + tile / 2 - Oval_size / 2, Oval_size, Oval_size);

                }
                count++;
                i++;
            }
            if (size % 2 == 0) {
                count++;
            }
            j++;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if (PvE) {
            if (mouseUsed % 2 == 0) {
                wQ = board[mouseY / tile][mouseX / tile];
                if (wQ.color.equals("W")) {
                    mouseUsed++;
                    redQueens = getRedQueens(board, wQ);
                    showMove();
                    if (redQueens.length == 0) {
                        gameText.setText("doesn't have any moves");
                        mouseUsed--;
                    }
                }
            } else if (mouseUsed % 2 == 1) {
                toWQ = board[mouseY / tile][mouseX / tile];
                if (wQ.validQueenMove(board, toWQ)) {
                    AI.updateBoard(AI_W, board, wQ, toWQ);
                    win = this.winGame(board);
                    mouseUsed++;
                    showMove();
                    ai.minimax(diff, board, AI_B, AI_W, true);
                    win = this.winGame(board);
                    gameWindow.paintComponents(gameWindow.getGraphics());
                }
                if (toWQ == wQ) {
                    gameText.setText("changed white queen");
                    mouseUsed--;
                    showMove();
                }

            }
            this.startPvE(diff);
        } else {
            if (mouseUsed % 4 == 0) {
                wQ = board[mouseY / tile][mouseX / tile];
                if (wQ.color.equals("W")) {
                    mouseUsed++;
                    redQueens = getRedQueens(board, wQ);
                    showMove();
                    if (redQueens.length == 0) {
                        gameText.setText("doesn't have any moves");
                        mouseUsed--;
                    }
                }
            } else if (mouseUsed % 4 == 1) {
                toWQ = board[mouseY / tile][mouseX / tile];
                if (wQ.validQueenMove(board, toWQ)) {
                    wQ.switchQueen(toWQ);
                    win = this.winGame(board);
                    mouseUsed++;
                    showMove();
                }
                if (toWQ == wQ) {
                    gameText.setText("changed white queen");
                    mouseUsed--;
                    showMove();
                }
            } else if (mouseUsed % 4 == 2) {
                bQ = board[mouseY / tile][mouseX / tile];
                if (bQ.color.equals("B")) {
                    mouseUsed++;
                    redQueens = getRedQueens(board, bQ);
                    showMove();
                    if (redQueens.length == 0) {
                        gameText.setText("doesn't have any moves");
                        mouseUsed--;
                    }
                }
            } else if (mouseUsed % 4 == 3) {
                toBQ = board[mouseY / tile][mouseX / tile];
                if (bQ.validQueenMove(board, toBQ)) {
                    bQ.switchQueen(toBQ);
                    win = this.winGame(board);
                    mouseUsed++;
                    showMove();
                }
                if (toBQ == bQ) {
                    gameText.setText("changed black queen");
                    mouseUsed--;
                    showMove();
                }
            }
            this.startPvP();

        }
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String key = e.getActionCommand();

        if (key.equals("Restart")) {
            for(int i = 0; i < size; i++)
                for(int j = 0; j < size; j++)
                    board[i][j].color = "G";

            aiCountB = 0;
            aiCountW = 0;
            for (int i = 0; i < size; i++) {
                if (i % 2 == 0) {
                    this.board[0][i] = new Queen(0, i, "W", aiCountW);
                    AI_W[aiCountW++] = this.board[0][i];
                    this.board[size - 1][i] = new Queen(size - 1, i, "B", aiCountB);
                    AI_B[aiCountB++] = this.board[size - 1][i];
                } else {
                    this.board[0][i] = new Queen(0, i, "B", aiCountB);
                    AI_B[aiCountB++] = this.board[0][i];
                    this.board[size - 1][i] = new Queen(size - 1, i, "W", aiCountW);
                    AI_W[aiCountW++] = this.board[size - 1][i];
                }
            }

            this.board[size / 2][0] = new Queen(size / 2, 0, "W", aiCountW);
            AI_W[aiCountW++] = board[size / 2][0];
            this.board[size / 2][size - 1] = new Queen(size / 2, size - 1, "B", aiCountB);
            AI_B[aiCountB++] = board[size / 2][size - 1];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (this.board[i][j] == null) {
                        this.board[i][j] = new Queen(i, j, "G", 100);
                    }
                }
                mouseUsed = 0;
            }
            gameWindow.paintComponents(gameWindow.getGraphics());
            gameText.setText("Choose a White Queen");
            if(win) {
                addMouseListener(this);
                win = false;
            }
        }
    }
}