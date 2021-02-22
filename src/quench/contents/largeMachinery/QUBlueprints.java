package quench.contents.largeMachinery;

import fuleikeindustry.contents.types.structure.blueprint.*;

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

import java.util.*;

import static mindustry.type.ItemStack.*;
import static mindustry.Vars.*;

public class QUBlueprints implements ContentList
{
	public static Blueprint hydroelectricGenerator;
	public static ArrayList<Blueprint> blueprints;
	
	@Override
	public void load()
	{
	    blueprints = new ArrayList<Blueprint>();
		hydroelectricGenerator = new Blueprint("hydroelectricGenerator")
		{
			{
			    //结构的信息(null为换行)
			    structure = {QUStructureBlock.mechanicalShell,QUStructureBlock.mechanicalShell,QUStructureBlock.mechanicalShell,null,QUStructureBlock.mechanicalShell,QUStructureBlock.structuralCore,QUStructureBlock.mechanicalShell,null,QUStructureBlock.mechanicalShell,QUStructureBlock.mechanicalShell,QUStructureBlock.mechanicalShell};
			    x = {-1,0,1,-1,0-1,-1,0,1}
			    y = {1,1,1,0,0,0,-1,-1,-1}
			}
		};
		blueprints.add(hydroelectricGenerator);
	}
}