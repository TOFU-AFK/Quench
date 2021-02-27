package quench.contents.types;
import java.util.*;

public class Structure{
    public BlockData[] datas;
    public int coreX,coreY;
    public ArrayList<LargeMachinery> battery;//电池集合
    public Structure(BlockData[] datas)
    {
        battery = new ArrayList<LargeMachinery>();
        this.datas = datas;
    }
}