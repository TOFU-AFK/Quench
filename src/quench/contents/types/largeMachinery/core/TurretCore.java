package quench.contents.types.core;

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

import java.util.*;
import quench.contents.blocks.*;
import quench.contents.types.LargeMachinery.LargeMachineryBuild;
import quench.contents.types.*;
import quench.contents.types.MechanicalCore.MechanicalCoreBuild;
import quench.contents.types.turret.*;
import quench.contents.types.turret.LargeTurret.LargeTurretBuild;
import quench.contents.types.draw.*;

import static mindustry.Vars.*;

public class TurretCore extends MechanicalCore{
  
  public TurretCore(String name){
        super(name);
        solid = true;
        destructible = true;
        configurable = true;
  }
      
  @Override
  public void init(){
    super.init();
    QULargeTurret.motiveTurret.init(this);
    occupy = QULargeTurret.motiveTurret.occupy;
  }
  
  @Override
  public void load(){
    super.load();
    QULargeTurret.motiveTurret.load();
  }
  
  public class TurretCoreBuild extends MechanicalCoreBuild{
    public LargeTurret turret = QULargeTurret.motiveTurret;
    public LargeTurretBuild build = QULargeTurret.motiveTurret.build(this);
    
    @Override
    public void update(){
      super.update();
      if(!mechanicalData.overburden){
        build.update();
      }
    }
    
    @Override
    public void draw(){
      super.draw();
      build.draw();
    }
    
    @Override
    public void setDatas(){
      super.setDatas();
      //护盾
      drawData.add("护盾",build.healthf,turret.health,Color.white,Pal.shield);
      //射击冷却
      if(turret.bulletLife>0&&build.life>0){
        drawData.add("持续",build.life,turret.bulletLife,Color.valueOf("00FF7F"),Color.valueOf("00FFFF"));
      }else{
        drawData.add("冷却",build.coolTime,turret.shootCool,Color.valueOf("00FF7F"),Color.valueOf("00FFFF"));
      }
    }
    
    @Override
    public void drawConfigure(){
      super.drawConfigure();
      build.drawSelect();
    }
  }
  
}