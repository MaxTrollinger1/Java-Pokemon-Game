package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UIHandler {

    public GamePanel gp;
    public Graphics2D g2;
    Font pixel;

    public int commandNum = 0;

    public UIHandler(GamePanel gp)
    {
        this.gp = gp;

        //Loading Font
        InputStream is = getClass().getResourceAsStream("/font/pixel.ttf");

        try{
            pixel = Font.createFont(Font.TRUETYPE_FONT, is);
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
        else if (gp.gameState == gp.inGameState)
        {
            drawGame();
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

    public void onUIInput()
    {
        if(commandNum == 0)
        {
            gp.playSFX(4, 0.2f, false);
            gp.HandleStateChange(gp.inGameState);
        }
        else if(commandNum == 1)
        {
            gp.HandleStateChange(gp.exitingState);
        }
    }

    public void drawGame()
    {
        gp.setBackground(Color.decode("#9bd4c3"));
    }

    public int getXCenteredText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
