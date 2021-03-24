package quench.contents.largeMachinery.drawers;

import arc.*;
import arc.math.geom.*;
import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.audio.*;
import arc.util.*;
import arc.struct.*;

import mindustry.ctype.*;
import mindustry.content.*;
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
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import quench.contents.types.LargeMachinery.LargeMachineryBuild;
import quench.contents.types.WaterWheel.WaterWheelBuild;
import quench.contents.types.*;
import quench.contents.types.draw.*;
import quench.contents.types.motive.*;
import quench.contents.types.motive.MotiveTransmission.MotiveTransmissionBuild;
import quench.contents.types.LargeMachinery.LargeMachineryBuild;

import java.util.ArrayList;

import static mindustry.Vars.*;

public class DrawMotiveTransmission extends DrawLargeMachinery {
    public int quantity = 1;//贴图数量
    public Color color1;
    public Color color2;
    ArrayList<TextureRegion> sprites;
    TextureRegion light;
    int index = 0;
    float time;
    float angle;

	@Override
	public void draw(LargeMachineryBuild entity){
	       turn(entity.rotation*90,entity);
  }
  
  @Override
  public void update(LargeMachineryBuild entity){
  	  //因为drawer变量在LargeMachinery上，每个LargeMachineryBuild都会执行一次update，导致动画速度加快，所以限制只有等于master的LargeMachineryBuild才能增加time
  	  if(master==null) master = entity;
  	  if(!entity.overburden()&&entity.x==master.x&&entity.y==master.y){
  	    time++;
  	    	//定时将索引加一
  	    	if(time>=4){
  	    	  time = 0;
  	    	  if(index+1>=quantity){
  	    	    index = 0;
  	    	  }else{
  	    	    index++;
  	    	  }
  	     }
  	  }
  	}
    
    public void turn(float angle,LargeMachineryBuild build){
        Draw.rect(sprites.get(build.turn==Motive.left ? index:quantity-1-index), build.x, build.y,angle);
    }

    @Override
    public void load(Block block){
     super.load(block);
     sprites = new ArrayList<TextureRegion>();
     for(int i=0;i<=quantity;i++){
		    sprites.add(Core.atlas.find(block.name + "-" + i));
    }
    }

}