//动力提供type
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

public class PowerSupplyMachine extends LargeMachinery{
    public PowerSupplyMachine(String name){
        super(name);
        solid = true;
        destructible = true;
        buildCostMultiplier = 5f;
        configurable = true;
        canProvidePower = true;
        store = 100;//动能存量
        yield = 0.3f;
        outputMotive = 0.2f;
    }

    @Override
    public void load(){
        super.load();
    }
    
    @Override
    public StructureType getType(){
        return StructureType.powerSupply;
    }
    
    @Override
	public void setBars(){
		super.setBars();
		bars.add(Core.bundle.get("PowerSupplyMachine.motiveForce"), 
			(PowerSupplyMachineBuild entity) -> new Bar(
				() -> Core.bundle.get("PowerSupplyMachine.motiveForce",Float.toString(entity.motiveQuantity)),
				() -> Pal.powerBar,
				() -> entity.motiveQuantity / store
			)
		);
	}
	 
    public class PowerSupplyMachineBuild extends LargeMachineryBuild{
        
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.add(c!=null ? "核心: x:"+c.tile().x+" y:"+c.tile().y:"核心:null");
            table.add(cont);
        }
        
        @Override
        public void update(){
            super.update();
            if(c!=null&&canProvidePower&&store>0&&begin) providePower();
        }
        
        public void providePower(){
            if(motiveQuantity+yield*c.mechanicalData.efficiency<=store){
            motiveQuantity+=yield*c.mechanicalData.efficiency;
            }else if(store-motiveQuantity>0){
            motiveQuantity+=store-motiveQuantity;
            }
            outputMotive();
        }
        
        //输出动力
        public void outputMotive(){
            ArrayList<Tile> generator = new ArrayList<Tile>();
            float output = outputMotive / generator.size();
            for(int i=0;i<generator.size();i++){
            Tile t = generator.get(i);
            LargeMachineryBuild build = (LargeMachineryBuild) t.build;
            if(build.motiveQuantity+output<=build.store){
            build.motiveQuantity+=output;
            motiveQuantity-=output;
            }else if(store-build.motiveQuantity>0){
                output = store-build.motiveQuantity;
                build.motiveQuantity+=output;
                motiveQuantity-=output;
            }
            }
}
        @Override
        public void draw(){
            super.draw();
        }
        
        @Override
        public void write(Writes write){
            super.write(write);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
        }
    }
}