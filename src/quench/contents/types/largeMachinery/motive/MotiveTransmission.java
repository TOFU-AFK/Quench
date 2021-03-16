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
    public float occupy = 2;
    public boolean transportable;
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
        public Motive motive;
        public float amount = 0;
        public boolean start = false;
        
        @Override
        public void buildConfiguration(Table table){
            /*Table cont = new Table();
            table.add(cont);*/
        }
        
        @Override
        public void update(){
        }
        
        //当传来动力时
        //参数1为动力的运动方向，参数2为动力总量
        public void reception(Motive motive,float amount){
          this.motive = motive;
          this.amount = amount;
          start = true;
        }
        
        //可接收动力，动力传输方块传输动力前会执行此方法
        public boolean acceptable(LargeMachineryBuild build){
          return build.rotation == rotation;
        }
        
        //目标，传输动力的目标
        public Tile target(){
          return nearby(rotation);
        }
        
        //可传输动力
        public boolean transportable(){
          Tile tile = target();
          MotiveTransmissionBuild build;
          //强制转换，如果出错则返回false
          try{
             build = (MotiveTransmissionBuild) tile.build;
          }catch(Exception e){
            return false;
          }
          if(build.acceptable(this)&&amount>0&&motive!=null) return true;
          return false;
        }
        
        //传输动力
        public void transmit(){
          Tile tile = target();
          MotiveTransmissionBuild build;
          //强制转换，如果出错则返回false
          try{
             build = (MotiveTransmissionBuild) tile.build;
          }catch(Exception e){
            return;
          }
          build.reception(motive,5);
        }

        @Override
        public void draw(){
            drawer.draw(this);
            if(start){
              Draw.rect(Core.atlas.find("quench-status-right"),x-tilesize/2,y+tilesize);
            }
        }

    }
}