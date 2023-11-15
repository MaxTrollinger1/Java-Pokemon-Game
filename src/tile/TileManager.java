package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public String mapFileName = "world01.txt";

    public TileManager(GamePanel gp)
    {
        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap(mapFileName);
    }

    public void getTileImage()
    {
        try
        {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Grass.png"));
            tile[0].collision = false;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/triggers/Tall_Grass_01.png"));
            tile[1].isTrigger = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/Water.png"));
            tile[2].collision = true;

            LoadSpecificTiles();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapName)
    {
        try{
            //Import map
            InputStream is = getClass().getClassLoader().getResourceAsStream("maps/" + mapName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) // Text File Scan
            {
                String line = br.readLine();

                while(col < gp.maxWorldCol)
                {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch(Exception e)
        {

        }
    }

    public void draw(Graphics2D g2)
    {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)
        {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.scaledTileSize;
            int worldY = worldRow * gp.scaledTileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.scaledTileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.scaledTileSize * 2 < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.scaledTileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.scaledTileSize * 2 < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.scaledTileSize, gp.scaledTileSize, null);
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol)
            {
                worldCol = 0;
                worldRow++;
            }
        }
    }


    void LoadSpecificTiles() {
        try {
            String[][] tileData = {
                    {"3", "tiles/cliffs/Hills_Down_L.png", "true"},
                    {"4", "tiles/cliffs/Hills_Down_M.png", "true"},
                    {"5", "tiles/cliffs/Hills_Down_R.png", "true"},
                    {"0", "tiles/cliffs/Hills_Grass.png", "false"},
                    {"7", "tiles/cliffs/Hills_Middle_L.png", "true"},
                    {"8", "tiles/cliffs/Hills_Middle_R.png", "true"},
                    {"9", "tiles/cliffs/Hills_Top_L.png", "true"},
                    {"10", "tiles/cliffs/Hills_Top_M.png", "true"},
                    {"11", "tiles/cliffs/Hills_Top_R.png", "true"},
                    {"12", "tiles/cliffs/TreeBottom.png", "true"},
                    {"13", "tiles/cliffs/TreeTop.png", "true"},
                    {"14", "tiles/cliffs/Hills_Down_L_Water.png", "true"},
                    {"15", "tiles/cliffs/Hills_Down_R_Water.png", "true"},
                    {"16", "tiles/cliffs/Hills_Grass_2.png", "false"},
                    {"17", "tiles/cliffs/Hills_Grass_3.png", "false"},
                    {"18", "tiles/buildings/House_Top_L.png", "true"},
                    {"19", "tiles/buildings/House_Top_M.png", "true"},
                    {"20", "tiles/buildings/House_Top_R.png", "true"},
                    {"21", "tiles/buildings/House_Mid_L.png", "true"},
                    {"22", "tiles/buildings/House_Mid_M.png", "true"},
                    {"23", "tiles/buildings/House_Mid_R.png", "true"},
                    {"24", "tiles/buildings/House_Bottom_L.png", "true"},
                    {"25", "tiles/buildings/House_Bottom_M.png", "true"},
                    {"26", "tiles/buildings/House_Bottom_R.png", "false"},
                    {"27", "tiles/paths/Path_Bottom_L.png", "false"},
                    {"28", "tiles/paths/Path_Bottom_M.png", "false"},
                    {"29", "tiles/paths/Path_Bottom_R.png", "false"},
                    {"30", "tiles/paths/Path_Middle_L.png", "false"},
                    {"31", "tiles/paths/Path_Middle.png", "false"},
                    {"32", "tiles/paths/Path_Middle_R.png", "false"},
                    {"33", "tiles/paths/Path_Top_L.png", "false"},
                    {"34", "tiles/paths/Path_Top_M.png", "false"},
                    {"35", "tiles/paths/Path_Top_R.png", "false"}
            };

            for (String[] data : tileData) {
                int tileIndex = Integer.parseInt(data[0]);
                String imagePath = data[1];
                boolean collision = Boolean.parseBoolean(data[2]);

                tile[tileIndex] = new Tile();
                tile[tileIndex].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath));
                tile[tileIndex].collision = collision;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
