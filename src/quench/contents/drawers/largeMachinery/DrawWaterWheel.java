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
    TextureRegion region;

	@Override
	public void draw(LargeMachineryBuild entity){
	    build = (WaterWheelBuild) entity;
	    color1 = build.liquid.color;
	    color2 = build.liquid.lightColor;
	    block = (WaterWheel) build.block;
	    if(build.liquid.viscosity>block.viscosity){
	        Draw.rect(block.region, entity.x, entity.y);
	    }else{
            for(int i=0;i<quantity;i++){
                Draw.rect(sprites.get(i), build.x, build.y);
            }
	    }
    }

    @Override
    public void load(Block block){
     region = Core.atlas.find(block.name);
     sprites = new ArrayList<TextureRegion>();
     for(int i=0;i<=quantity;i++){
		    sprites.add(Core.atlas.find(block.name + "-" + i));
    }
    }

}