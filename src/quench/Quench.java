package quench;

//导入原版包
import arc.*;
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
import quench.contents.units.*;

public class Quench extends Mod{
	 //模组被启动
    public Quench()
    
	 {
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
	  new QUWalls().load();
	  new QUPlanets().load();
    }
	
}
