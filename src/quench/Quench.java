package quench;

//导入原版包
import arc.*;
import arc.files.*;
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

public class Quench extends Mod{
    public Quench()
	{
    }
    
    @Override
    public void init(){
    //获取配置文件
    Fi config = getConfig();
    BaseDialog dialog = new BaseDialog("Quench");
    dialog.cont.pane(p -> {
        p.margin(10f);
        p.table(Tex.button, t -> {
            t.add(config != null ? config.readString():"未找到配置文件，请检查模组完整性!");
            t.add("此窗口用于测试配置文件");
        }).marginLeft(12f);
    });
    dialog.buttons.button("@ok", () -> {
    dialog.hide();
    });
    dialog.show();
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
