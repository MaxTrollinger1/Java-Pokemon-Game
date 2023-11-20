package main;

import entity.Moves;
import entity.Pokemon;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

public class Enemy extends Pokemon {

    Moves[] moves;
    public Enemy(String name, BufferedImage sprite, int HP, Moves primaryMove, Moves secondaryMove)
    {
        super(name, sprite, HP, primaryMove, secondaryMove);
    }

    public Moves getRandomMove() {
        int randomNumber = (int) (Math.random() * 2);
        return randomNumber == 0 ? getPrimaryMove() : getSecondaryMove();
    }
}

