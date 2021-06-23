import javax.swing.*;

public class Game_GUI {

    public Game_GUI(int diff) {
        JFrame frame = new JFrame();
        Game game = new Game();

        frame.setBounds(0, game.tile / 2 + game.tile / 6, game.size * game.tile + game.tile / 16, game.size * game.tile + game.tile / 4);
        frame.setTitle("4 Queens");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);

        game.gameWindow = frame;
        if (diff == 0)
            game.startPvP();
        else
            game.startPvE(diff);
    }
}