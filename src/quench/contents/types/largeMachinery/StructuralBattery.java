/*
*大型机械的基础type
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

public class StructuralBattery extends LargeMachinery{
    public MechanicalCore core;
    public StructuralBattery(String name){
        super(name);
        solid = true;
        destructible = true;
        //group = BlockGroup.walls;
        buildCostMultiplier = 5f;
        configurable = true;
        isBattery = true;
        hasPower = true;
        outputsPower = true;
        consumesPower = true;
    }

    @Override
    public void load(){
        super.load();
    }
	 
    public class StructuralBatteryBuild extends LargeMachineryBuild{
        public MechanicalCore c;
        
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.add(c!=null ? "核心: "+c.name:"核心:null");
            table.add(cont);
        }
        
        @Override
        public void overwrote(Seq<Building> previous){
            for(Building other : previous){
                if(other.power != null && other.block.consumes.hasPower() && other.block.consumes.getPower().buffered){
                    float amount = other.block.consumes.getPower().capacity * other.power.status;
                    power.status = Mathf.clamp(power.status + amount / block.consumes.getPower().capacity);
                }
            }
        }

        @Override
        public void draw(){
            super.draw();
            if(core!=null&&c==null) c = core;
        }
    }
}