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
	public static LargeMachinery hydroelectricGeneratorCore,mediumHydroelectricGeneratorCore,basicBlock,mediumBasicBlock,smallStructureBattery,powerGenerator;
	
	@Override
	public void load()
	{
	    instantiation();
	    
	    //请在这里实例化核心，核心type需要用到LargeMachinery方块，否则将会发生闪退！
		hydroelectricGeneratorCore = new MechanicalCore("hydroelectricGeneratorCore")
		{
			{
            requirements(Category.crafting, with(Items.silicon, 45, Items.lead, 85,Items.titanium, 25));
			size = 1;
			health = 40*size*size;
			structure = new Structure(new BlockData[]{new BlockData("quench-basicBlock",8,8),new BlockData("quench-basicBlock",-8,8),new BlockData("quench-basicBlock",8,16),new BlockData("quench-basicBlock",-8,16),new BlockData("quench-basicBlock",8,24),new BlockData("quench-basicBlock",-8,24),new BlockData("quench-basicBlock",8,-8),new BlockData("quench-basicBlock",-8,-8),new BlockData("quench-basicBlock",0,-8),new BlockData("quench-basicBlock",8,0),new BlockData("quench-basicBlock",-8,0),new BlockData(smallStructureBattery,0,8),new BlockData(powerGenerator,0,16)});//每次偏移8像素，就偏移一格
			}
		};
		
        mediumHydroelectricGeneratorCore = new MechanicalCore("mediumHydroelectricGeneratorCore")
		{
			{
            requirements(Category.crafting, with(Items.silicon, 90, Items.lead, 85,Items.titanium, 50));
			size = 1;
			health = 40*size*size;
			structure = new Structure(new BlockData[]{new BlockData("quench-basicBlock",8,8),new BlockData("quench-basicBlock",-8,8),new BlockData("quench-basicBlock",8,16),new BlockData("quench-basicBlock",-8,16),new BlockData("quench-basicBlock",8,24),new BlockData("quench-basicBlock",-8,24),new BlockData("quench-basicBlock",8,-8),new BlockData("quench-basicBlock",-8,-8),new BlockData("quench-basicBlock",0,-8),new BlockData("quench-basicBlock",8,0),new BlockData("quench-basicBlock",-8,0),new BlockData(smallStructureBattery,0,8),new BlockData(powerGenerator,0,16),new BlockData(mediumBasicBlock,24,8),new BlockData(mediumBasicBlock,-24,8),new BlockData(mediumBasicBlock,24,32),new BlockData(mediumBasicBlock,-24,32),new BlockData(smallStructureBattery,12,16),new BlockData(smallStructureBattery,-12,16),new BlockData(powerGenerator,12,24),new BlockData(powerGenerator,-12,24)});//每次偏移8像素，就偏移一格
			}
		};
	}
	
	//在此实例化非核心方块
	public void instantiation(){
	    basicBlock = new LargeMachinery("basicBlock")
		{
			{
            requirements(Category.crafting, with(Items.copper, 25));
			size = 1;
			health = 40*size*size;
			}
		};
		
		mediumBasicBlock = new LargeMachinery("mediumBasicBlock")
		{
			{
            requirements(Category.crafting, with(Items.copper, 100,Items.lead,25));
			size = 2;
			health = 40*size*size;
			}
		};
		
		smallStructureBattery = new StructuralBattery("smallStructureBattery")
		{
			{
            requirements(Category.crafting, with(Items.silicon, 25, Items.lead, 30));
			size = 1;
			health = 40*size*size;
			consumes.powerBuffered(3500f);
			}
		};
		
		powerGenerator = new BaseGenerator("powerGenerator"){
		    {
            requirements(Category.crafting, with(Items.silicon, 45, Items.lead, 85,Items.titanium, 25));
			size = 1;
			health = 40*size*size;
			consumes.powerBuffered(3500f);
			}
		};
	}
}
