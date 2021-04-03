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
import quench.contents.types.motive.*;
import quench.contents.types.core.*;
import quench.contents.types.turret.*;

import static mindustry.type.ItemStack.*;
import static mindustry.Vars.*;

public class QULargeTurret implements ContentList
{
  public static LargeTurret motiveTurret;
  
  @Override
  public void load(){
    motiveTurret = new LargeTurret("motiveTurret"){
      {
        bullet = QUBullets.disturbance;
      }
    }
  }
}