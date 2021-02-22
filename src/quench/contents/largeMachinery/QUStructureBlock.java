package quench.contents.largeMachinery;
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
import quench.contents.types.largeMachinery.*;

import static mindustry.type.ItemStack.*;
import static mindustry.Vars.*;

public class QUStructureBlock implements ContentList
{
	public static Block mechanicalShell,structuralCore;
	
	@Override
	public void load()
	{
		mechanicalShell = new MultipleBlock("structure-mechanicalShell")
		{
			{
            requirements(Category.crafting, with(Items.lead, 45, Items.copper, 85));
				size = 1;
				health = 80*size*size;
			}
		};
		
		structuralCore = new StructuralCore("structure-structuralCore")
		{
			{
            requirements(Category.crafting, with(Items.silicon, 45, Items.lead, 85));
				size = 1;
				health = 80*size*size;
			}
		};
	}
}
