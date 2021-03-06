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
        return "建造提示: "+String.valueOf(buildingTips)+"\n"+"错误提示: "+String.valueOf(mistakeTips)+"\n"+"统计电力: "+String.valueOf(statisticalPower)+"\n"+"统计动力: "+String.valueOf(statisticalMotive)+"\n"+"显示窗口: "+String.valueOf(displayWindow);
    }
}