package quench.contents.drawers;

import arc.*;
import arc.math.geom.*;
import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;

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

import mindustry.annotations.Annotations.*;
import mindustry.content.*;
import mindustry.entities.units.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class QUImpactFactory extends DrawBlock {
	public Color printColor;
	public Color lightColor;
	public Color lightColor2;
	public Color lightColor3;
	public float moveLength = 8f;
	public float time;
	public TextureRegion bottom, lightRegion;
	@Override
	public void draw(GenericCrafterBuild entity) {
		Draw.rect(bottom, entity.x, entity.y);
		Draw.color(printColor);
		Draw.alpha(entity.warmup);
		float sin = Mathf.sin(entity.totalProgress, time, moveLength);
		for (int i : Mathf.signs) {
			Lines.lineAngleCenter(entity.x + i * sin, entity.y, 90, 16);
			Lines.lineAngleCenter(entity.x, entity.y + i * sin, 0, 16);
            Lines.poly(entity.x + i * sin, entity.y, 6, 4);
            Lines.poly(entity.x, entity.y + i * sin, 6, 4);
		}
		Draw.reset();
		Draw.rect(entity.block.region, entity.x, entity.y);

			Draw.color(lightColor, lightColor2,lightColor3, entity.warmup);
			Draw.alpha((0.3f + Mathf.absin(Time.time, 2f  * 2f, 0.3f * 0.05f)) * entity.warmup);
			Draw.blend(Blending.additive);
			Draw.rect(lightRegion, entity.x, entity.y);
			Draw.blend();
			Draw.reset();
	}


	@Override
	public void load(Block block) {
		bottom = Core.atlas.find(block.name + "-bottom");
		lightRegion = Core.atlas.find(block.name + "-light");
	}

	@Override
	public TextureRegion[] icons(Block block) {
		return new TextureRegion[] {bottom,block.region};
	}

}