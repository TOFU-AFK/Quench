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

public class EnergizedLargeTurret extends LargeTurret{
  
  public Sound energizing;
  public float chargingTime;
  
  public EnergizedLargeTurret (String name){
    super(name);
    energizing = Sounds.lasercharge;
    chargingTime = 10;//请注意，射击冷却时间过后才会充能
  }
  
  @Override
  public LargeTurretBuild build(TurretCoreBuild core){
    return new EnergizedLargeTurretBuild(core);
  }
  
  public class EnergizedLargeTurretBuild extends LargeTurretBuild{
    
    public boolean isCharging;
    public float charging;
    
    public EnergizedLargeTurretBuild(TurretCoreBuild core){
      super(core);
      isCharging = false;
      charging = 0;
    }
    
    @Override
    public void draw(){
      super.draw();
    }
    
    @Override
    public void update(){
      super.update();
    }
    
    //在攻击前
    @Override
    public boolean beforeAttack(){
      if(charging>=chargingTime){
        charging = 0;
        isCharging = true;
        return true;
      }else{
        if(!isCharging){
          energizing.at(core.x,core.y);
          charging += core.delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
          return false;
        }
        return true;
      }
    }
    
    //攻击
    @Override
    public void attack(){
      if(beforeAttack()&&shootable()&&coolTime>=shootCool){
        QUFx.ray.at(core);
        QUFx.ray.at(core.x+Vars.tilesize,core.y);
        QUFx.ray.at(core.x-Vars.tilesize,core.y);
        QUFx.disturbance.at(core);
        coolTime=0;
        shootEffect.at(core.x+offset.x,core.y+offset.y,rotation);
          
        isCharging = false;
      }
      if(coolTime<shootCool){
        coolTime+=core.delta() * baseReloadSpeed();
      }
    }
    
  }

}