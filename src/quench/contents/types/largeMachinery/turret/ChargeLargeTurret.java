package quench.contents.types.turret;

import mindustry.world.*;
import mindustry.entities.*;
import mindustry.game.*;
import arc.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.func.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.Bits;
import arc.struct.*;
import arc.util.io.*;
import arc.scene.ui.*;
import arc.audio.*;
import mindustry.core.GameState.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
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
import mindustry.core.*;
import mindustry.*;
import mindustry.entities.Units.Sortf;

import java.util.*;
import quench.contents.blocks.*;
import quench.contents.types.LargeMachinery.LargeMachineryBuild;
import quench.contents.types.core.*;
import quench.contents.types.core.TurretCore.TurretCoreBuild;
import quench.contents.effects.*;
import quench.contents.bullets.*;

import static mindustry.Vars.*; 

public class ChargeLargeTurret extends LargeTurret{
  
  public float chargeTime;
  
  public ChargeLargeTurret (String name){
    super(name);
    chargeSound = Sounds.lasercharge;
    chargeTime = 50;
  }
  
  @Override
  public LargeTurretBuild build(TurretCoreBuild core){
    return new ChargeLargeTurretBuild(core);
  }
  
  public class ChargeLargeTurretBuild extends LargeTurretBuild{
    
    public ChargeLargeTurretBuild(TurretCoreBuild core){
      super(core);
    }
    
    @Override
    public void draw(){
      super.draw();
    }
    
    @Override
    public void update(){
      super.update();
    }
  
    
    //攻击
    @Override
    public void attack(){
      if(shootable()){
        tr.trns(rotation, shootLength);
        QUFx.ray.at(core.x,core.y,rotation);
        QUFx.ray.at(core.x+tr.x,core.y+tr.y,rotation);
        QUFx.ray.at(core.x-tr.x,core.y-tr.y,rotation);
        QUFx.disturbance.at(core.x,core.y,rotation);
        coolTime=0;
        shootEffect.at(core.x+offset.x,core.y+offset.y,rotation);
      }
    }
    
  }

}