package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;
    public CollisionChecker(GamePanel gp)
    {
        this.gp = gp;
    }

    public void checkTile(Entity entity)
    {
        /*
         * Checks for collision and triggers for the entity based on its movement.
         *
         * @param entity The entity to check for collision and triggers.
         *
         * Algorithm - Check the next tile over from the players position plus the players direction
         * to determine if that tile is marked as a collision if so, block the player's movement.
         */

        // gather entity's position, create a box around the entity to show where is solid
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        // get the row and cols
        int entityLeftCol = entityLeftWorldX/gp.scaledTileSize;
        int entityRightCol = entityRightWorldX/gp.scaledTileSize;
        int entityTopRow = entityTopWorldY/gp.scaledTileSize;
        int entityBottomRow = entityBottomWorldY/gp.scaledTileSize;

        int tileNum1, tileNum2;

        // Check Direction
        switch (entity.direction)
        {
            case "up":
                // determine if solid or trigger
                entityTopRow = (entityTopWorldY - entity.speed)/gp.scaledTileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) //Check Coll
                {
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tileNum1].isTrigger == true || gp.tileM.tile[tileNum2].isTrigger == true) //Check Coll
                {
                    entity.triggerOn = true;
                }
                break;
            case "down":
                // determine if solid or trigger
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.scaledTileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) //Check Coll
                {
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tileNum1].isTrigger == true || gp.tileM.tile[tileNum2].isTrigger == true) //Check Coll
                {
                    entity.triggerOn = true;
                }
                break;
            case "left":
                // determine if solid or trigger
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.scaledTileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) //Check Coll
                {
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tileNum1].isTrigger == true || gp.tileM.tile[tileNum2].isTrigger == true) //Check Coll
                {
                    entity.triggerOn = true;
                }
                break;
            case "right":
                // determine if solid or trigger
                entityRightCol = (entityRightWorldX + entity.speed)/gp.scaledTileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) //Check Coll
                {
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tileNum1].isTrigger == true || gp.tileM.tile[tileNum2].isTrigger == true) //Check Coll
                {
                    entity.triggerOn = true;
                }
                break;
        }
    }
}
