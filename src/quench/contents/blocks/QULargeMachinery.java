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
import mindustry.content.TechTree.TechNode;

import quench.contents.types.*;
import quench.contents.items.*;
import quench.contents.drawers.*;
import quench.contents.effects.*;
import quench.contents.largeMachinery.drawers.*;

import static mindustry.type.ItemStack.*;
import static mindustry.Vars.*;

public class QULargeMachinery implements ContentList
{
	public static LargeMachinery hydroelectricGeneratorCore,mediumHydroelectricGeneratorCore,basicBlock,mediumBasicBlock,smallStructureBattery,powerGenerator,waterwheel,powerExport;
	
	@Override
	public void load()
	{
	    instantiation();
	    
	    //请在这里实例化核心，因为核心type需要用到LargeMachinery方块，否则将会发生闪退！
		hydroelectricGeneratorCore = new MechanicalCore("hydroelectricGeneratorCore")
		{
			{
            requirements(Category.power, with(Items.silicon, 45, Items.lead, 85,Items.titanium, 25));
			size = 1;
			health = 40*size*size;
			structure = new Structure(new BlockData[]{new BlockData("quench-basicBlock",8,8),new BlockData("quench-basicBlock",-8,8),new BlockData("quench-basicBlock",8,16),new BlockData("quench-basicBlock",-8,16),new BlockData("quench-basicBlock",8,24),new BlockData("quench-basicBlock",-8,24),new BlockData("quench-basicBlock",8,-8),new BlockData("quench-basicBlock",-8,-8),new BlockData(powerExport,0,-8),new BlockData("quench-basicBlock",8,0),new BlockData("quench-basicBlock",-8,0),new BlockData(smallStructureBattery,0,8),new BlockData(powerGenerator,0,16),new BlockData(waterwheel,0,24)});//一方块8像素
			//添加科技树
			new TechNode(TechTree.root, this,new ItemStack[]{new ItemStack(Items.silicon, 45),new ItemStack(Items.lead, 85),new ItemStack(Items.titanium, 25)});
			}
		};
		
        mediumHydroelectricGeneratorCore = new MechanicalCore("mediumHydroelectricGeneratorCore")
		{
			{
            requirements(Category.power, with(Items.silicon, 90, Items.lead, 85,Items.titanium, 50));
			size = 1;
			health = 40*size*size;
			efficiency = 1.2f;
			structure = new Structure(new BlockData[]{new BlockData(basicBlock,-16,24),new BlockData(basicBlock,-8,24),new BlockData(basicBlock,8,24),new BlockData(basicBlock,16,24),new BlockData(powerGenerator,-16,16),new BlockData(powerGenerator,-8,16),new BlockData(powerGenerator,8,16),new BlockData(powerGenerator,16,16),new BlockData(smallStructureBattery,0,16),new BlockData(smallStructureBattery,0,8),new BlockData(mediumBasicBlock,-16,8),new BlockData(mediumBasicBlock,16,8),new BlockData(basicBlock,-8,8),new BlockData(basicBlock,8,8),new BlockData(waterwheel,-16,0),new BlockData(waterwheel,-8,0),new BlockData(waterwheel,8,0),new BlockData(waterwheel,16,0),new BlockData(powerExport,0,24)});//一方块8像素
			//添加科技树
			new TechNode(TechTree.get(hydroelectricGeneratorCore), this,new ItemStack[]{new ItemStack(Items.titanium, 2)});
			}
		};
	}
	
	//在此实例化非核心方块
	public void instantiation(){
	    //骨架
	    basicBlock = new LargeMachinery("basicBlock")
		{
			{
            requirements(Category.effect, with(Items.copper, 25));
			size = 1;
			health = 40*size*size;
			new TechNode(TechTree.root, this,new ItemStack[]{new ItemStack(Items.copper, 25)});
			}
		};
		
		mediumBasicBlock = new LargeMachinery("mediumBasicBlock")
		{
			{
            requirements(Category.effect, with(Items.copper, 100,Items.lead,25));
			size = 1;
			health = 80*size*size;
			new TechNode(TechTree.get(basicBlock), this,new ItemStack[]{new ItemStack(Items.copper, 100),new ItemStack(Items.lead, 25)});
			}
		};
		//电池
		smallStructureBattery = new StructuralBattery("smallStructureBattery")
		{
			{
            requirements(Category.effect, with(Items.silicon, 25, Items.lead, 30));
			size = 1;
			health = 40*size*size;
			consumes.powerBuffered(3500f);
			new TechNode(TechTree.get(basicBlock), this,new ItemStack[]{new ItemStack(Items.silicon, 25),new ItemStack(Items.lead, 30)});
			}
		};
		//电力
		powerGenerator = new BaseGenerator("powerGenerator"){
		    {
            requirements(Category.effect, with(Items.silicon, 45, Items.lead, 85,Items.titanium, 25));
			size = 1;
			health = 40*size*size;
			powerOutput = 200;
			increasePower = 200;
			consumes.powerBuffered(10f);
			new TechNode(TechTree.get(basicBlock), this,new ItemStack[]{new ItemStack(Items.silicon, 45),new ItemStack(Items.lead, 85),new ItemStack(Items.titanium, 25)});
			}
		};
		//动力
		waterwheel = new WaterWheel("waterwheel")
		{
			{
            requirements(Category.effect, with(Items.copper, 100,Items.lead,25));
			size = 1;
			health = 80*size*size;
			floating = true;
			drawer = new DrawWaterWheel(){
			    {
			        quantity = 5;
			    }
			};
			new TechNode(TechTree.get(basicBlock), this,new ItemStack[]{new ItemStack(Items.copper, 100),new ItemStack(Items.lead, 25)});
			}
		};
		//电力出口
		powerExport = new PowerExport("powerExport")
		{
			{
            requirements(Category.effect, with(Items.copper, 25));
			size = 1;
			health = 40*size*size;
			drawer = new DrawPowerExport();
			consumes.powerBuffered(1250f);
			new TechNode(TechTree.get(basicBlock), this,new ItemStack[]{new ItemStack(Items.copper, 25)});
			}
		};
	}
}
