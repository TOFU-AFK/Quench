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

import java.util.ArrayList;

import static mindustry.Vars.*;

public class DrawWaterWheel extends DrawLargeMachinery {
    public int quantity = 1;//贴图数量
    public Color color1;
    public Color color2;
    WaterWheelBuild build;
    WaterWheel block;
    ArrayList<TextureRegion> sprites;
    TextureRegion light;
    public int index = 0;
    float time;
    float angle;

	@Override
	public void draw(LargeMachineryBuild entity){
	    build = (WaterWheelBuild) entity;
	    block = (WaterWheel) build.block;
	    if(build.liquid!=null&&build.liquid.viscosity<=block.viscosity){
	        color1 = build.liquid.color;
	        color2 = build.liquid.lightColor;
	        if(build.c!=null&&build.c.mechanicalData!=null){
	        angle = build.c.mechanicalData.getAngle();
	        }else{
	            angle = 0;
	        }
	   //因为drawer变量在LargeMachinery上，每个LargeMachineryBuild都会执行一次draw，导致动画速度加快，所以限制只有等于master的LargeMachineryBuild才能增加time
	   if(master==null) master = entity;
	     if(entity.x==master.x&&entity.y==master.y){
	       time++;
	       //定时将索引加一
	       if(time>=8){
	        	time = 0;
	        	if(index+1>=quantity){
	        	index = 0;
	       }else{
	        	index++;
	       }
	       }
	   }
	       //传结构的角度
	       turn(angle);
	        Draw.color(color1, color2, (float)time / quantity);
                Draw.alpha(0.5f);
                Drawf.light(entity.team, entity.x, entity.y, (110f + Mathf.absin(5, 5f)), Tmp.c1.set(color2).lerp(color1, Mathf.absin(7f, 0.2f)), 0.8f);
                Draw.blend(Blending.additive);
                Draw.rect(light, entity.x, entity.y,angle);
                Draw.blend();
	    }else{
	        Draw.rect(block.region, entity.x, entity.y,angle);
	    }
    }
    
    public void turn(float angle){
        Draw.rect(sprites.get(index), build.x, build.y,angle);
    }

    @Override
    public void load(Block block){
     super.load(block);
     sprites = new ArrayList<TextureRegion>();
     light = Core.atlas.find(block.name + "-light");
     for(int i=0;i<=quantity;i++){
		    sprites.add(Core.atlas.find(block.name + "-" + i));
    }
    }

}