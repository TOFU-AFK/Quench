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
import mindustry.entities.*;
import mindustry.io.*;
import mindustry.game.*;

import java.util.ArrayList;

import static mindustry.Vars.*;

public class QUProcessingFactory extends DrawBlock {
	public float moveLength = 8f;
	public float time;
	public TextureRegion bottom;
	public int plasmaQuantity = 2;//plasma的数量，从一开始
	public ArrayList<TextureRegion> plasmaCollection;
	public float warmupSpeed = 0.001f;
	public float timeScale = 0.01f;
	public Color plasma1;
	public Color plasma2;
	@Override
	public void draw(GenericCrafterBuild entity) {
		Draw.rect(bottom, entity.x, entity.y);

            for(int i = 0; i < plasmaQuantity; i++){
                float r = entity.block().size * 2 - 3f + Mathf.absin(Time.time, 2f + i * 1f, 5f - i * 0.5f);

                Draw.color(plasma1, plasma2, (float)i / plasmaQuantity);
                Draw.alpha((0.3f + Mathf.absin(Time.time, 2f + i * 2f, 0.3f + i * 0.05f)) * entity.warmup);
                Draw.blend(Blending.additive);
                Draw.rect(plasmaCollection.get(i), entity.x, entity.y, r, r, Time.time * (12 + i * 6f) * entity.warmup);
                Draw.blend();
            }

            Draw.color();
            Draw.rect(entity.block.region, entity.x, entity.y);
            Draw.color();
	}


	@Override
	public void load(Block block) {
	    plasmaCollection = new ArrayList<TextureRegion>();
		bottom = Core.atlas.find(block.name + "-bottom");
		for(int i=1;i<=plasmaQuantity;i++){
		    plasmaCollection.add(Core.atlas.find(block.name + "-plasma-" + i));
		}
	}

	@Override
	public TextureRegion[] icons(Block block) {
		return new TextureRegion[] {bottom,block.region};
	}

}