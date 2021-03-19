/*
*大型机械的核心type
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
import quench.contents.blocks.*;
import quench.contents.types.LargeMachinery.LargeMachineryBuild;
import quench.*;

import static mindustry.Vars.*;

public class MechanicalCore extends LargeMachinery{
    public Structure structure;
    public MechanicalCore block = this;
    public float efficiency = 1;//该核心对发电机等方块的效率提升
    public MechanicalCore(String name){
        super(name);
        solid = true;
        destructible = true;
        //group = BlockGroup.none;
        configurable = true;
    }
    
    @Override
    public void load(){
        super.load();
    }
    
    @Override
    public StructureType getType(){
        return StructureType.core;
    }
    
    @Override
	public void setBars(){
		super.setBars();
		bars.add(Core.bundle.get("MechanicalCore.totalEnergy"), 
			(MechanicalCoreBuild entity) -> new Bar(
				() -> Core.bundle.get("MechanicalCore.totalEnergy"),
				() -> Pal.powerBar,
				() -> entity.mechanicalData.graph.getBatteryStored() / entity.mechanicalData.graph.getBatteryCapacity()
			)
		);
		
	    bars.add(Core.bundle.get("MechanicalCore.totalMotive"), 
			(MechanicalCoreBuild entity) -> new Bar(
				() -> Core.bundle.get("MechanicalCore.totalMotive"),
				() -> Pal.powerBar,
				() -> entity.mechanicalData.getMotive() / entity.mechanicalData.getTotalMotive()
			)
		);
	}
	 
    public class MechanicalCoreBuild extends LargeMachineryBuild{
        public int direction = 0;//核心方向，0为上，1为右，2为下，3为左
        public boolean start = false;
        public TextureRegion condition;//状态贴图，就是核心左上角那对错贴图
        public MechanicalData mechanicalData = new MechanicalData(this,structure);
        public boolean isRead = false;
        
        //旋转按钮
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.defaults().size(40);
            cont.button(Icon.add, Styles.clearTransi, () -> {
                rotate();
            });
            cont.row();
            cont.add(Core.bundle.get("largeMachinery.rotate"));
            table.add(cont);
        }
        
        public void rotate(){
           empty();
           if(direction<3){
               direction++;
           }else{
               direction = 0;
           }
        }
        
        public void initially(){
            if(efficiency<1) efficiency = 1;
            if(mechanicalData.efficiency==0) mechanicalData.efficiency = efficiency;
        }
        
        @Override
        public void update(){
            initially();
            start = construct();
            if(isRead&&!start){
                for(BlockData data:structure.datas){
                Tile tile = Vars.world.tile((int) tile().x+data.x(direction)/8,(int) tile().y+data.y(direction)/8);
                if(tile.build!=null){
                LargeMachineryBuild build = (LargeMachineryBuild) tile.build;
                build.c = this;
                }
            }
            }
            if(start){
                controlStart();
                mechanicalData.update();
                condition = Core.atlas.find("quench-status-right");
            }else{
                mechanicalData.initialize();
                condition = Core.atlas.find("quench-status-mistake");
            }
        }

        @Override
        public void draw(){
            super.draw();
        }
        
        public boolean construct(){
            for(BlockData data:structure.datas){
                Tile tile = Vars.world.tile((int) tile().x+data.x(direction)/8,(int) tile().y+data.y(direction)/8);
                if(!tile.block().name.equals(data.name)||tile.team()!=team) return false;
                LargeMachineryBuild build = (LargeMachineryBuild) tile.build;
                if(build.c!=null&&build.c != this&&build.team!=team) return false;
            }
            return true;
        }
        
        //用于绘制结构
        @Override
        public void drawConfigure(){
        super.drawConfigure();
        if(!start){
        for(BlockData data:structure.datas){
        Draw.alpha(0.5f);
        Draw.rect(Core.atlas.find(data.name), x+data.x(direction), y+data.y(direction));
        Lines.stroke(1);
        Lines.square(x+data.x(direction), y+data.y(direction),tilesize/2+2,0);
        }
        }
        if(condition!=null){
            Draw.rect(condition,x-tilesize/2,y+tilesize);
        }
        //获取结构数据，触发结构中方块的drawConfigure()方法
        //感觉无用，先注释了
        /*for(int i=0;i<mechanicalData.getTiles().size();i++){
            LargeMachineryBuild build = (LargeMachineryBuild) mechanicalData.getTile(i).build;
            build.drawConfigure();*/
        }
        
        public void controlStart(){
            for(BlockData data:structure.datas){
                if(structure.datas.length>mechanicalData.getTiles().size()){
                //先获取地图的tile，再将build强制转换成LargeMachineryBuild，因为前面已经判断了方块的名称，所以不用担心出现问题
                int r;//如果方块可旋转，则保留旋转值
                Tile tile = Vars.world.tile((int) tile().x+data.x(direction)/8,(int) tile().y+data.y(direction)/8);
                if(tile.block().rotate){
                  r = tile.build.rotation;
                }
                tile.setNet(data.block,team(),0);
                LargeMachineryBuild build = (LargeMachineryBuild) tile.build;
                if(data.block.rotate){
                  build.rotation = r;
                }
                build.c = this;
                mechanicalData.addTile(tile);
                }
            }
        }
        
        //清空
        //在核心旋转先，清空原先方块的core值
        public void empty(){
            if(start){
            for(BlockData data:structure.datas){
                Tile tile = Vars.world.tile((int) tile().x+data.x(direction)/8,(int) tile().y+data.y(direction)/8);
                if(tile.build!=null){
                LargeMachineryBuild build = (LargeMachineryBuild) tile.build;
                build.c = null;
                }
            }
            mechanicalData.initialize();
            }
        }
        
        @Override
        public void write(Writes write){
            super.write(write);
            write.i(direction);
            write.bool(isRead);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            direction = read.i();
            //读取时将逻辑值反转，用于重新设置结构核心值
            isRead = !read.bool();
        }
    }
}