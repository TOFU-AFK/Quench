package quench.contents.types;

public class BlockData{
    public String name;
    public LargeMachineryBuild build;
    public int x,y;
    public BlockData(LargeMachineryBuild build,int x,int y){
        this.name = build.getBlock().name;
        this.x = x;
        this.y = y;
    }
}