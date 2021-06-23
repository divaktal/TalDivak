import java.io.OptionalDataException;
import java.util.Arrays;
import java.util.List;


public class AI {
    public static Game game;

    public AI(Game newGame) {
        this.game = newGame;
    }

    public static void updateBoard(Queen[] arr, Queen[][] board, Queen from, Queen to) {
        int row = from.row, col = from.col;

        from.row = to.row;
        from.col = to.col;

        to.row = row;
        to.col = col;

        board[to.row][to.col] = to;
        board[from.row][from.col] = from;

        arr[from.id].col = from.col;
        arr[from.id].row = from.row;
    }

    public static Queen[] copyArr(Queen[] arr) {
        Queen[] copiedArr = new Queen[arr.length];
        for (int i = 0; i < arr.length; i++) {
            Queen copy = new Queen(arr[i].row, arr[i].col, arr[i].color, arr[i].id);
            copiedArr[i] = copy;
        }
        return copiedArr;
    }

    public static Queen[][] copyBoard(Queen[][] board) {
        Queen[][] copy = new Queen[game.size][game.size];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) {
                Queen q = new Queen(board[i][j].row, board[i][j].col, board[i][j].color, board[i][j].id);
                copy[i][j] = q;
            }
        return copy;
    }

    public static int pointPlace(Queen[][] board, Queen q) {
        int ally, enemy, score = 0;
        //horizantal
        for (int row = 0; row < game.size; row++) {
                for (int col = 0; col <= game.size - game.winCon; col++) {
                    Queen[] rowCount = {board[row][col], board[row][col + 1], board[row][col + 2], board[row][col + 3]};
                    ally = 0;
                    enemy = 0;
                    for (int i = 0; i < rowCount.length; i++) {
                        if (rowCount[i].color.equals(q.color))
                            ally++;
                        else if (!rowCount[i].color.equals("G") && !rowCount[i].color.equals("R"))
                            enemy++;
                    }
                    if (ally == 4)
                        score += 100000;
                    if (enemy == 3 && ally == 1)
                        score += 100;
                    score += 2 * ally - enemy;
                }
            }
        //vertical
        for (int col = 0; col < game.size; col++) {
            for (int row = 0; row <= game.size - game.winCon; row++) {
                Queen[] colCount = {board[row][col], board[row + 1][col], board[row + 2][col], board[row + 3][col]};
                ally = 0;
                enemy = 0;
                for (int i = 0; i < colCount.length; i++) {
                    if (colCount[i].color.equals(q.color))
                        ally++;
                    else if (!colCount[i].color.equals("G") && !colCount[i].color.equals("R"))
                        enemy++;
                }
                if (ally == 4)
                    score += 100000;
                if (enemy == 3 && ally == 1)
                    score += 100;
                score += 2 * ally - enemy;
            }
        }

        //diagonal
        for (int row = 0; row <= game.size - game.winCon; row++){
            for (int col = 0; col <= game.size - game.winCon; col++){
                ally = 0;
                enemy = 0;
                Queen[] diagonalCount = {board[row][col], board[row + 1][col + 1], board[row + 2][col + 2], board[row + 3][col + 3]};
                for (int i = 0; i < diagonalCount.length; i++) {
                    if (diagonalCount[i].color.equals(q.color))
                        ally++;
                    else if (!diagonalCount[i].color.equals("G") && !diagonalCount[i].color.equals("R"))
                        enemy++;
                }
                if (ally == 4)
                    score += 100000;
                if (enemy == 3 && ally == 1)
                    score += 100;
                score += 2 * ally - enemy;
            }
        }

        //negDiagonal
        for (int row = 0; row < game.size/2; row++){
            for (int col = game.size - 2; col < game.size; col++){
                ally = 0;
                enemy = 0;
                Queen[] negDiagonalCount = {board[row][col], board[row + 1][col - 1], board[row + 2][col - 2], board[row + 3][col - 3]};
                for (int i = 0; i < negDiagonalCount.length; i++) {
                    if (negDiagonalCount[i].color.equals(q.color))
                        ally++;
                    else if (!negDiagonalCount[i].color.equals("G") && !negDiagonalCount[i].color.equals("R"))
                        enemy++;
                }
                if (ally == 4)
                    score += 100000;
                if (enemy == 3 && ally == 1)
                    score += 100;
                score += 2 * ally - enemy;
            }
        }
        return score;
    }


    public static boolean isTerminal(Queen[][] currentBoard) {
        return game.winGame(currentBoard);
    }

    public int minimax(int depth, Queen[][] currentBoard, Queen[] currentBlack, Queen[] currentWhite, boolean aiTurn) {
        if (depth == 0 || isTerminal(currentBoard)) {
            if (depth == 0)
                return 0;
        }
        Queen[][] maxBoard = new Queen[0][];
        Queen[] maxBlack = new Queen[0];
        Queen[] maxWhite = new Queen[0];
        int maxScore = -1000000;
        if(aiTurn) {
            for (int bQueen = 0; bQueen < currentBlack.length; bQueen++) {
                Queen[] allMoves = Game.allMoves(currentBoard, currentBlack[bQueen]);
                for (int move = 0; move < allMoves.length; move++) {
                    Queen[][] copyBoard = copyBoard(currentBoard);
                    Queen[] copyBlack = copyArr(currentBlack);
                    //System.out.print("from :" + copyBlack[bQueen].row + "," + copyBlack[bQueen].col + " | to : " + allMoves[move].row + "," + allMoves[move].col);
                    updateBoard(copyBlack, copyBoard, copyBlack[bQueen], allMoves[move]);
                    int score = pointPlace(copyBoard, copyBlack[bQueen]) - minimax(depth - 1, copyBoard, copyBlack, currentWhite, false);
                    //System.out.println(" score : " + score);
                    //System.out.println("---------------");
                    if (maxScore < score) {
                        maxScore = score;
                        maxBoard = copyBoard;
                        maxBlack = copyBlack;
                    }
                }
            }
        }

        else if(!aiTurn){
            maxScore = -1000000;
            for (int wQueen = 0; wQueen < currentWhite.length; wQueen++) {
                Queen[] allMoves = Game.allMoves(currentBoard, currentWhite[wQueen]);
                for (int move = 0; move < allMoves.length; move++) {
                    Queen[][] copyBoard = copyBoard(currentBoard);
                    Queen[] copyWhite = copyArr(currentWhite);
                    updateBoard(copyWhite, copyBoard, copyWhite[wQueen], allMoves[move]);
                    int score = pointPlace(copyBoard, copyWhite[wQueen]) - minimax(depth - 1, copyBoard, currentBlack, copyWhite, true);
                    if (maxScore < score) {
                        maxScore = score;
                        maxBoard = copyBoard;
                    }
                }

            }
        }

        game.board = maxBoard;
        game.AI_B = maxBlack;
        return maxScore;
    }
}