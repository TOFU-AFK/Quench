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

import java.util.ArrayList;

import static mindustry.Vars.*;

public class DrawMotiveTransmission extends DrawLargeMachinery {
    public int quantity = 1;//贴图数量
    public Color color1;
    public Color color2;
    ArrayList<TextureRegion> sprites;
    TextureRegion light;
    public int index = 0;
    public static float time;
    public static boolean start = false;
    float angle;

	@Override
	public void draw(LargeMachineryBuild entity){
	    time++;
	    //定时将索引加一
	    if(time>=4){
	      time = 0;
	     if(index+1>=quantity){
	       index = 0;
	       }else{
	       index++;
	     }
	     if(entity.overburden()){
	       Draw.color(color1, color2, (float)time / quantity);
         Draw.alpha(0.5f*time);
         Drawf.light(entity.team, entity.x,entity.y, (110f + Mathf.absin(5, 5f)), Tmp.c1.set(color1).lerp(color2, Mathf.absin(7f, 1f)), 0.8f);
         Draw.rect(entity.block.region, entity.x, entity.y);
	     }else{
	       turn(entity.rotation*90,entity);
	     }
    }else if(!start){
      Draw.rect(entity.block.region, entity.x, entity.y);
      start = true;
    }
  }
    
    public void turn(float angle,LargeMachineryBuild build){
        Draw.rect(sprites.get(index), build.x, build.y,angle);
    }

    @Override
    public void load(Block block){
     sprites = new ArrayList<TextureRegion>();
     for(int i=0;i<=quantity;i++){
		    sprites.add(Core.atlas.find(block.name + "-" + i));
    }
    }

}