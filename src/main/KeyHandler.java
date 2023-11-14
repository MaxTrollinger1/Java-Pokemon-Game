package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    GamePanel gp;

    public KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //Title
        if(gp.gameState == gp.titleState)
        {
            if(code == KeyEvent.VK_W)
            {
                gp.uiHandler.commandNum--;
                gp.playSFX(3, 0.2f, false);

                if(gp.uiHandler.commandNum < 0)
                {
                    gp.uiHandler.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_S)
            {
                gp.uiHandler.commandNum++;
                gp.playSFX(3, 0.2f, false);

                if(gp.uiHandler.commandNum > 1)
                {
                    gp.uiHandler.commandNum = 0;
                }
            }

            if(code == KeyEvent.VK_ENTER)
            {
                gp.uiHandler.onUIInput();
            }
        }

        if(code == KeyEvent.VK_W)
        {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S)
        {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A)
        {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D)
        {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W)
        {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S)
        {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A)
        {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
    }
}
