/*
*大型机械的基础发电机type
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

public class BaseGenerator extends StructuralBattery{
    public BaseGenerator(String name){
        super(name);
        solid = true;
        destructible = true;
        buildCostMultiplier = 5f;
        configurable = true;
        isBattery = false;
        hasPower = true;
        outputsPower = true;
        consumesPower = false;
    }

    @Override
    public void load(){
        super.load();
    }
    
    @Override
    public StructureType getType(){
        return StructureType.generator;
    }
	 
    public class BaseGeneratorBuild extends StructuralBatteryBuild{
        
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.add(c!=null ? "核心: "+c.block().name:"核心:null");
            table.add(cont);
        }
        
        @Override
        public void overwrote(Seq<Building> previous){
            super.overwrote(previous);
        }
        
        @Override
        public void update(){
          super.update();
          if(c!=null){
            if(c.start&&power.status+0.001f<=consumes.getPower().capacity) power.status+=0.001f;
          }
          outputPower();
        }
        
        //给多方块结构中的电池方块输入电力
        public void outputPower(){
            if(batteries!=null){
          for(int i=0;i<batteries.size();i++){
              Tile tile = batteries.get(i);
              if(power.status>0&&tile.build.power.status+0.001f<=tile.block().consumes.getPower().capacity){
              tile.build.power.status+=0.001f;
              power.status-=0.001f;
              }
          }
          }else{
              Log.info("[淬火] batteries为空", "");
          }
        }

        @Override
        public void draw(){
            super.draw();
        }
    }
}