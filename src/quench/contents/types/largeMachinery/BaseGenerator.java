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

import quench.*;

import static mindustry.Vars.*;

public class BaseGenerator extends StructuralBattery{
    public float increasePower = 2f;//每帧电力产生
    public float powerOutput = 1f;//最大输出电力(当结构中有多个电池时，会平均分)
    //public float consumeMotive = 0.01f;
    public BaseGenerator(String name){
        super(name);
        canGenerate = true;
        occupy = 2f;
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
            super.buildConfiguration(table);
        }
        
        @Override
        public void overwrote(Seq<Building> previous){
            super.overwrote(previous);
        }
        
        @Override
        public void update(){
          super.update();
          if(c!=null&&canGenerate&&!c.mechanicalData.overburden){
              generate();
          }
        }
        
        public void generate(){
            float capacity = consumes.getPower().capacity;
            //发电量占总电量储存的百分比
            float generatePower = increasePower /capacity*c.mechanicalData.efficiency;
            if(c.start&&power.status*capacity+generatePower<=capacity){
            power.status+=generatePower;
            }else if(capacity-power.status*capacity>0){
                power.status=1;
            }
            if(c.mechanicalData!=null) outputPower();
        }
        
        //给多方块结构中的电池方块输入电力
        public void outputPower(){
            ArrayList<Tile> battery = c.mechanicalData.getBatteryTile();
            float output = powerOutput / battery.size()*c.mechanicalData.efficiency;
          for(int i=0;i<battery.size();i++){
              Tile tile = battery.get(i);
              //防止build为空
              if(tile.build!=null){
              float status = tile.build.power.status;
              float capacity = tile.block().consumes.getPower().capacity;
              //电力输出量占总数的百分比
              float outputPower = output / capacity;
              if(power.status*consumes.getPower().capacity>0&&status*capacity+outputPower<=capacity){
              tile.build.power.status+=outputPower;
              power.status-=outputPower;
              }else if(power.status*consumes.getPower().capacity>0&&capacity-status*capacity>0){
              tile.build.power.status=1;
              power.status-=outputPower;
              }
              }
          }
        }

        @Override
        public void draw(){
            super.draw();
        }
    }
}