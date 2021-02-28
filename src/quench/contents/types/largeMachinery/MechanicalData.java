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

public class MechanicalData extends Tile{
    public MechanicalCoreBuild core;
    public Structure structure;
    ArrayList<Tile> battery = new ArrayList<Tile>();
    ArrayList<LargeMachinery> block = new ArrayList<LargeMachinery>();
    public MechanicalData(int x,int y,MechanicalCoreBuild core,Structure structure){
        super(x,y);
        this.core = core;
        this.structure = structure;
    }
    
    public void addBattery(Tile t){
        battery.add(t);
    }
    
    public void addBlock(LargeMachinery b){
        block.add(b);
    }
    
    public void setBattery(ArrayList<Tile> b){
        battery = b;
    }
    
    public void setBlock(ArrayList<LargeMachinery> b){
        block = b;
    }
    
    public ArrayList<Tile> getBattery(){
        return battery;
    }
    
    public ArrayList<LargeMachinery> getBlock(){
        return block;
    }
    
    public ArrayList<LargeMachinery> getBlocks(){
        return block;
    }
    
    public LargeMachinery getBlock(int i){
        return block.get(i);
    }
    
    public ArrayList<Tile> getBatteryTile(){
        return battery;
    }
}