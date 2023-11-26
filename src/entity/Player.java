package entity;

import main.Enemy;
import main.GamePanel;
import main.KeyHandler;
import main.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public Pokemon currentStarter;

    public final int screenX;
    public final int screenY;

    boolean playingFootsteps = false;
    int curFootstep = -1;

    // Trigger Check
    public final int maxTriggerCheck = 120; // Every 2 Seconds Check For Trigger
    int curTriggerCheck = 0;

    // Game Options

    public final int WildAttackPercentage = 50; // Percentage / 100

    public Player(GamePanel gp, KeyHandler keyH)
    {
        this.gp = gp;
        this.keyH = keyH;
        sizeModifier = 1;

        screenX = gp.screenWidth / 2 - (gp.scaledTileSize * sizeModifier / 2);
        screenY = gp.screenHeight / 2 - (gp.scaledTileSize * sizeModifier / 2);

        solidArea = new Rectangle(8, 30, 25, 20); // Create our collision

        setDefaultValues();
        getPlayerSpriteSheet();
    }

    public void setDefaultValues()
    {
        worldX = gp.scaledTileSize * 23;
        worldY = gp.scaledTileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerSpriteSheet()
    {
        // Load players sprite sheet
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
        // UPDATE is called 60 times a second from the GamePanel

        // Handle Input here
        curTriggerCheck++;
        if(keyH.upInput) { direction = "up"; }
        else if (keyH.downInput) { direction = "down"; }
        else if (keyH.leftInput) { direction = "left"; }
        else if (keyH.rightInput) { direction = "right"; }
        else //No keys pressed
        {
            direction = "idle";
        }

        if(gp.gameState != gp.inGameState) direction = "idle";

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

        if(curTriggerCheck > maxTriggerCheck)
        {
            if(triggerOn)
            {
                CheckBattle();
            }
            curTriggerCheck = 0;
        }


        // Increment the sprite counter to make the player animate
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
        // Draw player
        BufferedImage image = null;

        BufferedImage[] images = null;

        switch (direction) {
            case "up":
                images = new BufferedImage[]{up1, up2, up3, up4};
                break;
            case "down":
                images = new BufferedImage[]{down1, down2, down3, down4};
                break;
            case "left":
                images = new BufferedImage[]{left1, left2, left3, left4};
                break;
            case "right":
                images = new BufferedImage[]{right1, right2, right3, right4};
                break;
            default:
                images = new BufferedImage[]{idle1, idle2, idle3, idle4};
                break;
        }

        if (spriteNum >= 1 && spriteNum <= images.length) {
            image = images[spriteNum - 1];
        }

        // Draw Players Shadow
        if(!triggerOn) g2.drawImage(shadow, screenX, screenY, gp.scaledTileSize * sizeModifier, gp.scaledTileSize * sizeModifier + 5, null);

        // Draw Player
        g2.drawImage(image, screenX, screenY, gp.scaledTileSize * sizeModifier, gp.scaledTileSize * sizeModifier, null);
    }

    public void handleFootsteps() {
        // Control footsteps using a boolean gate of playingFootsteps
        if(isMoving && !playingFootsteps)
        {
            playingFootsteps = true;
            curFootstep = triggerOn ? 2 : 1;
            gp.playLoopSFX(curFootstep, 0.2f);
        }
        else if ((!isMoving || ( triggerOn && curFootstep != 2) || ( !triggerOn && curFootstep != 1)) && playingFootsteps)
        {
            playingFootsteps = false;
            gp.stopLoopSFX();
        }
    }

    public void CheckBattle()
    {
        // Check if the player has triggered a battle
        int ran = (int)(Math.random()*100);

        if(ran < WildAttackPercentage)
        {
            Logger.LogMessage("Load Battle Scene");
            gp.HandleStateChange(gp.battleState);
            isMoving = false;
            playingFootsteps = false;
        }
    }
}
