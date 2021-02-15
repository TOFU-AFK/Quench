package quench.contents.blocks;
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
import quench.contents.bullets.*;

import static mindustry.type.ItemStack.*;
import static mindustry.Vars.*;

public class QUWalls implements ContentList
{
	public static Block re_thunderWall;
	
	@Override
	public void load()
	{
		re_thunderWall = new EnergizedCounterattackWall("re-thunderWall")
		{
			{
            requirements(Category.defense, with(Items.silicon, 65, Items.phaseFabric, 35,Items.titanium, 45, QUItems.re_thunder, 15));
				size = 2;
				health = 420*size*size;
                ownerBullet = QUBullets.circularMissile;
                smallBullet = QUBullets.smallCircularMissile;
			}
		};
	}
}