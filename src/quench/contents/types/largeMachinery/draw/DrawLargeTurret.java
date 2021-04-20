package quench.contents.types.draw;

import arc.graphics.g2d.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;
import quench.contents.types.LargeMachinery.LargeMachineryBuild;
import quench.contents.types.core.*;
import quench.contents.types.core.TurretCore.TurretCoreBuild;
import quench.contents.types.turret.*;
import quench.contents.types.turret.LargeTurret.LargeTurretBuild;

public class DrawLargeTurret{
    //public TextureRegion bottom;
    public void draw(LargeTurret turret,LargeTurretBuild entity){
      //Draw.rect(bottom,x,y);
      Draw.rect(turret.region(),entity.core.x,entity.core.y,rotation-90);
    }

    public void load(LargeTurret turret){
    }

    public TextureRegion[] icons(LargeTurret turret){
      return new TextureRegion[]{turret.region()};
    }
}
