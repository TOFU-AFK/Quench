package quench.contents.types.draw;

import arc.graphics.g2d.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;
import quench.contents.types.LargeMachinery.LargeMachineryBuild;

public class DrawLargeMachinery{
    //public TextureRegion bottom;
    public LargeMachineryBuild master;//现在无用
    public boolean isLoader = false;
    public void draw(LargeMachineryBuild entity){
        //Draw.rect(bottom,x,y);
        Draw.rect(entity.block.region,entity.x,entity.y,entity.rotation*90);
    }

    public void load(Block block){
     //bottom = Core.atlas.find("quench-bottom");
     isLoader = true;
    }

    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{block.region};
    }
}
