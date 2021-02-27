package quench.contents.types;
import quench.contents.blocks.*;

public class BlockData{
    public String name;
    public LargeMachinery block;
    int x,y;
    public BlockData(LargeMachinery block,String name,int x,int y){
        QULargeMachinery().load();
        this.block = block;
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public BlockData(String name,int x,int y){
        QULargeMachinery().load();
        this.block = QULargeMachinery.basicBlock;
        this.name = name;
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
        return x;
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
        return y;
    }
}