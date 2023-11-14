package main;

import entity.Player;
import tile.Tile;
import tile.TileManager;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // Screen Settings
    final int originalTileSize = 16; // 16x16 Tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 px
    public final int screenHeight = tileSize * maxScreenRow; // 576 px

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    Sound soundSFX = new Sound();
    Thread gameThread; // For Timing Purposes
    UIHandler uiHandler = new UIHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);

    public int gameState;
    public final int exitingState = -1;
    public final int titleState = 0;
    public final int inGameState = 1;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        gameState = titleState;
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start(); // Call Run Method Below
    }

    @Override
    public void run() { // Game Loop

        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) // Update ( Drawing and Updating Character Information)
        {
            update();

            //Draw scene
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; //Convert to milliseconds

                if(remainingTime < 0)
                {
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update()
    {
        if(gameState == inGameState) player.update();
    }

    public void paintComponent(Graphics g) // JPanel Method (call super class)
    {
        super.paintComponent(g);

        //Handle Player
        Graphics2D g2 = (Graphics2D)g;
        uiHandler.draw(g2);

        if(gameState == inGameState)
        {
            tileM.draw(g2); // Draw tiles first

            player.draw(g2);
        }

        g2.dispose(); // Memory Saving
    }

    public void HandleStateChange(int newState)
    {
        gameState = newState;

        if(gameState == inGameState)
        {
            playMusic(0, 0.05f);
        }
        else if(gameState == exitingState)
        {
            System.out.println("Exiting Game");
            System.exit(0);
        }
    }

    public void playMusic(int index, float volume)
    {
        sound.setFile(index);
        sound.play(volume);
        sound.loop();
    }

    public void stopMusic()
    {
        sound.stop();
    }

    public void playSFX(int index, float volume, boolean loop)
    {
        soundSFX.setFile(index);
        soundSFX.play(volume);
        if(loop) soundSFX.loop();
    }

    public void stopSFX()
    {
        soundSFX.stop();
    }

}
