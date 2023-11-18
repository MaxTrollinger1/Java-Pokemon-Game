package entity;

public class Moves {
    private int basePower;
    private int Accuracy;
    private String moveName;
    private boolean isSpecial;


    public Moves(int basePower, int Accuracy, String moveName, boolean isSpecial){
        this.Accuracy = Accuracy;
        this.basePower = basePower;
        this.moveName = moveName;
        this.isSpecial = isSpecial;
    }

    public int getBasePower() {
        return basePower;
    }

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
class Flamethrower extends Moves{
    private Fire fireType;

    public Flamethrower(int basePower, int Accuracy, String moveName, boolean isSpecial, Fire fireType){
        super(basePower, Accuracy, moveName, isSpecial);
        this.fireType = fireType;
    }

    public Fire getFireType() {
        return fireType;
    }

    public void setFireType(Fire fireType) {
        this.fireType = fireType;
    }


}
