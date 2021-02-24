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
import mindustry.core.*;
import mindustry.*;

import static mindustry.Vars.*;

public class MechanicalCore extends LargeMachinery{
    public TextureRegion condition;
    public Structure structure;
    public MechanicalCore(String name){
        super(name);
        solid = true;
        destructible = true;
        group = BlockGroup.none;
        configurable = true;
    }
    
    @Override
    public void load(){
        super.load();
        condition = Core.atlas.find("quench-status-mistake");
    }
	 
    public class MechanicalCoreBuild extends LargeMachineryBuild{
        public int direction = 0;//核心方向，0为上，1为右，2为下，3为左
        
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
           if(direction<3){
               direction++;
           }else{
               direction = 0;
           }
        }
        
        @Override
        public void update(){
            super.update();
        }

        @Override
        public void draw(){
            super.draw();
            Draw.rect(condition,x-tilesize/2,y+tilesize);
        }
        
        //用于绘制结构
        @Override
        public void drawConfigure(){
        for(BlockData data:structure.datas){
        Lines.stroke(1);
        Lines.square(x+data.x, y+data.y,tilesize/2+2,0);
        }
        BaseDialog material = new BaseDialog(Core.bundle.get("ui.material"),Styles.defaultDialog);
        material.setFillParent = false;
        material.setSize(60,100);
        material.setX(x-150);
        material.setY(y);
        material.show();
        }
    }
}