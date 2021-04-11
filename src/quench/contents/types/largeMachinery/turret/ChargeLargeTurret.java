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
  Bullet b = null;
  
  public ChargeLargeTurret (String name){
    super(name);
    chargeSound = Sounds.lasercharge;
    chargeTime = 50;
  }
  
  @Override
  public LargeTurretBuild build(TurretCoreBuild core){
    return new ChargeLargeTurretBuild(core);
  }
  
  @Override
  public void init(TurretCore core){
    super.init(core);
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
        float trnx = Angles.trnsx(rotation, 9);
        float trny = Angles.trnsy(rotation, 9);
        float randSx = Angles.trnsx(rotation + 90, 9);
        float randSy = Angles.trnsy(rotation + 90, 9);
        float randSx2 = Angles.trnsx(rotation - 90, 9);
        float randSy2 = Angles.trnsy(rotation - 90, 9);
        QUFx.ray.at(core.x+trnx,core.y+trny,rotation);
        QUFx.ray.at(core.x+trnx+randSx,core.y+trny+randSy,rotation);
        QUFx.ray.at(core.x+trnx+randSx2,core.y+trny+randSy2,rotation);
        for(int i=0;i<3;i++){
          float x=0;
          float y=0;
          switch (i){
            case 0:
              x=trnx;
              y=trny;
              break;
            case 1:
              x=randSx;
              y=randSy;
              break;
            case 2:
              x=randSx2;
              y=randSy2;
              break;
          }
          damage(x,y);
        }
        //QUFx.disturbance.at(core.x,core.y,rotation);
        coolTime=0;
        //shootEffect.at(core.x+offset.x,core.y+offset.y,rotation);
        if(duration>0){
          Time.run(duration, () -> {
          shooting = false;
          });
        }else{
          shooting = false;
        }
      }
    }
    
    public void damage(float px,float py){
      Angles.randLenVectors(32, 10, 550 * 2 / 2 + 460 / 2,rotation, 0,(x,y) -> {
        	 Damage.damage(core.team,core.x + x + px, core.y + y + py,4,20,true,true);
      });
    }
    
  }

}