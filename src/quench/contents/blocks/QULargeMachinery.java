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

public class QULargeMachinery implements ContentList
{
	public static LargeMachinery hydroelectricGeneratorCore,basicBlock;
	
	@Override
	public void load()
	{
		hydroelectricGeneratorCore = new MechanicalCore("hydroelectricGeneratorCore")
		{
			{
            requirements(Category.crafting, with(Items.silicon, 45, Items.lead, 85,Items.titanium, 25));
			size = 1;
			health = 40*size*size;
			structure = new Structure(new BlockData[]{new BlockData("quench-basicBlock",8,8),new BlockData("quench-basicBlock",-8,8),new BlockData("quench-basicBlock",8,16),new BlockData("quench-basicBlock",-8,16),new BlockData("quench-basicBlock",8,24),new BlockData("quench-basicBlock",-8,24),new BlockData("quench-basicBlock",8,-8),new BlockData("quench-basicBlock",-8,-8),new BlockData("quench-basicBlock",0,-8)});//每次偏移8像素，就偏移一格
			}
		};
		
		basicBlock = new LargeMachinery("basicBlock")
		{
			{
            requirements(Category.crafting, with(Items.silicon, 45, Items.lead, 85,Items.titanium, 25));
			size = 1;
			health = 40*size*size;
			}
		};
	}
}
