package quench.contents.types;
import java.util.*;

public class Structure{
    public BlockData[] datas;
    public int coreX,coreY;
    public Structure(BlockData[] datas)
    {
        battery = new ArrayList<LargeMachinery>();
        this.datas = datas;
    }
}