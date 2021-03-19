/*
*大型机械的动力传输type
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

public class MotiveTransmission extends LargeMachinery{
    public MotiveTransmission(String name){
        super(name);
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        update = true;
        buildCostMultiplier = 5f;
        configurable = true;
        rotate = true;
        transportable = true;
    }

    @Override
    public void load(){
        super.load();
        drawer.load(this);
    }
    
    public StructureType getType(){
        return StructureType.motive;
    }
    
	 
    public class MotiveTransmissionBuild extends LargeMachineryBuild{
        
        @Override
        public void buildConfiguration(Table table){
            /*Table cont = new Table();
            table.add(cont);*/
        }
        
        @Override
        public void update(){
          if(transportable()){
            transmit();
          }
        }
        
        /*
        @Override
        public void reception(Motive motive,float amount){
          super.reception(motive,amount)
        }
        
        @Override
        public boolean acceptable(LargeMachineryBuild build){
          super.acceptable(build);
        }
        
        @Override
        public Tile target(){
          super.target();
        }
        
        @Override
        public boolean transportable(){
          super.transportable();
        }
        
        //传输动力
        public void transmit(){
          super.transmit();
        }*/

        @Override
        public void draw(){
            drawer.draw(this);
            if(start){
              Draw.rect(Core.atlas.find("quench-status-right"),x-tilesize/2,y+tilesize);
            }
        }

    }
}