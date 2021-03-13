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

import quench.contents.types.MechanicalCore.MechanicalCoreBuild;
import quench.contents.types.LargeMachinery.LargeMachineryBuild;

public class StructureGraph extends PowerGraph{
    public StructureGraph(){
        
    }
    
    @Override
    public float getBatteryStored(){
        float totalAccumulator = 0f;
        for(Building battery : batteries){
            if(battery==null) continue;//如果battery为空则跳出本次循环
            Consumers consumes = battery.block.consumes;
            if(battery.enabled && consumes.hasPower()){
                totalAccumulator += battery.power.status * consumes.getPower().capacity;
            }
        }
        return totalAccumulator;
    }

    @Override
    public float getBatteryCapacity(){
        float totalCapacity = 0f;
        for(Building battery : batteries){
            if(battery==null) continue;//如果battery为空则跳出本次循环
            if(battery.enabled && battery.block.consumes.hasPower()){
                ConsumePower power = battery.block.consumes.getPower();
                totalCapacity += (1f - battery.power.status) * power.capacity;
            }
        }
        return totalCapacity;
    }

    @Override
    public float getTotalBatteryCapacity(){
        float totalCapacity = 0f;
        if(battery==null) continue;//如果battery为空则跳出本次循环
        for(Building battery : batteries){
            if(battery.enabled && battery.block.consumes.hasPower()){
                totalCapacity += battery.block.consumes.getPower().capacity;
            }
        }
        return totalCapacity;
    }
    
    @Override
    public float useBatteries(float needed){
        float stored = getBatteryStored();
        if(Mathf.equal(stored, 0f)) return 0f;

        float used = Math.min(stored, needed);
        float consumedPowerPercentage = Math.min(1.0f, needed / stored);
        for(Building battery : batteries){
            if(battery==null) continue;//如果battery为空则跳出本次循环
            Consumers consumes = battery.block.consumes;
            if(battery.enabled && consumes.hasPower()){
                battery.power.status *= (1f-consumedPowerPercentage);
            }
        }
        return used;
    }
    
    @Override
    public float chargeBatteries(float excess){
        float capacity = getBatteryCapacity();
        //how much of the missing in each battery % is charged
        float chargedPercent = Math.min(excess/capacity, 1f);
        if(Mathf.equal(capacity, 0f)) return 0f;

        for(Building battery : batteries){
            if(battery==null) continue;//如果battery为空则跳出本次循环
            Consumers consumes = battery.block.consumes;
            if(battery.enabled && consumes.hasPower()){
                ConsumePower consumePower = consumes.getPower();
                if(consumePower.capacity > 0f){
                    battery.power.status += (1f- battery.power.status) * chargedPercent;
                }
            }
        }
        return Math.min(excess, capacity);
    }
}