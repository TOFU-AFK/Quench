package quench.contents.types;

public class BlockData{
    public String name;
    public LargeMachinery block;
    int x,y;
    public BlockData(LargeMachinery block,int x,int y){
        this.name = block.name;
        this.x = x;
        this.y = y;
    }
    
    public int x(int direction){
        switch (direction){
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return -x;
            case 3:
                return -y;
        }
    }
    
    public int y(int direction){
        switch (direction){
            case 0:
                return y;
            case 1:
                return x;
            case 2:
                return -y;
            case 3:
                return -x;
        }
    }
}