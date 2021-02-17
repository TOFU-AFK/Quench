package quench.contents.items;
import mindustry.content.*;
import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.type.*;

public class QUItems implements ContentList
{
	public static Item re_thunder,carbonFibre;
	
  @Override
  public void load()
  {
	 re_thunder = new Item("re-thunder",Color.valueOf("ffd280"))
	 {{
	   explosiveness = 0.2f;
      hardness = 4;
      radioactivity = 1f;
      cost = 1.1f;
    }};
    carbonFibre = new Item("carbonFibre",Color.valueOf("ffd280"))
	 {{
      hardness = 2;
      cost = 0.5f;
    }};
  }
}