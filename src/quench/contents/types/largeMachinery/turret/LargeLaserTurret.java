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

public class LargeLaserTurret extends LargeTurret{
  public float bulletLife;//子弹存在时间,激光可持续多久
  public float firingMoveFract = 0.25f;
  public float shootDuration = 100f;
  
  public LargeLaserTurret (String name){
    super(name);
    bulletLife = 120;
  }
  
  @Override
  public LargeTurretBuild build(TurretCoreBuild core){
    return new LargeLaserTurretBuild(core);
  }
  
  @Override
  public void init(TurretCore core){
    super.init(core);
    }
  
  public class LargeLaserTurretBuild extends LargeTurretBuild{
    Bullet bullet;
    protected boolean shooting = false;
    
    public LargeLaserTurretBuild(TurretCoreBuild core){
      super(core);
    }
    
    @Override
    public void targetPosition(Posc pos){
      super.targetPosition(pos);
    }
    
    @Override
    public void updateShooting(){
      coolTime++;
      if(coolTime>=shootCool){
        BulletType type = peekAmmo();
        bullet(type,rotation);
        coolTime = shootCool;
      }
    }
    
    @Override
    public boolean shootable(){
      if(coolTime>=shootCool){
        coolTime = 0;
        return true;
      }else{
        updateShooting();
        return false;
      }
    }
  
    
    //攻击
    @Override
    public void attack(){
      if(shooting||shootable()){
        if(bulletLife > 0 && bullet != null){
          shooting = true;
          tr.trns(rotation, shootLength, 0f);
          bullet.rotation(rotation);
          bullet.set(core.x + tr.x, core.y + tr.y);
          bullet.time(0f);
          bulletLife -= Time.delta;
          if(bulletLife <= 0f){
            bullet = null;
            coolTime = 0;
            shooting = false;
          }
        }
      }
    }
    
    @Override
    public BulletType peekAmmo(){
      if(core.mechanicalData.getMotiveDirection().contains(Motive.right,true)&&rightBullet!=null&&!shooting){
        return rightBullet;
      }
      return core.turret.bullet;
    }
    
    @Override
    protected void turnToTarget(float targetRot){
      rotation = Angles.moveToward(rotation, targetRot, core.efficiency() * rotateSpeed * core.delta() * (bulletLife > 0f ? firingMoveFract : 1f));
    }
    
    protected void bullet(BulletType type, float angle){
      bullet = type.create(core, core.team, core.x + tr.x, core.y + tr.y, angle);
      bulletLife = shootDuration;
      
    }
    
    public boolean shouldActiveSound(){
      return bulletLife > 0 && bullet != null;
    }
    
  }

}