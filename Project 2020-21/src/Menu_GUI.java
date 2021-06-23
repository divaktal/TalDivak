import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class Menu_GUI extends JFrame implements ActionListener
{
    private JLabel titleL;
    private JButton startBPvP,startBPvE1,startBPvE2,startBPvE3, helpB, exitB;

    static JFrame frame1 = new JFrame();

    public Menu_GUI()
    {
        frame1.setSize(500,250);

        Container mainP = frame1.getContentPane();
        mainP.setLayout(null);

        titleL = new JLabel("4 Queens");
        startBPvP = new JButton("StartPvP");
        startBPvE1 = new JButton("StartPvE1");
        startBPvE2 = new JButton("StartPvE2");
        startBPvE3 = new JButton("StartPvE3");
        helpB = new JButton("Help");
        exitB = new JButton("Exit");


        mainP.add(titleL);
        titleL.setFont(new Font("Chiller",Font.BOLD,50));
        titleL.setBounds(175, 30, 200, 50);

        mainP.add(startBPvP);
        startBPvP.setMnemonic(KeyEvent.VK_A);
        startBPvP.setBounds(200, 80, 100, 20);

        mainP.add(startBPvE1);
        startBPvE1.setMnemonic(KeyEvent.VK_B);
        startBPvE1.setBounds(70, 110, 100, 20);

        mainP.add(startBPvE2);
        startBPvE2.setMnemonic(KeyEvent.VK_C);
        startBPvE2.setBounds(200, 110, 100, 20);

        mainP.add(startBPvE3);
        startBPvE3.setMnemonic(KeyEvent.VK_D);
        startBPvE3.setBounds(330, 110, 100, 20);

        mainP.add(helpB);
        helpB.setMnemonic(KeyEvent.VK_E);
        helpB.setBounds(200, 140, 100, 20);


        mainP.add(exitB);
        exitB.setMnemonic(KeyEvent.VK_F);
        exitB.setBounds(200, 170, 100, 20);


        startBPvP.addActionListener(this);
        startBPvE1.addActionListener(this);
        startBPvE2.addActionListener(this);
        startBPvE3.addActionListener(this);
        helpB.addActionListener(this);
        exitB.addActionListener(this);

        frame1.setVisible(true);
        frame1.setResizable(false);


    }


    public void actionPerformed(ActionEvent e)
    {
        String key = e.getActionCommand();

        if(key.equals("StartPvP"))
        {
            frame1.dispose();
            new Game_GUI(0);
        }
        else if(key.equals("StartPvE1")){
            frame1.dispose();
            new Game_GUI(1);
        }
        else if(key.equals("StartPvE2")){
            frame1.dispose();
            new Game_GUI(2);
        }
        else if(key.equals("StartPvE3")){
            frame1.dispose();
            new Game_GUI(3);
        }

        else if(key.equals("Help")) {
            JOptionPane.showMessageDialog(null, "/1/ you play by turns \n" +
                    "/2/ you can only pick your own color \n" +
                    "/3/ the pieces move like queen in chess and they cannot jump over a piece or on a piece \n" +
                    "/4/ the first one to complete a sequence of 4 of his own color wins \n" );
        }

        else
            System.exit(0);
    }
}