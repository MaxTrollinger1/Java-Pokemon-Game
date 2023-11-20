package entity;

import java.awt.image.BufferedImage;

public class Pokemon {

    private String name;
    private BufferedImage sprite;
    private Moves primaryMove;
    private Moves secondaryMove;
    private int HP;
    private int Attack;
    private int Defense;
    private int Speed;
    private int SpecAttack;
    private int SpecDefense;

    public Pokemon(String name, BufferedImage sprite, int HP, Moves primaryMove, Moves secondaryMove){
        this.name = name;
        this.sprite = sprite;
        this.HP = HP;
        this.primaryMove = primaryMove;
        this.secondaryMove = secondaryMove;
    }

    public String getName() { return this.name;}
    public BufferedImage getSprite() {return this.sprite;}
    public Moves getPrimaryMove() { return this.primaryMove;}
    public Moves getSecondaryMove() { return this.secondaryMove;}
    public int getHP() {
        return HP;
    }
    public int getAttack() {
        return Attack;
    }
    public int getDefense() {
        return Defense;
    }
    public int getSpeed() {
        return Speed;
    }
    public int getSpecAttack() {
        return SpecAttack;
    }
    public int getSpecDefense() {
        return SpecDefense;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }
    public void setAttack(int attack) {
        Attack = attack;
    }
    public void setDefense(int defense) {
        Defense = defense;
    }
    public void setSpeed(int speed) {
        Speed = speed;
    }
    public void setSpecAttack(int specAttack) {
        SpecAttack = specAttack;
    }
    public void setSpecDefense(int specDefense) {
        SpecDefense = specDefense;
    }

}
