package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    boolean playingFootsteps = false;
    int curFootstep = -1;

    public Player(GamePanel gp, KeyHandler keyH)
    {
        this.gp = gp;
        this.keyH = keyH;
        sizeModifier = 1;

        screenX = gp.screenWidth / 2 - (gp.tileSize * sizeModifier / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize * sizeModifier / 2);

        solidArea = new Rectangle(8, 30, 25, 20); // Create our collision

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues()
    {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage()
    {
        try{
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Up_01.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Up_02.png"));
            up3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Up_03.png"));
            up4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Up_04.png"));

            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Down_01.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Down_02.png"));
            down3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Down_03.png"));
            down4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Down_04.png"));

            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Right_01.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Right_02.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Right_03.png"));
            right4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Right_04.png"));

            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Left_01.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Left_02.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Left_03.png"));
            left4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Walk_Left_04.png"));

            idle1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Idle_01.png"));
            idle2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Idle_02.png"));
            idle3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Idle_03.png"));
            idle4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Idle_04.png"));

            shadow = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/Shadow.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update()
    {
        if(keyH.upPressed) { direction = "up"; }
        else if (keyH.downPressed) { direction = "down"; }
        else if (keyH.leftPressed) { direction = "left"; }
        else if (keyH.rightPressed) { direction = "right"; }
        else //No keys pressed
        {
            direction = "idle";
        }

        //Check Tile Collision
        collisionOn = false;
        triggerOn = false;
        gp.cChecker.checkTile(this);

        isMoving = !direction.equals("idle") && !collisionOn;
        handleFootsteps();

        // If Collision
        if(!collisionOn)
        {
            switch(direction)
            {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        spriteCounter++;
        if(spriteCounter > spriteUpdateInterval)
        {
            spriteNum += 1;

            if(spriteNum > 4)
            {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        switch (direction)
        {
            case "up":
                if(spriteNum == 1) image = up1;
                if(spriteNum == 2) image = up2;
                if(spriteNum == 3) image = up3;
                if(spriteNum == 4) image = up4;
                break;
            case "down":
                if(spriteNum == 1) image = down1;
                if(spriteNum == 2) image = down2;
                if(spriteNum == 3) image = down3;
                if(spriteNum == 4) image = down4;
                break;
            case "left":
                if(spriteNum == 1) image = left1;
                if(spriteNum == 2) image = left2;
                if(spriteNum == 3) image = left3;
                if(spriteNum == 4) image = left4;
                break;
            case "right":
                if(spriteNum == 1) image = right1;
                if(spriteNum == 2) image = right2;
                if(spriteNum == 3) image = right3;
                if(spriteNum == 4) image = right4;
                break;
            default:
                if(spriteNum == 1) image = idle1;
                if(spriteNum == 2) image = idle2;
                if(spriteNum == 3) image = idle3;
                if(spriteNum == 4) image = idle4;
                break;
        }

        if(!triggerOn) g2.drawImage(shadow, screenX, screenY, gp.tileSize * sizeModifier, gp.tileSize * sizeModifier + 5, null);

        g2.drawImage(image, screenX, screenY, gp.tileSize * sizeModifier, gp.tileSize * sizeModifier, null);
    }

    public void handleFootsteps() {
        if(isMoving && !playingFootsteps)
        {
            playingFootsteps = true;
            curFootstep = triggerOn ? 2 : 1;
            gp.playSFX(curFootstep, 0.2f, true);
        }
        else if ((!isMoving || ( triggerOn && curFootstep != 2) || ( !triggerOn && curFootstep != 1)) && playingFootsteps)
        {
            playingFootsteps = false;
            gp.stopSFX();
        }
    }
}
