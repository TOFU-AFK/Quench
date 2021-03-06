package quench.contents;

public class Setting{
    public boolean buildingTips;
    public boolean mistakeTips;
    public boolean statisticalPower;
    public boolean statisticalMotive;
    public boolean displayWindow;
    public Setting(){
        buildingTips = true;
        mistakeTips = true;
        statisticalPower = true;
        statisticalMotive = true;
        displayWindow = true;
    }
    
    public String toString(){
        return String.valueOf(buildingTips)+"\n"+String.valueOf(mistakeTips)+"\n"+String.valueOf(statisticalPower)+"\n"+String.valueOf(statisticalMotive)+"\n"+String.valueOf(displayWindow);
    }
}