package entity;

public class Moves {
    private int basePower;
    private int Accuracy;
    private String moveName;
    private int attackSound;
    private boolean isSpecial;


    public Moves(String moveName, int basePower, int Accuracy, int attackSound){
        this.basePower = basePower;
        this.moveName = moveName;
        this.Accuracy = Accuracy;
        this.attackSound = attackSound;
    }

    public int getBasePower() {
        return basePower;
    }
    public int getAttackSound() { return attackSound;}

    public int getAccuracy() {
        return Accuracy;
    }

    public String getMoveName() {
        return moveName;
    }

    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }

    public void setAccuracy(int accuracy) {
        Accuracy = accuracy;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public boolean isSpecial() {
        return isSpecial;
    }
}
