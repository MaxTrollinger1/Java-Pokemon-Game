package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

public class UIHandler {

    public GamePanel gp;
    public Graphics2D g2;
    Font pixel;

    public int commandNum = 0;

    //RES
    BufferedImage selectionIcon;
    BufferedImage bSelectionIcon;
    BufferedImage battleBG;

    public String battleText = "";

    // Animation

    double currentAngle = 0;
    double currentEnemyAngle = 0;
    int currentPositionModifier = 0;
    int currentEnemyPositionModifier = 0;
    int maxLerpDistance = 5;
    float lerpSpeed = 0.08f;

    public UIHandler(GamePanel gp)
    {
        this.gp = gp;

        // Load Resources

        InputStream is = getClass().getResourceAsStream("/font/pixel.ttf");

        try{
            pixel = Font.createFont(Font.TRUETYPE_FONT, is);

            selectionIcon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ui/selection.png"));
            battleBG = ImageIO.read(getClass().getClassLoader().getResourceAsStream("scenes/BattleScene.png"));

            bSelectionIcon = selectionIcon;
            bSelectionIcon.setRGB(0,0,0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }

    }
    public void draw(Graphics2D g2)
    {
        // Determine which scene to draw based on gameState
        this.g2 = g2;

        if(gp.gameState == gp.titleState)
        {
            drawTitle();
        }
        else if (gp.gameState == gp.selectionState)
        {
            drawSelection();
        }
        else if (gp.gameState == gp.inGameState)
        {
            drawGame();
        }
        else if (gp.gameState == gp.battleState)
        {
            drawBattle();
        }
    }

    public void drawTitle()
    {
        // Handle drawing the title scene

        g2.setColor(Color.black);
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        g2.setFont(pixel);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));

        String text = "Pokemon";
        int x = getXCenteredText(text);
        int y = gp.scaledTileSize * 3;

        //Shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));

        text = "PLAY";
        x = getXCenteredText(text);
        y += gp.scaledTileSize * 4;
        g2.drawString(text, x, y);
        if(commandNum == 0)
        {
            g2.drawString(")", x - gp.scaledTileSize, y);
        }

        text = "EXIT";
        x = getXCenteredText(text);
        y += gp.scaledTileSize * 2;
        g2.drawString(text, x, y);

        if(commandNum == 1)
        {
            g2.drawString(")", x - gp.scaledTileSize, y);
        }
    }

    public void drawSelection()
    {
        // Handle drawing selection screen
        // Command num is the selection
        g2.setColor(Color.black);
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        g2.setFont(pixel);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));
        String text = "Select Your Starter";
        int x = getXCenteredText(text);
        int y = gp.scaledTileSize * 3;

        //Shadow
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //Selection

        x = gp.screenWidth/2 - (gp.tileSize*5) / 2;
        y += gp.tileSize*5;

        x -= gp.tileSize * 10;
        BufferedImage starterOne = PokemonGenerator.starters[0].getSprite();
        g2.drawImage(starterOne, x, y, gp.tileSize*5, gp.tileSize*5, null);

        if(commandNum == 0)
        {
            g2.drawImage(selectionIcon, x, y, gp.tileSize*5, gp.tileSize*5, null);
        }

        x += gp.tileSize * 10;
        BufferedImage starterTwo = PokemonGenerator.starters[1].getSprite();
        g2.drawImage(starterTwo, x, y, gp.tileSize*5, gp.tileSize*5, null);

        if(commandNum == 1)
        {
            g2.drawImage(selectionIcon, x, y, gp.tileSize*5, gp.tileSize*5, null);
        }

        x += gp.tileSize * 10;
        BufferedImage starterThree = PokemonGenerator.starters[2].getSprite();
        g2.drawImage(starterThree, x, y, gp.tileSize*5, gp.tileSize*5, null);

        if(commandNum == 2)
        {
            g2.drawImage(selectionIcon, x, y, gp.tileSize*5, gp.tileSize*5, null);
        }
    }

    public void onUIInput()
    {
        // Determine where to handle the UI Input
        if(gp.gameState == gp.titleState)
        {
            if(commandNum == 0)
            {
                gp.playSFX(4, 0.2f);
                gp.HandleStateChange(gp.selectionState);
            }
            else if(commandNum == 1)
            {
                gp.HandleStateChange(gp.exitingState);
            }
        }
        else if (gp.gameState == gp.selectionState)
        {
            gp.player.currentStarter = PokemonGenerator.starters[commandNum];

            gp.HandleStateChange(gp.inGameState);
        }
        else if (gp.gameState == gp.battleState)
        {
            if(commandNum == 0)
            {
                gp.battleManager.PlayerAttack(gp.player.currentStarter, gp.player.currentStarter.getPrimaryMove());
            }
            else if(commandNum == 1)
            {
                gp.battleManager.PlayerAttack(gp.player.currentStarter, gp.player.currentStarter.getSecondaryMove());
            }
            else if(commandNum == 2)
            {
                gp.playSFX(7, 0.2f);
                gp.HandleStateChange(gp.inGameState);
            }
        }
    }

    public void drawGame()
    {
        // Draw Game scene
        gp.setBackground(Color.decode("#9bd4c3"));
    }

    public void drawBattle()
    {
        // draw battle scene
        gp.setBackground(Color.black);
        // use x and y for positions of GUI
        int x = 0;
        int y = gp.screenHeight / 2;

        int scale = 37;

        x = gp.screenWidth/2 - (gp.tileSize*scale) / 2;
        y -= (gp.tileSize * scale) / 2;

        g2.drawImage(battleBG, x, y, gp.tileSize*scale, gp.tileSize*scale, null);


        // Drawing UI
        g2.setFont(pixel);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        g2.setColor(Color.black);

        if(gp.battleManager.showAttackUI)
        {
            int textPadding = 30;

            //Attack One
            String attackOneText = gp.player.currentStarter.getPrimaryMove().getMoveName();

            x = ((getXCenteredText(attackOneText) / 2) + textPadding) - 50;
            y = gp.screenHeight - 10;

            g2.setColor(Color.black);
            g2.drawString(attackOneText, x, y);

            if(commandNum == 0)
            {
                g2.drawString(")", x - gp.scaledTileSize / 3, y);
            }

            //Attack Two
            String attackTwoText = gp.player.currentStarter.getSecondaryMove().getMoveName();

            x = ((getXCenteredText(attackTwoText)) - textPadding) + 50;
            y = gp.screenHeight - 10;

            g2.setColor(Color.black);
            g2.drawString(attackTwoText, x, y);

            if(commandNum == 1)
            {
                g2.drawString(")", x - gp.scaledTileSize / 3, y);
            }

            //Run Option
            String runText = "Run";

            x = (((getXCenteredText(runText) / 2) + getXCenteredText(runText)) - textPadding) + 50;
            y = gp.screenHeight - 10;

            g2.setColor(Color.black);
            g2.drawString(runText, x, y);

            if(commandNum == 2)
            {
                g2.drawString(")", x - gp.scaledTileSize / 3, y);
            }
        }

        // Announcer Text

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25f));

        x = (gp.screenWidth / 2) - 250;
        y = gp.screenHeight - 80;
        g2.drawString(battleText, x, y);

        // Draw Pokemon

        // - - Starter

        double value = maxLerpDistance * Math.sin(currentAngle);
        currentAngle += lerpSpeed;
        currentPositionModifier = (int)value;

        x = (gp.screenWidth / 6) + currentPositionModifier;
        y = (int)(gp.screenHeight / 2.3);

        if(!gp.battleManager.battleOver) {
            g2.drawImage(gp.player.currentStarter.getSprite(), x, y, gp.tileSize * 15, gp.tileSize * 15, null);
        }
        else if(gp.battleManager.playerWon)
        {
            g2.drawImage(gp.player.currentStarter.getSprite(), x, y, gp.tileSize * 15, gp.tileSize * 15, null);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
        g2.setColor(Color.green);

        g2.drawString("HP " + Integer.toString(gp.battleManager.playerHealth), x, y);

        // -- Enemy

        double enemyValue = maxLerpDistance * Math.sin(currentEnemyAngle);
        currentEnemyAngle += lerpSpeed - 0.02;
        currentEnemyPositionModifier = (int)enemyValue;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        g2.setColor(Color.black);

        x = (int)(gp.screenWidth / 1.9) + currentEnemyPositionModifier;
        y = (int)(gp.screenHeight / 8);


        if(!gp.battleManager.battleOver)
        {
            if(gp.battleManager.currentEnemy != null) {
                g2.drawImage(gp.battleManager.currentEnemy.getSprite(), x, y, gp.tileSize*15, gp.tileSize*15, null);
            }
        }
        else if (!gp.battleManager.playerWon)
        {
            if(gp.battleManager.currentEnemy != null) {
                g2.drawImage(gp.battleManager.currentEnemy.getSprite(), x, y, gp.tileSize*15, gp.tileSize*15, null);
            }
        }
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
        g2.setColor(Color.red);

        g2.drawString("HP " + Integer.toString(gp.battleManager.enemyHealth), x, y);

    }

    public int getXCenteredText(String text)
    {
        // determine the center based upon a string and return it
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
