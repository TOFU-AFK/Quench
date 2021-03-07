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

public class MechanicalData{
    public MechanicalCoreBuild core;
    public Structure structure;
    ArrayList<Tile> battery = new ArrayList<Tile>();
    ArrayList<Tile> tile = new ArrayList<Tile>();
    ArrayList<Tile> generator = new ArrayList<Tile>();
    ArrayList<Tile> powerSupply = new ArrayList<Tile>();
    public float efficiency = 0;
    public float powerCapacity = 0;
    public float power = 0;
    public float motiveStorage = 0;//动力总量
    public float motive = 0;//动力
    public boolean using = false;//正在使用中
    public MechanicalData(MechanicalCoreBuild core,Structure structure){
        this.core = core;
        this.structure = structure;
    }
    
    //初始化所有数据
    public void initialize(){
        battery.clear();
        tile.clear();
        generator.clear();
        powerSupply.clear();
        efficiency = 0;
        powerCapacity = 0;
        power = 0;
        motiveStorage = 0;
        motive = 0;
        using = false;
    }
    
    public void addBattery(Tile t){
        battery.add(t);
        powerCapacity = 0;
        for(int i=0;i<battery.size();i++){
           powerCapacity+=battery.get(i).block().consumes.getPower().capacity;
        }
    }
    
    public void addGenerator(Tile t){
        generator.add(t);
    }
    
    public void addPowerSupply(Tile t){
        powerSupply.add(t);
        LargeMachinery block = (LargeMachinery) t.block();
        motiveStorage+=block.store;
    }
    
    //返回结构角度
    public float getAngle(){
        switch (core.direction){
            case 0:
                return 0;
            case 1:
                return 270;
            case 2:
                return 180;
            case 3:
                return 90;
        }
        return 0;
    }
    
    //返回电力百分比
    public float getPower(){
        power = 0;
        for(int i=0;i<battery.size();i++){
            Tile t = battery.get(i);
            if(t.build!=null){
           power+=t.build.power.status;
            }
        }
        return power;
    }
    
    //使用整个结构的电力，返回实际消耗的数量
    public float usePower(float amount){
        float consume = 0;
        float usePower = amount / battery.size();
        for(int i=0;i<battery.size();i++){
            Building build = battery.get(i).build;
            float capacity = build.block.consumes.getPower().capacity;
            float total = build.power.status*capacity;
            if(capacity>=usePower+total){
                build.power.status-=usePower / capacity;
                consume+=usePower;
            }
        }
        return consume;
    }
    
    public float getMotive(){
        motive = 0;
        for(int i=0;i<powerSupply.size();i++){
            Tile t = powerSupply.get(i);
            LargeMachineryBuild build = (LargeMachineryBuild) t.build;
            motive+=build.motiveQuantity;
        }
        return motive;
    }
    
    public ArrayList<Tile> getGenerator(){
        ArrayList<Tile> t = new ArrayList<Tile>();
        for(int i=0;i<tile.size();i++){
            LargeMachinery block = (LargeMachinery) tile.get(i).block();
            if(block.getType()==StructureType.generator) t.add(tile.get(i));
        }
        return t;
    }
    
    public void addTile(Tile b){
        if(!using) using = true;
        tile.add(b);
        LargeMachinery block = (LargeMachinery) b.block();
        if(block.getType()==StructureType.battery){
            addBattery(b);
        }else if(block.getType()==StructureType.powerSupply){
            addPowerSupply(b);
        }else if(block.getType()==StructureType.generator){
            addGenerator(b);
        }
        }
    
    public void setBattery(ArrayList<Tile> b){
        battery = b;
    }
    
    public void setTile(ArrayList<Tile> b){
        tile = b;
    }
    
    public ArrayList<Tile> getBattery(){
        return battery;
    }
    
    public ArrayList<Tile> getTiles(){
        return tile;
    }
    
    public Tile getTile(int i){
        return tile.get(i);
    }
    
    public ArrayList<Tile> getBatteryTile(){
        return battery;
    }
}