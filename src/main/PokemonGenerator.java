package main;

import entity.Moves;
import entity.Pokemon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PokemonGenerator {

    public static Pokemon[] starters = new Pokemon[3];
    public static void GeneratePokemon()
    {
        GenerateStarters();
    }

    private static void GenerateStarters()
    {
        BufferedImage bulbSprite;
        BufferedImage squirtleSprite;
        BufferedImage charmanderSprite;

        try{
            //Starters
            bulbSprite = ImageIO.read(PokemonGenerator.class.getClassLoader().getResourceAsStream("sprites/bulbasaur.png"));
            squirtleSprite = ImageIO.read(PokemonGenerator.class.getClassLoader().getResourceAsStream("sprites/squirtle.png"));
            charmanderSprite = ImageIO.read(PokemonGenerator.class.getClassLoader().getResourceAsStream("sprites/charmander.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Moves primaryMove = new Moves("Tackle", 10, 80, 19);
        Moves primaryMoveTwo = new Moves("Scratch", 8, 90, 9);


        Moves secondaryMove = new Moves("Vine Whip", 15, 65, 20);
        Moves squirtleSecondaryMove = new Moves("Water Pulse", 18, 65, 21);
        Moves charmanderSecondaryMove = new Moves("Flamethrower", 22, 55, 22);

        Pokemon Bulbasaur = new Pokemon("Bulbasaur", bulbSprite, 100, primaryMove, secondaryMove);
        Pokemon Squirtle = new Pokemon("Squirtle", squirtleSprite, 100, primaryMove, squirtleSecondaryMove);
        Pokemon Charmander = new Pokemon("Charmander", charmanderSprite, 100, primaryMoveTwo, charmanderSecondaryMove);

        starters[0] = Bulbasaur;
        starters[1] = Squirtle;
        starters[2] = Charmander;
    }

    public static void SetupEnemy(GamePanel gp)
    {
        BufferedImage eeveeSprite;
        BufferedImage pichuSprite;
        BufferedImage pidgeySprite;
        BufferedImage taurosSprite;
        BufferedImage woobatSprite;

        try{
            eeveeSprite = ImageIO.read(PokemonGenerator.class.getClassLoader().getResourceAsStream("sprites/eevee.png"));
            pichuSprite = ImageIO.read(PokemonGenerator.class.getClassLoader().getResourceAsStream("sprites/pichu.png"));
            pidgeySprite = ImageIO.read(PokemonGenerator.class.getClassLoader().getResourceAsStream("sprites/pidgey.png"));
            taurosSprite = ImageIO.read(PokemonGenerator.class.getClassLoader().getResourceAsStream("sprites/tauros.png"));
            woobatSprite = ImageIO.read(PokemonGenerator.class.getClassLoader().getResourceAsStream("sprites/woobat.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Moves[] moves = new Moves[11];
        moves[0] = new Moves("Slam", 6, 90, 10);
        moves[1] = new Moves("Cut", 14, 80, 11);
        moves[2] = new Moves("Growl", 16, 70, 12);
        moves[3] = new Moves("Headbutt", 20, 50,13);
        moves[4] = new Moves("Fang", 15, 65,14);
        moves[5] = new Moves("Pound", 13, 80, 15);
        moves[6] = new Moves("Rage", 15, 65, 16);
        moves[7] = new Moves("Razor Wind", 12, 65, 17);
        moves[8] = new Moves("Roar", 22, 50, 18);
        moves[9] = new Moves("Scratch", 10, 90, 9);

        gp.enemies[0] = new Enemy("Pidgey", pidgeySprite, 80, moves[7], moves[9]);
        gp.enemies[1] = new Enemy("Tauros", taurosSprite, 110, moves[0], moves[3]);
        gp.enemies[2] = new Enemy("Woobat", woobatSprite, 60, moves[1], moves[9]);
        gp.enemies[3] = new Enemy("Pichu", pichuSprite, 80, moves[4], moves[9]);
        gp.enemies[4] = new Enemy("Eevee", eeveeSprite, 90, moves[8], moves[5]);

    }

}
