package quench.contents.planets;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.content.*;

public class QUPlanets implements ContentList{
    public static Planet
    fuleike;

    @Override
    public void load(){
        fuleike = new Planet("fuleike",Planets.sun, 6, 12){{
       atmosphereColor = Color.valueOf("#ec7458aa");
       startSector = 15;
        }};
    }
}
