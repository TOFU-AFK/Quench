package quench.contents.types;

import mindustry.entities.*;
import arc.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.ui.*;
import mindustry.ctype.*;
import mindustry.content.*;
import mindustry.world.blocks.defense.turrets.*;
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

//工厂
public class QUAdvancedFactory extends GenericCrafter{
   //构造方法
    public QUAdvancedFactory(String name){
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        sync = true;
    }

    @Override
    public boolean outputsItems(){
        return outputItem != null;
    }
    
    @Override
	public void setBars(){
		super.setBars();
		bars.add(Core.bundle.get("other.time"), 
			(QUAdvancedFactoryBuild entity) -> new Bar(
				() -> Core.bundle.get("other.time"),
				() -> Color.valueOf("D3806A"),
				() -> entity.progress / entity.totalProgress
			)
		);
	}

    public class QUAdvancedFactoryBuild extends GenericCrafterBuild{
        @Override
        public void draw(){
            drawer.draw(this);
        }

        @Override
        public void updateTile(){
            if(consValid()){

                progress += getProgressIncrease(craftTime);
                totalProgress += delta();
                warmup = Mathf.lerpDelta(warmup, 1f, 0.02f);

                if(Mathf.chanceDelta(updateEffectChance)){
                    updateEffect.at(getX() + Mathf.range(size * 4f), getY() + Mathf.range(size * 4));
                }
            }else{
                warmup = Mathf.lerp(warmup, 0f, 0.02f);
            }

            if(progress >= 1f){
                consume();

                if(outputItem != null){
                    for(int i = 0; i < outputItem.amount; i++){
                        offload(outputItem.item);
                    }
                }

                if(outputLiquid != null){
                    handleLiquid(this, outputLiquid.liquid, outputLiquid.amount);
                }
                craftEffect.at(x, y);
                progress = 0f;
            }

            if(outputItem != null && timer(timerDump, dumpTime)){
                dump(outputItem.item);
            }

            if(outputLiquid != null){
                dumpLiquid(outputLiquid.liquid);
            }
        }
    }
}