/*
*多方块结构的核心type
*/
package quench.contents.types.largeMachinery;

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
import mindustry.ui.*;
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

import quench.contents.largeMachinery.*;

import static mindustry.Vars.*;

//导入蓝图type
import quench.contents.types.largeMachinery.blueprint.*;

public class StructuralCore extends MultipleBlock{
    public StructuralCore(String name){
        super(name);
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        buildCostMultiplier = 5f;
        configurable = true;
    }

    @Override
    public void load(){
        super.load();
    }
	 
    public class StructuralCoreBuild extends MultipleBlockBuild{
        String promptText = "点击按钮";
        
        //按钮，就是点击方块后出现的按钮
        @Override
        public void buildConfiguration(Table table){
            Table cont = new Table();
            cont.defaults().size(40);
            cont.button(Icon.add, Styles.clearTransi, () -> {
                structuralJudgment();
            });
            cont.row();
            //创建一个Table，用于显示提示文本
            Table text = new Table();
            text.add(promptText).center();
            cont.add(text);
            table.add(cont);
        }
        
        //点击按钮后触发
        public void structuralJudgment(){
            ArrayList<Blueprint> blueprints;
            blueprints = QUBlueprints.blueprints;
            for(int i=0;i<blueprints.size;i++){
              if(blueprints.get(i).judge(x,y)){
                  generate();
              }
            }
        }
        
        //生成结构
        public void generate(){
            promptText = "找到结构";
        }
        
        @Override
        public void update(){
            super.update();
        }

        @Override
        public void draw(){
            super.draw();
        }
    }
}