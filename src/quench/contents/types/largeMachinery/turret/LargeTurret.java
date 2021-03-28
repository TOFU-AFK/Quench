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

public class LargeTurret{
  public String name;
  public float health;//血量，也是护盾血量，当为0时，炮塔将关闭护盾，并飞走
  public int cool;//冷却时间，时间结束后，并且底座没有损坏，炮塔将重新回来
  public int coolSpeed;//冷却速度
  public float radius;//护盾半径
  public Effect defend;//护盾防御特效
  public float range;//攻击范围
  public float baseReloadSpeed;
  public float rotateSpeed;
  public Animation animation;
  public boolean targetAir,targetGround;
  public Sortf unitSort = Unit::dst2;
  
  public LargeTurret(String name){
    this.name = "quench-largeturret-"+name;
    health = 1200;
    cool = 240;
    coolSpeed = 1;
    radius = 60;
    defend = QUFx.smallShockWave;
    range = 240;
    animation = new Animation();
    baseReloadSpeed = 60;
    rotateSpeed = 12;
    targetAir = true;
    targetGround = true;
  }
  
  public TextureRegion region(){
    return Core.atlas.find(name);
  }
  
  public LargeTurretBuild build(TurretCoreBuild core){
    return new LargeTurretBuild(core);
  }
  
  public class LargeTurretBuild{
    public @Nullable Posc target;//目标
    
    public float healthf = health;//剩余血量
    private TurretCoreBuild core;//炮塔的核心
    
    public Cons<Bullet> shieldConsumer;
    public float hit;
    
    public boolean land;
    public boolean inCooling;
    public float coolf;//冷却时间
    public float rotation;
    public Vec2 targetPos = new Vec2();
    
    public LargeTurretBuild(TurretCoreBuild core){
      this.core = core;
      land = false;
      inCooling = false;
      rotation = core.rotation*90;
      shieldConsumer = trait -> {
        if(trait.team != core.team() && trait.type.absorbable && Intersector.isInsideHexagon(core.x, core.y, radius * 2f, trait.x(), trait.y())){
            trait.absorb();
            defend.at(trait);
            hit = 1f;
            healthf -= trait.damage;
        }
      };
    }
    
    public void draw(){
      if(core.start){
        if(land){
          if(inCooling){
            drawToSky();
          }else{
            drawRegion();
            drawShield();
          }
        }else{
          if(!inCooling){
            drawLand();
          }
        }
      }else{
        if(land){
          drawToSky();
        }
      }
    }
    
    public void drawRegion(){
      Draw.z(Layer.turret);
      Draw.rect(region(),core.x,core.y,rotation);
      Draw.reset();
    }
    
    public void drawLand(){
      animation.draw(region(),core);
      land = true;
    }
    
    public void drawToSky(){
      land = false;
    }
    
    public void drawShield(){
        Draw.z(Layer.shields);
        Draw.color(core.team().color, Color.white, Mathf.clamp(hit));
        if(Core.settings.getBool("animatedshields")){
          Fill.poly(core.x, core.y, 6, radius);
        }else{
          Lines.stroke(1.5f);
          Draw.alpha(0.09f + Mathf.clamp(0.08f * hit));
          Fill.poly(core.x, core.y, 6, radius);
          Draw.alpha(1f);
          Lines.poly(core.x, core.y, 6, radius);
          Draw.reset();
        }
      Draw.reset();
    }
    
    public void update(){
      if(core.start){
        if(healthf<=0){
          inCooling = true;
        }
        if(inCooling){
          if(coolf>=cool){
            healthf = health;
            inCooling = false;
          }else{
            coolf+=coolSpeed;
          }
        }
        if(land){
          shields();
          findTarget();
          targetPosition(target);
          float targetRot = core.angleTo(targetPos);
          turnToTarget(targetRot);
        }
      }
    }
    
    //护盾
    public void shields(){
      if(hit > 0f){
        hit -= 1f / 5f * Time.delta;
      }
      if(radius > 0){
        Groups.bullet.intersect(core.x - radius, core.y - radius, radius * 2f, radius * 2f, shieldConsumer);
      }
    }
    
    public void targetPosition(Posc pos){
      if(pos == null) return;
        BulletType bullet = peekAmmo();
        float speed = bullet.speed;
        if(speed < 0.1f) speed = 9999999f;
        targetPos.set(Predict.intercept(core, pos, speed));
        if(targetPos.isZero()){
          targetPos.set(pos);
      }
    }
    
    public BulletType peekAmmo(){
      return QUBullets.bigCircularMissile;
    }
    
    //寻找目标
    protected void findTarget(){
      if(targetAir && !targetGround){
        target = Units.bestEnemy(core.team(), core.x, core.y, range, e -> !e.dead() && !e.isGrounded(), unitSort);
      }else{
        target = Units.bestTarget(core.team(), core.x, core.y, range, e -> !e.dead() && (e.isGrounded() || targetAir) && (!e.isGrounded() || targetGround), b -> true, unitSort);
      }
    }
    
    public float baseReloadSpeed(){
      return baseReloadSpeed;
    }
    
    protected void turnToTarget(float targetRot){
      rotation = Angles.moveToward(rotation, targetRot, rotateSpeed * core.delta() * baseReloadSpeed());
      }
    
    //显示名称
    public String displayName(){
      return Core.bundle.get(name);
    }
    
    //暂时不做处理
    public void write(Writes write){
    }
    
    public void read(Reads read, byte revision){
    }
    
  }

}