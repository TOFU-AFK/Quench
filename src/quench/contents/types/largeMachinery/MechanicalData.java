package quench.contents.types;

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

import static mindustry.Vars.*;

import quench.contents.types.MechanicalCore.MechanicalCoreBuild;
import quench.contents.types.LargeMachinery.LargeMachineryBuild;

public class MechanicalData{
    public MechanicalCoreBuild core;
    public Structure structure;
    //集合，这里推荐用Seq，用ArrayList是因为当时我并没有想到用这个，现在也懒得改了
    ArrayList<Tile> battery = new ArrayList<Tile>();
    ArrayList<Tile> tile = new ArrayList<Tile>();
    ArrayList<Tile> generator = new ArrayList<Tile>();//消耗动力的方块
    ArrayList<Tile> powerSupply = new ArrayList<Tile>();
    //ArrayList<Tile> motive = new ArrayList<Tile>();
    public float efficiency = 0;//工作效率
    public StructureGraph graph;
    public boolean overburden = false;//动力是否超载
    public MechanicalData(MechanicalCoreBuild core,Structure structure){
        this.core = core;
        this.structure = structure;
        graph = new StructureGraph();
    }
    
    //初始化所有数据
    public void initialize(){
        battery.clear();
        tile.clear();
        generator.clear();
        powerSupply.clear();
        //motive.clear();
        efficiency = 0;
        graph = new StructureGraph();
    }
    
    public void addBattery(Tile t){
        battery.add(t);
        graph.add(t.build);
    }
    
    public void addGenerator(Tile t){
        generator.add(t);
        graph.add(t.build);
    }
    
    public void addPowerSupply(Tile t){
        powerSupply.add(t);
        /*LargeMachinery block = (LargeMachinery) t.block();*/
    }
    
    /*public void addMotive(Tile t){
      motive.add(t);
    }*/
    
    //返回结构角度
    public int getAngle(){
        switch (core.direction){
            case 0:
                return 0;
            case 1:
                return 270;
            case 2:
                return 180;
            case 3:
                return 90;
        }
        return 0;
    }
    
    //返回结构方向
    public int getRotation(){
      switch (core.direction){
            case 0:
                return 3;
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
              }
              return 3;
    }
    
    public void update(){
        //判断动力是否超载
          for(int i=0;i<generator.size();i++){
            Tile t = generator.get(i);
            LargeMachineryBuild build = (LargeMachineryBuild) t.build;
            if(build.amount==-1f){
              overburden = false;
              return;
            }
          }
        if(getMotive()>getTotalMotive()){
            overburden = true;
        }else{
            overburden = false;
        }
    }
    
    //得到动力使用
    public float getMotive(){
        float motive = 0;
        for(int i=0;i<generator.size();i++){
            Tile t = generator.get(i);
            LargeMachinery block = (LargeMachinery) t.block();
            motive+=block.occupy;
        }
        return motive;
    }
    
    //得到动力总量
    public float getTotalMotive(){
        float motive = 0;
        for(int i=0;i<powerSupply.size();i++){
            Tile t = powerSupply.get(i);
            LargeMachineryBuild build = (LargeMachineryBuild) t.build;
            motive+=build.motive;
        }
        return motive;
    }
    
    public ArrayList<Tile> getGenerator(){
        ArrayList<Tile> t = new ArrayList<Tile>();
        for(int i=0;i<tile.size();i++){
            LargeMachinery block = (LargeMachinery) tile.get(i).block();
            if(block.getType()==StructureType.generator) t.add(tile.get(i));
        }
        return t;
    }
    
    public void addTile(Tile b){
        tile.add(b);
        LargeMachinery block = (LargeMachinery) b.block();
        
        if(block.occupy>0){
          addGenerator(b);
        }
        
        switch(block.getType()){
          case battery:
            addBattery(b);
            break;
          case powerSupply:
            addPowerSupply(b);
            break;
          /*case StructureType.motive:
            addMotive(b);*/
        }
        }
    
    public void setBattery(ArrayList<Tile> b){
        battery = b;
    }
    
    public void setTile(ArrayList<Tile> b){
        tile = b;
    }
    
    public ArrayList<Tile> getBattery(){
        return battery;
    }
    
    public ArrayList<Tile> getTiles(){
        return tile;
    }
    
    public Tile getTile(int i){
        return tile.get(i);
    }
    
    public ArrayList<Tile> getBatteryTile(){
        return battery;
    }
}