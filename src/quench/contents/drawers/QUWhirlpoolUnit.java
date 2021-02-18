package quench.contents.drawers;

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

import java.util.ArrayList;

import quench.contents.types.draw.*;

import static mindustry.Vars.*;

public class QUWhirlpoolUnit extends DrawEntity {
	public int plasmaQuantity = 2;//plasma的数量，从一开始
	public ArrayList<TextureRegion> plasmaCollection;
	public float warmup = 0.1f;
	public float warmupSpeed = 0.001f;
	public float timeScale = 0.01f;
	public Color plasma1;
	public Color plasma2;
	public int size = 2;//旋转贴图大小
	public TextureRegion region;//单位主贴图
	
	@Override
	public void draw(Unit unit) {
		warmup = Mathf.lerpDelta(warmup, 1f, warmupSpeed * timeScale);
        if(Mathf.equal(warmup, 1f, 0.001f)){
            warmup = 1f;
        }
            for(int i = 0; i < plasmaQuantity; i++){
                float r = size * tilesize - 3f + Mathf.absin(Time.time, 2f + i * 1f, 5f - i * 0.5f);

                Draw.color(plasma1, plasma2, (float)i / plasmaQuantity);
                Draw.alpha((0.3f + Mathf.absin(Time.time, 2f + i * 2f, 0.3f + i * 0.05f)) * warmup);
                Drawf.light(unit.team, unit.x, unit.y, (110f + Mathf.absin(5, 5f)) * warmup, Tmp.c1.set(plasma2).lerp(plasma1, Mathf.absin(7f, 0.2f)), 0.8f * warmup);
                Draw.blend(Blending.additive);
                Draw.rect(plasmaCollection.get(i), unit.x, unit.y, r, r, Time.time * (12 + i * 6f) * warmup);
                Draw.blend();
            }
            Draw.color();
            Draw.rect(region, unit.x, unit.y);
            Draw.color();
	}


	@Override
	public void load(Unit unit) {
	    plasmaCollection = new ArrayList<TextureRegion>();
	    region = Core.atlas.find(unit.name);
	    //用循环来给等离子集合加入贴图
		for(int i=1;i<=plasmaQuantity;i++){
		    plasmaCollection.add(Core.atlas.find(unit.name + "-plasma-" + i));
		}
	}

}