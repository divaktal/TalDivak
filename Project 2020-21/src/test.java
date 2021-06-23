import javax.swing.*;
import java.awt.*;

public class test {
    public static void main(String[] args) {
        Game game = new Game();
        game.board[1][3].color = "B";
        game.board[2][2].color = "B";
        game.board[3][1].color = "B";
        game.board[4][0].color = "B";
        Game.printBoard(game.board);
        System.out.println(game.board[4][0].color);
        System.out.println(AI.pointPlace(game.board, game.board[4][0]));
    }
}

