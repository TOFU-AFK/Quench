package quench.contents.types.draw;

import arc.graphics.g2d.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;
import quench.contents.types.LargeMachinery.LargeMachineryBuild;

public class DrawLargeMachinery{
    //public TextureRegion bottom;

    public void draw(LargeMachineryBuild entity){
        //Draw.rect(bottom,x,y);
        Draw.rect(entity.block.region,entity.x,entity.y);
    }

    public void load(Block block){
     //bottom = Core.atlas.find("quench-bottom");
    }

    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{block.region};
    }
}
