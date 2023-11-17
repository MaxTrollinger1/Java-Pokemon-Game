package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UIHandler {

    public GamePanel gp;
    public Graphics2D g2;
    Font pixel;

    public int commandNum = 0;

    //RES
    BufferedImage selectionIcon;
    BufferedImage battleBG;

    public UIHandler(GamePanel gp)
    {
        this.gp = gp;

        // Load Resources

        InputStream is = getClass().getResourceAsStream("/font/pixel.ttf");

        try{
            pixel = Font.createFont(Font.TRUETYPE_FONT, is);

            selectionIcon = ImageIO.read(getClass().getClassLoader().getResourceAsStream("ui/selection.png"));
            battleBG = ImageIO.read(getClass().getClassLoader().getResourceAsStream("scenes/BattleScene.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }

    }
    public void draw(Graphics2D g2)
    {
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
        g2.setColor(Color.black);
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        g2.setFont(pixel);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));

        String text = "331 Pokemon Game";
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
        BufferedImage starterOne = gp.player.down1;
        g2.drawImage(starterOne, x, y, gp.tileSize*5, gp.tileSize*5, null);

        if(commandNum == 0)
        {
            g2.drawImage(selectionIcon, x, y, gp.tileSize*5, gp.tileSize*5, null);
        }

        x += gp.tileSize * 10;
        BufferedImage starterTwo = gp.player.down2;
        g2.drawImage(starterTwo, x, y, gp.tileSize*5, gp.tileSize*5, null);

        if(commandNum == 1)
        {
            g2.drawImage(selectionIcon, x, y, gp.tileSize*5, gp.tileSize*5, null);
        }

        x += gp.tileSize * 10;
        BufferedImage starterThree = gp.player.down3;
        g2.drawImage(starterThree, x, y, gp.tileSize*5, gp.tileSize*5, null);

        if(commandNum == 2)
        {
            g2.drawImage(selectionIcon, x, y, gp.tileSize*5, gp.tileSize*5, null);
        }
    }

    public void onUIInput()
    {
        if(gp.gameState == gp.titleState)
        {
            if(commandNum == 0)
            {
                gp.playSFX(4, 0.2f, false);
                gp.HandleStateChange(gp.selectionState);
            }
            else if(commandNum == 1)
            {
                gp.HandleStateChange(gp.exitingState);
            }
        }
        else if (gp.gameState == gp.selectionState)
        {
            //Store Selection Here <------------------------------------------------

            gp.HandleStateChange(gp.inGameState);
        }
    }

    public void drawGame()
    {
        gp.setBackground(Color.decode("#9bd4c3"));
    }

    public void drawBattle()
    {
        gp.setBackground(Color.black);
        int x = 0;
        int y = gp.screenHeight / 2;

        int scale = 37;

        x = gp.screenWidth/2 - (gp.tileSize*scale) / 2;
        y -= (gp.tileSize * scale) / 2;

        g2.drawImage(battleBG, x, y, gp.tileSize*scale, gp.tileSize*scale, null);
    }

    public int getXCenteredText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
