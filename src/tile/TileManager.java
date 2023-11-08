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

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize * 2 < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize * 2 < gp.player.worldY + gp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol)
            {
                worldCol = 0;
                worldRow++;
            }
        }
    }


    void LoadSpecificTiles()
    {
        try {
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Down_L.png"));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Down_M.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Down_R.png"));
            tile[5].collision = true;

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Grass.png"));
            tile[0].collision = false;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Middle_L.png"));
            tile[7].collision = true;

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Middle_R.png"));
            tile[8].collision = true;

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Top_L.png"));
            tile[9].collision = true;

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Top_M.png"));
            tile[10].collision = true;

            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Top_R.png"));
            tile[11].collision = true;

            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/TreeBottom.png"));
            tile[12].collision = true;

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/TreeTop.png"));
            tile[13].collision = true;

            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Down_L_Water.png"));
            tile[14].collision = true;

            tile[15] = new Tile();
            tile[15].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Down_R_Water.png"));
            tile[15].collision = true;

            tile[16] = new Tile();
            tile[16].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Grass_2.png"));
            tile[16].collision = false;

            tile[17] = new Tile();
            tile[17].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/cliffs/Hills_Grass_3.png"));
            tile[17].collision = false;

            tile[18] = new Tile();
            tile[18].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/buildings/House_Top_L.png"));
            tile[18].collision = true;

            tile[19] = new Tile();
            tile[19].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/buildings/House_Top_M.png"));
            tile[19].collision = true;

            tile[20] = new Tile();
            tile[20].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/buildings/House_Top_R.png"));
            tile[20].collision = true;

            tile[21] = new Tile();
            tile[21].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/buildings/House_Mid_L.png"));
            tile[21].collision = true;

            tile[22] = new Tile();
            tile[22].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/buildings/House_Mid_M.png"));
            tile[22].collision = true;

            tile[23] = new Tile();
            tile[23].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/buildings/House_Mid_R.png"));
            tile[23].collision = true;

            tile[24] = new Tile();
            tile[24].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/buildings/House_Bottom_L.png"));
            tile[24].collision = true;

            tile[25] = new Tile();
            tile[25].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/buildings/House_Bottom_M.png"));
            tile[25].collision = true;

            tile[26] = new Tile();
            tile[26].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/buildings/House_Bottom_R.png"));
            tile[26].collision = false;

            // Paths
            tile[27] = new Tile();
            tile[27].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/paths/Path_Bottom_L.png"));
            tile[27].collision = false;

            tile[28] = new Tile();
            tile[28].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/paths/Path_Bottom_M.png"));
            tile[28].collision = false;

            tile[29] = new Tile();
            tile[29].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/paths/Path_Bottom_R.png"));
            tile[29].collision = false;

            tile[30] = new Tile();
            tile[30].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/paths/Path_Middle_L.png"));
            tile[30].collision = false;

            tile[31] = new Tile();
            tile[31].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/paths/Path_Middle.png"));
            tile[31].collision = false;

            tile[32] = new Tile();
            tile[32].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/paths/Path_Middle_R.png"));
            tile[32].collision = false;

            tile[33] = new Tile();
            tile[33].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/paths/Path_Top_L.png"));
            tile[33].collision = false;

            tile[34] = new Tile();
            tile[34].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/paths/Path_Top_M.png"));
            tile[34].collision = false;

            tile[35] = new Tile();
            tile[35].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/paths/Path_Top_R.png"));
            tile[35].collision = false;

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
