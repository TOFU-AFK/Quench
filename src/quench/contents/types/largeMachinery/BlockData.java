package quench.contents.types;
import quench.contents.blocks.*;
import mindustry.world.*;
import mindustry.entities.*;
import mindustry.game.*;
import arc.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.func.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.Bits;
import arc.struct.*;
import arc.util.io.*;
import arc.scene.ui.*;
import mindustry.core.GameState.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mindustry.ctype.*;
import mindustry.content.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.experimental.*;
import mindustry.world.blocks.legacy.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.logic.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.core.*;
import mindustry.*;

import java.util.*;

public class BlockData{
    public String name;
    public LargeMachinery block;
    int x,y;
    //正常情况下请不要使用此变量，此变量专门用于黑名单
    public String n;
    public BlockData(LargeMachinery block,int x,int y){
        this.block = block;
        this.name = block.name;
        this.x = x;
        this.y = y;
    }
    
    //不要问为啥有这个构造方法，以前写的烂代码懒得改了
    public BlockData(String name,int x,int y){
        this.block = QULargeMachinery.basicBlock;
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    //正常情况下请不要使用此构造方法，此构造方法专门用于黑名单
    public BlockData(Block b){
        n = b.name;
    }
    
    public Tile tile(float coreX,float coreY){
        return Vars.world.tile((int) coreX+x/8,(int) coreY+y/8);
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