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

public class DrawPowerExport extends DrawLargeMachinery {
    public TextureRegion arrow,place;
    public float angle = 0;

	@Override
	public void draw(LargeMachineryBuild entity){
	    int r = entity.rotation+1;
	    Draw.rect(place, entity.x, entity.y);
	    Draw.color(Pal.powerBar);
        Draw.alpha(0.9f);
        Drawf.light(entity.team, entity.x, entity.y, (110f + Mathf.absin(5, 5f)), Tmp.c1.set(Pal.powerBar).lerp(Pal.lightTrail, Mathf.absin(7f, 0.2f)), 12f);
        Draw.blend(Blending.additive);
        Draw.rect(arrow, entity.x, entity.y,r * 90);
        Draw.blend();
    }

    @Override
    public void load(Block block){
     super.load(block);
     arrow = Core.atlas.find("quench-arrow");
     place = Core.atlas.find(block.name + "-place");
    }

}