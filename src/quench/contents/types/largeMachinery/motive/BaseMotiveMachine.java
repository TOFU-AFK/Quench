package quench.contents.types.motive;

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
import mindustry.content.TechTree.TechNode;

import java.util.*;

import quench.contents.types.MechanicalCore.MechanicalCoreBuild;
import quench.contents.types.draw.*;
import quench.contents.types.*;

import static mindustry.Vars.*;

public class BaseMotiveMachine extends LargeMachinery{
  public float baseOutputMotive = 3;//基础动力输出
  public float quicken = 1f;//动力增加数量
  public float baseConsumptionInterval = 120;//基础消耗间隔
  public Item burn;//燃烧物品
  public int burnAmout;//一次燃烧物品的数量
  
  public BaseMotiveMachine(String name){
    super(name);
    solid = true;
    destructible = true;
    group = BlockGroup.walls;
    update = true;
    buildCostMultiplier = 5f;
    configurable = true;
    transportable = true;
    rotate = true;
    occupy = 0;
    hasItems = true;
    acceptsItems = true;
    isMotiveMachine = true;
    burn = Items.coal;
    burnAmout = 1;
    group = BlockGroup.transportation;
  }
  
  @Override
  public StructureType getType(){
    return StructureType.powerSupply;
  }
  
  @Override
  public void setStats(){
    super.setStats();
    stats.add(Stat.output, baseOutputMotive, StatUnit.none);
  }
  
  @Override
	public void setBars(){
		super.setBars();
		  bars.add(Core.bundle.get("MechanicalCore.totalMotive"), 
			(BaseMotiveMachineBuild entity) -> new Bar(
				() -> Core.bundle.format("LargeMachinery.motiveForce",entity.outputMotive),
				() -> Pal.powerBar,
				() -> entity.amount / baseOutputMotive
			)
		);
	}
  
  public class BaseMotiveMachineBuild extends LargeMachineryBuild{
    public float outputMotive = 0;//动力输出，从零逐渐提升
    public float consumptionInterval = baseConsumptionInterval;
    
    @Override
    public void update(){
      super.update();
      updateMotive();
      outputMotive();
    }
    
    public void updateMotive(){
      if(outputMotive<baseOutputMotive){
        Log.info("[淬火] consValid()="+consValid(),"");
        if(consValid()){
          consumptionInterval-=1;
          if(consumptionInterval<=0){
            consumptionInterval = baseConsumptionInterval;
            consume();
            outputMotive+=quicken;
          }
        }
      }
      //如果动力超出上限，强制回到上限
      if(outputMotive>baseOutputMotive){
        outputMotive=baseOutputMotive;
      }
    }
    
    @Override
    public boolean acceptItem(Building source, Item item){
      if(item==burn){
        return true;
      }
      return false;
    }
    
    @Override
    public boolean consValid(){
      if(items.has(burn,burnAmout)){
        return true;
      }
      return false;
    }
    
    @Override
    public void consume(){
      items.remove(burn,burnAmout);
    }
    
    public void outputMotive(){
      amount=outputMotive;
    }
    
  }
  
}