package quench.contents.types.draw;

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

//用于绘制多方块结构的数据
public class DrawData{
  public LargeMachineryBuild entity;
  public float offset;
  public Color dataColor;//默认颜色
  public Color dataLightColor;//默认高亮颜色
  public float width;
  public float height;
  protected ItemArray itemArray;
  public float space;
  
  
  public DrawData(LargeMachineryBuild entity){
    this(entity,12);
  }
  
  public DrawData(LargeMachineryBuild entity,float offset){
    this.entity = entity;
    this.offset = offset;
    dataColor = Pal.heal;
    dataLightColor = Color.valueOf("#C6FFC6");
    width = 1;
    height = 32;
    itemArray = new ItemArray();
    space = 4; 
  }
  
  public void add(String name,float value,float max){
    itemArray.add(name,value,max);
  }
    
  public void add(String name,float value,float max,Color color,Color lightColor){
    itemArray.add(name,value,max,color,lightColor);
  }
  
  public void draw(){
    itemArray.draw();
  }
  
  public void update(){
    
  }
  
  public void clear(){
    itemArray.clear();
  }
  
  public class ItemArray{
    Seq<ItemData> items = new Seq<ItemData>();
    
    public ItemArray(){
      
    }
    
    public void clear(){
      items.clear();
    }
    
    public void add(String name,float value,float max){
      items.add(new ItemData(name,value,max));
    }
    
    public void add(String name,float value,float max,Color color,Color lightColor){
      items.add(new ItemData(name,value,max,color,lightColor));
    }
    
    public void draw(){
      for(int i=0;i<items.size;i++){
        float offsetX = (i+1)*space;
        float offsetY = -height/2;
        items.get(i).draw(offsetX+offset,offsetY);
      }
    }
    
  }
  
  public class ItemData{
    String name;//名称，暂时用不到
    float value;//值
    float max;//最高值
    Color color;//颜色
    Color lightColor;//高亮颜色
    float x,y;
    
    public ItemData(String name,float value,float max){
      this(name,value,max,dataColor,dataLightColor);
    }
    
    public ItemData(String name,float value,float max,Color color,Color lightColor){
      this.name = name;
      this.value = value;
      this.max = max ;
      this.color = color;
      this.lightColor = lightColor;
      x = entity.x;
      y = entity.y;
    }
    
    public void draw(float offsetX, float offsetY){
      Draw.z(Layer.turret);
      Draw.color(color);
      Fill.crect(x+offsetX,y+offsetY,width,height);
      Draw.reset();
      Draw.color(lightColor);
      Drawf.light(entity.team, entity.x+offsetX,entity.y+offsetY,height,lightColor,0.6f);
      Fill.crect(entity.x+offsetX,entity.y+offsetY,width,height/2);
      Draw.reset();
      Draw.reset();
    }
    
  }
  
}