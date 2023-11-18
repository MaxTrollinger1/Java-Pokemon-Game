package entity;

public class Pokemon {
    private int HP;
    private int Attack;
    private int Defense;
    private int Speed;
    private int SpecAttack;
    private int SpecDefense;

    public Pokemon(int HP, int Attack, int Defense, int Speed, int SpecAttack, int SpecDefense){
        this.HP = HP;
        this.Attack = Attack;
        this.Defense = Defense;
        this.Speed = Speed;
        this.SpecAttack = SpecAttack;
        this.SpecDefense = SpecDefense;
    }
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
class Charmander extends Pokemon{
    private Fire fire;
    private Moves move1;
    public Charmander(int HP, int Attack, int Defense, int Speed, int SpecAttack, int SpecDefense, Fire fire, Moves move1){
        super(HP, Attack, Defense, Speed, SpecAttack, SpecDefense);
        this.fire = fire;
        this.move1 = move1;
    }

    public Fire getFire() {
        return fire;
    }

    public void setFire(Fire fire) {
        this.fire = fire;
    }

    public Moves getMove1() {
        return move1;
    }

    public void setMove1(Moves move1) {
        this.move1 = move1;
    }
}
