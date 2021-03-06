package quench;

//导入原版包
import arc.*;
import arc.files.*;
import arc.util.serialization.*;
import mindustry.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.*;
import arc.scene.style.TextureRegionDrawable;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.*;

//导入模组内容
import quench.contents.items.*;
import quench.contents.effects.*;
import quench.contents.blocks.*;
import quench.contents.planets.*;
import quench.contents.bullets.*;
//import quench.contents.units.*;
import quench.contents.Setting;

public class Quench extends Mod{
    public static Setting setting = new Setting();
    public Quench()
	{
    }
    
    @Override
    public void init(){
    try{
    //获取配置文件
    Fi config = getConfig();
    Json json = new Json();
    setting = json.fromJson(Setting.class,config.readString());
    BaseDialog dialog = new BaseDialog("Quench");
    dialog.cont.pane(p -> {
        p.margin(10f);
        p.table(Tex.button, t -> {
            Table cont = new Table();
            cont.add(setting.toString()).top();
            cont.add("配置文件已启动").bottom();
            t.add(cont);
        }).marginLeft(12f);
    });
    dialog.buttons.button("@ok", () -> {
    dialog.hide();
    });
    dialog.show();
    }catch(Throwable e){
        Log.info("[淬火] 未找到配置文件",e);
    }
    }
	 //加载内容
    @Override
    public void loadContent()
	{
	  new QUItems().load();
	  new QUFx().load();
	  new QUBullets().load();
	  new QUTurrets().load();
	  new QUFactories().load();
	  new QULargeMachinery().load();
	  new QUWalls().load();
	  new QUPlanets().load();
    }
	
}
