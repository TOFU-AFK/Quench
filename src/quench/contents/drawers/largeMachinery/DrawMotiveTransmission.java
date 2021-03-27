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
    ArrayList<TextureRegion> sprites;
    float time;
    float angle;

	@Override
	public void draw(LargeMachineryBuild entity){
	  if(entity.startAnimation){
	    turn(entity.rotation*90,entity,entity.index);
	    entity.startAnimation = false;
	   }
  }
  
  @Override
  public void update(LargeMachineryBuild entity){
  }
    
    public void turn(float angle,LargeMachineryBuild build,int index){
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