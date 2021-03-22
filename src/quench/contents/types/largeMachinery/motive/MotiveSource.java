/*
*动力源type
*/
package quench.contents.types.motive;

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
import mindustry.content.TechTree.TechNode;

import java.util.*;

import quench.contents.types.MechanicalCore.MechanicalCoreBuild;
import quench.contents.types.draw.*;
import quench.contents.types.*;

import static mindustry.Vars.*;

public class MotiveSource extends LargeMachinery{
    public MotiveSource(String name){
        super(name);
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        update = true;
        buildCostMultiplier = 5f;
        configurable = true;
        transportable = true;
        rotate = true;
        occupy = 0;
    }

    @Override
    public void load(){
        super.load();
        drawer.load(this);
    }
    
    public StructureType getType(){
        return StructureType.motive;
    }
    
	 
    public class MotiveSourceBuild extends LargeMachineryBuild{
        
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.defaults().size(40);
            cont.button(Icon.add, Styles.clearTransi, () -> {
                if(turn==Motive.left){
                  turn = Motive.right;
                }else{
                  turn=Motive.left;
                }
            });
            cont.row();
            cont.add(Core.bundle.get("MotiveSourceBuild.turn"));
            table.add(cont);
        }
        
        @Override
        public void update(){
          amount = -1;//当总数为-1时，将判断为动力源
          if(transportable()){
            transmit();
          }
        }
        
        
        @Override
        public void reception(Motive motive,float amount,DrawLargeMachinery drawer){
        //因为是动力源，所以不做处理
        }
        
        @Override
        public boolean transportable(){
          LargeMachineryBuild build = target();
          if(build!=null&&build.acceptable(this)&&turn!=null) return true;
          return false;
        }
        
        @Override
        public void transmit(){
          LargeMachineryBuild build = target();
          if(build!=null&&!overburden()) build.reception(turn,amount,null);
        }

        @Override
        public void draw(){
            super.draw();
        }

    }
}