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
import arc.scene.ui.Dialog.DialogStyle;

import java.util.*;

import quench.contents.types.MechanicalCore.MechanicalCoreBuild;
import quench.contents.types.draw.*;
import quench.*;

import static mindustry.Vars.*;

public class LargeMachinery extends Block{
    //public MechanicalCoreBuild core;
    public boolean canProvidePower;//可提供动力，用于动力发电机检测方块
    public boolean canGenerate;//可以发电
    public float store = 0;//动能存量
    public float yield = 0;//动能产量
    public DrawLargeMachinery drawer = new DrawLargeMachinery();
    public float outputMotive = 0;//输出的动能
    public BlockData[] blacklist;//方块将不能放置在黑名单上的方块上。
    public BlockData[] whitelist;//方块将只能放置在白名单的方块上。
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
        drawer.load(this);
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
				() -> Core.bundle.get("LargeMachinery.motiveForce"),
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
        public float motiveQuantity = 0;//动能数量
        BaseDialog pop;
        
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.add(c!=null ? "核心: x:"+c.tile().x+" y:"+c.tile().y:"核心:null");
            table.add(cont);
        }
        
        @Override
        public void update(){
        }
        
        public Tile near(int rotation){
        if(rotation == 0) return Vars.world.tile((int)tile().x, (int)tile().y-1);
        if(rotation == 1) return Vars.world.tile((int)tile().x-1, (int)tile().y );
        if(rotation == 2) return Vars.world.tile((int)tile().x, (int)tile().y+1);
        if(rotation == 3) return Vars.world.tile((int)tile().x+1, (int)tile().y);
        return Vars.world.tile((int)tile().x, (int)tile().y-1);
    }

        @Override
        public void draw(){
            drawer.draw(this);
        }
        
        @Override
        public void drawConfigure(){
            DialogStyle style = new DialogStyle(){{
            stageBackground = none;
            titleFont = Fonts.def;
            background = windowEmpty;
            titleFontColor = Pal.lightPyraFlame;
        }};
            pop = new BaseDialog(block.getDisplayName(tile()),style);
            detailed(pop);
        }
        
        //被点击时显示详细数据
        public void detailed(BaseDialog pop){
            pop.setFillParent(false);
            pop.setWidth(80);
            pop.setHeight(160);
            pop.addCloseButton();
            pop.show();
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