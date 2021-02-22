/*
 蓝图的type
 */
package fuleikeindustry.contents.types.structure.blueprint;
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
import mindustry.ui.*;
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

import quench.contents.types.largeMachinery.*;

import static mindustry.Vars.*;

public class Blueprint {
    //结构文本，是包含结构信息的JSON文本
    public String name;
    public MultipleBlock[] structure;
    public int[] x;//x坐标，以0开始
    public int[] y;//y坐标，以0开始
    
    //是否错误
    boolean error = false;
    //构造方法
    public Blueprint(String name) {
        this.name = name;
    }
    
    //得到名称
    public String getName(){
        return name;
    }
    
    //判断结构，请传控制核心的xy坐标！
    public boolean judge(int x,int y){
        if(x.length!=y.length) return false;
        if(x == null||y == null||structure == null) return false;
        if(structure.length!=x.length) return false;
        for(int i=0;structure.length;i++){
        Tile t = Vars.world.tile(tile.x+x[i],tile.y+y[i]);
        if(!t.block.name.equals(structure[i].name)){
            return false;
        }
        }
        return true;
    }

    //是否是错误结构(暂时没用)
    public boolean isError() {
        return error;
    }
}
