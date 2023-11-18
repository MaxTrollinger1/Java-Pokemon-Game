package entity;

import java.util.ArrayList;
import java.util.List;

public class Types {
    private String type;

    public Types(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
class Fire extends Types{
    List<String> weaknesses = new ArrayList<>();

    public Fire(String type){
        super(type);
    }

    public boolean checkWeakness(String checkType){
        weaknesses.add("water");
        if (weaknesses.contains(checkType)){
            return true;
        }
        return false;
    }
}