package quench.contents;

public class Setting{
    public boolean buildingTips;//搭建提示
    public boolean mistakeTips;//错误提示
    public boolean statisticalPower;//统计电力
    public boolean statisticalMotive;//统计动力
    public boolean displayWindow;//显示窗口
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