/*
*大型机械的核心type
*/
package quench.contents.types;

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

import static mindustry.Vars.*;

public class MechanicalCore extends LargeMachinery{
    public Structure structure;
    public MechanicalCore(String name){
        super(name);
        solid = true;
        destructible = true;
        //group = BlockGroup.none;
        configurable = true;
    }
    
    @Override
    public void load(){
        super.load();
    }
    
    @Override
    public StructureType getType(){
        return StructureType.core;
    }
	 
    public class MechanicalCoreBuild extends LargeMachineryBuild{
        public int direction = 0;//核心方向，0为上，1为右，2为下，3为左
        public boolean start = false;
        public TextureRegion condition;//状态贴图，就是核心左上角那对错贴图
        
        //旋转按钮
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.defaults().size(40);
            cont.button(Icon.add, Styles.clearTransi, () -> {
                rotate();
            });
            cont.row();
            cont.add(Core.bundle.get("largeMachinery.rotate"));
            table.add(cont);
        }
        
        public void rotate(){
           empty();
           if(direction<3){
               direction++;
           }else{
               direction = 0;
           }
        }
        
        @Override
        public void update(){
            start = construct();
            if(start){
                controlStart();
            }
            if(start){
                condition = Core.atlas.find("quench-status-right");
            }else{
                condition = Core.atlas.find("quench-status-mistake");
            }
        }

        @Override
        public void draw(){
            super.draw();
            if(condition!=null){
            Draw.rect(condition,x-tilesize/2,y+tilesize);
            }
        }
        
        public boolean construct(){
            for(BlockData data:structure.datas){
                Tile tile = Vars.world.tile((int) tile().x+data.x(direction)/8,(int) tile().y+data.y(direction)/8);
                if(!tile.block().name.equals(data.name)) return false;
            }
            return true;
        }
        
        //用于绘制结构
        @Override
        public void drawConfigure(){
        if(!start){
        for(BlockData data:structure.datas){
        Draw.alpha(0.5f);
        Draw.rect(Core.atlas.find(data.name), x+data.x(direction), y+data.y(direction));
        Lines.stroke(1);
        Lines.square(x+data.x(direction), y+data.y(direction),tilesize/2+2,0);
        }
        }
        }
        
        public void controlStart(){
            for(BlockData data:structure.datas){
                if(data.block.core==null){
                data.block.core = this;
                Tile tile = Vars.world.tile((int) tile().x+data.x(direction)/8,(int) tile().y+data.y(direction)/8);
                tile.remove();
                tile.setNet(data.block,team(),0);
                Tile t = Vars.world.tile((int) tile().x+data.x(direction)/8,(int) tile().y+data.y(direction)/8);
                if(data.block.getType()==StructureType.battery){
                battery.add(t);
                data.block.battery.add(t);
                }
                }
            }
        }
        
        //清空
        //在核心旋转先，清空原先方块的core值
        public void empty(){
            if(start){
            for(BlockData data:structure.datas){
                Tile tile = Vars.world.tile((int) tile().x+data.x(direction)/8,(int) tile().y+data.y(direction)/8);
                tile.remove();
                data.block.core = null;
                data.block.battery = null;
                tile.setNet(data.block,team(),0);
            }
            }
        }
    }
}