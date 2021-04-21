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
import quench.contents.types.turret.*;
import quench.contents.types.turret.LargeTurret.LargeTurretBuild;

import java.util.ArrayList;

import static mindustry.Vars.*;

public class DrawMotiveTurret extends DrawLargeTurret {
  TextureRegion bottom;
  public int quantity = 3;
  ArrayList<TextureRegion> sprites;
  public float warmupSpeed = 0.001f;
	public float timeScale = 0.01f;
	public Color plasma1;
	public Color plasma2;
	public float moveLength = 8f;
	public float time;
	
	@Override
  public void draw(LargeTurretBuild entity){
    //Draw.rect(bottom,x,y);
    Draw.rect(bottom,entity.core.x,entity.core.y,entity.rotation-90);
    Draw.rect(entity.region(),entity.core.x,entity.core.y,entity.rotation-90);
    
    for(int i = 0; i < quantity; i++){
      float r = 5 * tilesize - 3f + Mathf.absin(Time.time, 2f + i * 1f, 5f - i * 0.5f);
      Draw.color(plasma1, plasma2, (float)i / quantity);
      Draw.alpha((0.3f + Mathf.absin(Time.time, 2f + i * 2f, 0.3f + i * 0.05f)) * entity.hit);
      Drawf.light(entity.core.team, entity.core.x, entity.core.y, (110f + Mathf.absin(5, 5f)) * entity.hit, Tmp.c1.set(plasma2).lerp(plasma1, Mathf.absin(7f, 0.2f)), 0.8f * entity.hit);
      Draw.blend(Blending.additive);
      Draw.rect(sprites.get(i), entity.core.x, entity.core.y, r, r, Time.time * (12 + i * 6f) * entity.hit);
      Draw.blend();
    }
  }
  
  @Override
  public void load(LargeTurret turret){
    bottom = Core.atlas.find(turret.name+"-bottom");
    for(int i=0;i<quantity;i++){
      sprites.add(Core.atlas.find(turret.name + "-plasma-" + i));
    }
  }
  
  @Override
  public TextureRegion[] icons(LargeTurret turret){
    return new TextureRegion[]{turret.region()};
  }

}