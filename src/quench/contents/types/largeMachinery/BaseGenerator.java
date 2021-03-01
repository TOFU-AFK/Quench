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
    public float increasePower = 0.005f;//增加的电力
    public float powerOutput = 0.005f;//输出电力
    public BaseGenerator(String name){
        super(name);
        solid = true;
        destructible = true;
        buildCostMultiplier = 5f;
        configurable = true;
        hasPower = true;
        outputsPower = true;
        consumesPower = true;
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
            float capacity = consumes.getPower().capacity;
            if(c.start&&power.status*capacity+increasePower<=capacity) power.status+=increasePower;
            if(c.mechanicalData!=null) outputPower();
          }
        }
        
        //给多方块结构中的电池方块输入电力
        public void outputPower(){
            ArrayList<Tile> battery = c.mechanicalData.getBatteryTile();
            float output = powerOutput / battery.size();
          for(int i=0;i<battery.size();i++){
              Tile tile = battery.get(i);
              if(power.status*consumes.getPower().capacity>0&&tile.build.power.status*tile.block().consumes.getPower().capacity+output<=tile.block().consumes.getPower().capacity){
              tile.build.power.status+=output;
              power.status-=output;
              }
          }
        }

        @Override
        public void draw(){
            super.draw();
        }
    }
}