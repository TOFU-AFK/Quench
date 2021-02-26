package quench.contents.types;

public class BlockData{
    public String name;
    public LargeMachinery block;
    public int x,y;
    public BlockData(LargeMachinery block,int x,int y){
        this.name = block.name;
        this.x = x;
        this.y = y;
    }
}