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
    //public MechanicalCoreBuild core;
    public boolean canProvidePower;//可提供动力，用于动力发电机检测方块
    public boolean canGenerate;//可以发电
    public float store = 0;//动能存量
    public float yield = 0;//动能产量
    public float outputMotive = 0;//输出的动能
    public BlockData[] blacklist;//方块将不能放置在黑名单上的方块上。
    public BlockData[] whitelist;//方块将只能放置在白名单的方块上。
    public TextureRegion bottom;
    public LargeMachinery(String name){
        super(name);
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        update = true;
        buildCostMultiplier = 5f;
        configurable = true;
        hasPower = false;
        canProvidePower = false;
        canGenerate = false;
    }

    @Override
    public void load(){
        super.load();
        bottom = Core.atlas.find("quench-bottom");
    }
    
    public StructureType getType(){
        return StructureType.block;
    }
    
    @Override
	public void setBars(){
		super.setBars();
		if(store>0){
		bars.add(Core.bundle.get("LargeMachinery.motiveForce"), 
			(LargeMachineryBuild entity) -> new Bar(
				() -> Core.bundle.get("LargeMachinery.motiveForce",Float.toString(entity.motiveQuantity)),
				() -> Pal.powerBar,
				() -> entity.motiveQuantity / store
			)
		);
		}
	}
    
    //使用黑名单和白名单判断是否可放在指定方块上，比如水车只能放在水方块上
    //在黑名单和白名单同时存在时，将使用黑名单
    @Override
    public boolean canPlaceOn(Tile tile, Team team){
        if(blacklist!=null&&blacklist.length>0){
            for(BlockData data:blacklist){
                if(data.n.equals(tile.block().name)||data.n.equals(tile.floor().name)) return false;
            }
        }else if(whitelist!=null&&whitelist.length>0){
            for(BlockData data:whitelist){
                if(data.n.equals(tile.block().name)||data.n.equals(tile.floor().name)){ 
    return true;
    }else{
        return false;
    }
        }
    }
    return true;
    }
	 
    public class LargeMachineryBuild extends Building{
        public MechanicalCoreBuild c;
        public boolean begin = true;//开始提供动力
        public float motiveQuantity = 0;//动能数量
        
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.add(c!=null ? "核心: x:"+c.tile().x+" y:"+c.tile().y:"核心:null");
            table.add(cont);
        }
        
        @Override
        public void update(){
        }

        @Override
        public void draw(){
            Draw.rect(bottom,x,y);
            Draw.rect(block.region,x,y);
        }
        
        @Override
        public void write(Writes write){
            super.write(write);
            if(hasPower) write.f(power.status);
            write.f(motiveQuantity);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            if(hasPower) power.status = read.f();
            motiveQuantity = read.f();
        }
    }
}