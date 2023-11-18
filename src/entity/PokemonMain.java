package entity;

import java.util.*;
public class PokemonMain {
    public static void main(String[] args){
        Fire fireType = new Fire("Fire");
        Flamethrower flamethrower = new Flamethrower(70, 100, "Flamethrower", true, fireType);
        Charmander charmander = new Charmander(20,20,20,20,20,20,fireType, flamethrower);
        System.out.println(charmander.getFire().getType());
        System.out.println(damageCalc());
        System.out.println(charmander.getMove1().isSpecial());
    }
    public static int damageCalc(){ //int userAttack, int opponentDef, Types moveType, Types opponentType
        Random rand = new Random();
        int critRoll = rand.nextInt(100);
        int damRoll = rand.nextInt(16);
        System.out.println(critRoll);
        System.out.println();
        return damRoll+85;
    }
}
