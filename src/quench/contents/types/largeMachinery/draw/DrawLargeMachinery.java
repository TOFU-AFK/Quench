package quench.contents.types.draw;

import arc.graphics.g2d.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;
import quench.contents.types.LargeMachinery.LargeMachineryBuild;

public class DrawLargeMachinery{
    //public TextureRegion bottom;
    public LargeMachineryBuild master;//详细请看DrawMotiveTransmission
    public boolean isLoader = false;
    public void draw(LargeMachineryBuild entity){
        //Draw.rect(bottom,x,y);
        Draw.rect(entity.block.region,entity.x,entity.y);
    }
    
    //因为在远离玩家一定范围后，draw方法就会停止执行，为了让如传动杆一样的方块继续执行动画，添加了update方法
    public void update(LargeMachineryBuild entity){
      
    }

    public void load(Block block){
     //bottom = Core.atlas.find("quench-bottom");
     isLoader = true;
    }

    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{block.region};
    }
}
