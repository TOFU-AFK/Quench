package quench.contents.units;
import arc.*;
import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.ctype.*;
import mindustry.content.*;

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
import quench.contents.items.*;
import quench.contents.drawers.*;
import quench.contents.effects.*;

import static mindustry.type.ItemStack.*;
import static mindustry.Vars.*;

public class QUUnitTypes implements ContentList{
    public static UnitTypes exterminator;
    @Override
	public void load()
	{
	    exterminator = new QUUnitType("exterminator"){
	        {
	        defaultController = MinerAI::new;
            flying = true;
            drag = 0.06f;
            accel = 0.12f;
            speed = 1.5f;
            health = 100;
            engineSize = 1.8f;
            engineOffset = 5.7f;
            range = 50f;
            isCounted = false;

            ammoType = AmmoTypes.powerLow;

            mineTier = 1;
            mineSpeed = 2.5f;
            drawer = new QUWhirlpoolUnit() {
					{
				plasmaQuantity = 3;
				plasma1 = Color.valueOf("#FFFF8F");
				plasma2 = Color.valueOf("#D3806A");
					}
	        }
	    };
	}
}