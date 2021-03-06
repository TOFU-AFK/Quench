/*
*电力出口
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

public class PowerExport extends StructuralBattery{
    public PowerExport(String name){
        super(name);
        solid = true;
        destructible = true;
        buildCostMultiplier = 5f;
        configurable = true;
        hasPower = true;
        insulated = true;
        noUpdateDisabled = true;
        consumesPower = true;
        outputsPower = true;
        rotate = true;
    }

    @Override
    public void load(){
        super.load();
    }
	 
    public class PowerExportBuild extends StructuralBatteryBuild{
        
        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
        }
        
        @Override
        public void update(){
            super.update();
            if(c != null && front() != null &&  front().block.hasPower && team == near(c.direction).team() && c.start) {
            MechanicalData data = c.mechanicalData;
            PowerGraph frontGraph = front().power.graph;
            float backStored = data.graph.getBatteryStored() / data.graph.getTotalBatteryCapacity();
            float frontStored = frontGraph.getBatteryStored() / frontGraph.getTotalBatteryCapacity();
                float amount = data.graph.getBatteryStored() * (backStored - frontStored) / 2;
                amount = Mathf.clamp(amount, 0, frontGraph.getTotalBatteryCapacity() * (1 - frontStored));

                data.graph.transferPower(-amount);
                frontGraph.transferPower(amount);
            }
        }

        @Override
        public void draw(){
            super.draw();
        }
    }
}