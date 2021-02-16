package quench.contents.blocks;
 
import arc.*;
import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.ctype.*;
import mindustry.content.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.entities.*;
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
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.sandbox.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import quench.contents.types.*;
import quench.contents.bullets.*;
import quench.contents.effects.*;
import quench.contents.items.*;
 
import static mindustry.type.ItemStack.*;
 
public class QUTurrets implements ContentList {
 
  // Load Mod Turrets
  public static Block smallCurvilinearHowitzer,thunderHowitzer;
 
  @Override
  public void load() {
		smallCurvilinearHowitzer = new ItemTurret("small-curvilinear-howitzer"){
			{
				requirements(Category.turret, with(Items.titanium, 85,Items.lead,45,Items.silicon,55));
				ammo(
					Items.lead,QUBullets.smallcurvebomb
				);
				baseRegion = Core.atlas.find("quench-block-" + size);
				size = 2;
				range = 280;
				reloadTime = 50f;
				shootCone = 15f;
				ammoUseEffect = QUFx.grenadeLaunch;
				health = 150 * size * size;
				shootSound = Sounds.laser;
			}
		};
		
		thunderHowitzer = new PowerTurret("thunderHowitzer"){
			{
				requirements(Category.turret, with(Items.titanium, 85,QUItems.re_thunder,45,Items.silicon,55));
				baseRegion = Core.atlas.find("quench-block-" + size);
				size = 3;
				range = 300;
				shootType = QUBullets.bigCircularMissile;
				chargeTime = 120f;
                reloadTime = 80f;
                powerUse = 4f;
                shootShake = 3f;
                shootEffect = Fx.lancerLaserShoot;
                smokeEffect = Fx.none;
                chargeEffect = QUFx.highlightBall;
                chargeBeginEffect = Fx.none;
                heatColor = Color.blue;
				health = 150 * size * size;
				shootSound = Sounds.laser;
			}
		};
  }
}