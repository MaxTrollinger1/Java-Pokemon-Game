package main;

import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // Screen Settings
    final int tileSize = 16;
    final int scale = 3;
    final int fpsConstant = 1000000000;

    // 48x48
    public final int scaledTileSize = tileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = scaledTileSize * maxScreenCol; // 768 px
    public final int screenHeight = scaledTileSize * maxScreenRow; // 576 px

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    //FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);

    // Sound
    Sound sound = new Sound();
    Sound soundSFX = new Sound();

    // For Timing Purposes
    Thread gameThread;
    UIHandler uiHandler = new UIHandler(this);

    // Player
    public CollisionChecker cChecker = new CollisionChecker(this);
    KeyHandler keyH = new KeyHandler(this);
    public Player player = new Player(this, keyH);


    public int gameState;
    public final int exitingState = -1;
    public final int titleState = 0;
    public final int selectionState = 1;
    public final int inGameState = 2;
    public final int battleState = 3;

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
        startGameThread();
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start(); // Call Run Method Below
    }

    @Override
    public void run() { // Game Loop

        double drawInterval = (double)fpsConstant / FPS;
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

            }
            catch (InterruptedException e)
            {
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
        Graphics2D graphics = (Graphics2D)g;
        uiHandler.draw(graphics);

        if(gameState == inGameState)
        {
            tileM.draw(graphics); // Draw tiles first

            player.draw(graphics);
        }

        graphics.dispose(); // Memory Saving
    }

    public void HandleStateChange(int newState)
    {
        gameState = newState;

        if(gameState == inGameState)
        {
            stopMusic();
            playMusic(0, 0.05f);
        }
        else if(gameState == battleState)
        {
            stopMusic();
            playMusic(6, 0.03f);
        }
        else if(gameState == selectionState)
        {
            stopMusic();
            playMusic(5, 0.05f);
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
