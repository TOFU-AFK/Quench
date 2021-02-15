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

import static mindustry.type.ItemStack.*;
import static mindustry.Vars.*;

public class QUFactories implements ContentList
{
	public static Block R
	re_thunderImpactFactory;
	
	@Override
	public void load()
	{
		re_thunderImpactFactory = new QUAdvancedFactory("re-thunder-Impactfactory")
		{
			{
            requirements(Category.crafting, with(Items.silicon, 45, Items.lead, 85,Items.titanium, 25));
				hasItems = hasPower = true;
				craftTime = 60f;
				outputItem = new ItemStack(QUItems.re_thunder, 1);
				size = 3;
				health = 320;
				craftEffect = QUFx.highEnergyShockWave;
                updateEffect = QUFx.re_thunder_charging;
				drawer = new QUImpactFactory() {
					{
				printColor = Color.valueOf("#D3806A");
				lightColor = Color.valueOf("#ec745855");
				lightColor2 = Color.valueOf("#ff9c5a");
				lightColor3 = Color.valueOf("#ec7458aa");
				moveLength = 6.2f;
				time = 40f;
					}
				};
				consumes.power(3f);
				consumes.items(new ItemStack(Items.titanium, 2), new ItemStack(Items.graphite, 1));
			}
		};
	}
}
