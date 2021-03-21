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
import quench.contents.types.motive.*;
import quench.contents.types.draw.*;
import quench.*;

import static mindustry.Vars.*;

public class LargeMachinery extends Block{
    //public MechanicalCoreBuild core;
    public boolean canProvidePower;//可提供动力，用于动力发电机检测方块
    public boolean canGenerate;//可以发电
    public float yield = 0;//动能产量
    public DrawLargeMachinery drawer = new DrawLargeMachinery();
    public float outputMotive = 0;//输出的动能
    public BlockData[] blacklist;//方块将不能放置在黑名单上的方块上。
    public BlockData[] whitelist;//方块将只能放置在白名单的方块上。
    public float occupy = 0;//动力占用
    public boolean transportable;//可传输动力
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
        transportable = false;
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
		if(occupy>0){
		  bars.add(Core.bundle.get("MechanicalCore.totalMotive"), 
		  			(LargeMachineryBuild entity) -> new Bar(
		  				() -> Core.bundle.get("MechanicalCore.totalMotive"),
		  				() -> Pal.powerBar,
		  				() -> entity.amount == -1 ? 0 / 1:entity.amount / occupy)
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
        public float motive = 0;//实际动能产出
        public Motive turn = Motive.left;//动力转向
        public float amount = 0;//动力总数
        
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.add(c!=null ? "核心: x:"+c.tile().x+" y:"+c.tile().y:"核心:null");
            table.add(cont);
            showPop(table);//显示弹窗
        }
        
        public void showPop(Table table){
          table.row();
          table.cont.pane(p -> {
                p.margin(10f);
                p.table(Tex.button, t -> {
                    TextButtonStyle style = Styles.cleart;
                    t.defaults().size(280f, 60f).left();

                    t.button("@schematic.copy", Icon.copy, style, () -> {
                    }).marginLeft(12f);
                    t.row();
                    t.button("@schematic.copy.import", Icon.download, style, () -> {
                    }).marginLeft(12f);
                });
            });
        }
        
        @Override
        public void update(){
          if(transportable()){
            transmit();
          }
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

        //是否超载
        public boolean overburden(){
          if(amount<occupy&&amount!=-1) return true;
          return false;
        }
        
        //当传来动力时
        //参数1为动力的运动方向，参数2为动力总量
        public void reception(Motive motive,float amount){
          this.turn = motive;
          if(amount!=-1){
            this.amount = amount;
          }else{
            this.amount = -1;
          }
        }
        
        //可接收动力，动力传输方块传输动力前会执行此方法
        public boolean acceptable(LargeMachineryBuild build){
          return build.rotation == rotation;
        }
        
        //目标，传输动力的目标
        public LargeMachineryBuild target(){
          Tile tile = tile().nearby(rotation);
          LargeMachineryBuild build=null;
            if(tile.block().name.contains("quench-")){
            build = (LargeMachineryBuild) tile.build;
            }
            return build;
        }
        
        //可传输动力
        public boolean transportable(){
          LargeMachineryBuild build = target();
          if(build!=null&&build.acceptable(this)&&turn!=null) return true;
          return false;
        }
        
        //传输动力
        public void transmit(){
          LargeMachineryBuild build = target();
          if(build!=null&&!overburden()) build.reception(turn,amount);
        }
        
        
        @Override
        public void write(Writes write){
            super.write(write);
            if(hasPower) write.f(power.status);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            if(hasPower) power.status = read.f();
        }
    }
}