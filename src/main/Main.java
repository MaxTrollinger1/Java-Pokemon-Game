package main;
import javax.swing.JFrame;
import java.awt.*;

public class Main {

    public static final String gameTitle = "";
    public static void main(String[] args)
    {
        packGame();
    }

    public static void packGame()
    {
        JFrame frame = new JFrame();

        // Set up the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle(gameTitle);

        // Load Behavior
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.pack();

        // Center to Middle
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start Behavior
        gamePanel.setupGame();
    }
}
