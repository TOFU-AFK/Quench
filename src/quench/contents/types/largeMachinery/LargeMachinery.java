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

import quench.contents.types.MechanicalCore.MechanicalCoreBuild;

import static mindustry.Vars.*;

public class LargeMachinery extends Block{
    public MechanicalCoreBuild core;
    public BlockData[] datas;//结构方块数组
    public boolean isBattery;
    public boolean canProvidePower;//可提供动力，用于动力发电机检测方块
    public LargeMachineryBuild build;//此变量无用
    public LargeMachinery(String name){
        super(name);
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        update = true;
        buildCostMultiplier = 5f;
        configurable = true;
        hasPower = false;
        isBattery = false;
        canProvidePower = false;
    }

    @Override
    public void load(){
        super.load();
    }
    
    public StructureType getType(){
        return StructureType.block;
    }
	 
    public class LargeMachineryBuild extends Building{
        public MechanicalCoreBuild c;
        public BlockData[] ds;
        
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.add(c!=null ? "核心: "+c.block().name:"核心:null");
            table.add(cont);
        }
        
        @Override
        public void update(){
            if(core!=null&&c==null) c = core;
            if(datas!=null&&ds==null) ds = datas;
            if(build==null) build = this;
        }

        @Override
        public void draw(){
            super.draw();
        }
    }
}