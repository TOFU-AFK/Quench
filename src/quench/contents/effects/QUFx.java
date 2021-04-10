package quench.contents.effects;
 
import arc.audio.*;
import arc.math.geom.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.struct.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.io.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
 
import static mindustry.Vars.*;
import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
 
public class QUFx implements ContentList {
	public static
	Effect charge,disturbance,ray,re_thunder_charging,highEnergyShockWave,grenadeLaunch,highlightBall,smallHighlightBall,smallShockWave;
 
	@Override
	public void load() {
	  
	  charge = new Effect(32f, e ->{
	    Draw.color(Pal.heal, Color.valueOf("#C6FFC6"),e.fin());
	    Angles.randLenVectors(e.id, 12, 2f + 20f * e.fout(), e.rotation, 120f, (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 6f + 1f);
        });
	  });
	  
	  disturbance = new Effect(32f, e -> {
	     Draw.color(Pal.heal, Color.valueOf("#C6FFC6"),e.fin());
	     Draw.alpha((0.3f + Mathf.absin(Time.time, 2f  * 2f, 0.3f * 0.05f))*e.fin());
	     Lines.lineAngle(e.x,e.y,e.rotation,460);
	 });
	  
	  ray = new Effect(32f, e -> {
	     Draw.color(Pal.heal, Color.valueOf("#C6FFC6"),e.fin());
	     Angles.randLenVectors(e.id, 10, 440 * e.fin() / 2 + 460 / 2,e.rotation, 0,(x,y) -> {
	     Lines.lineAngle(e.x + x, e.y + y, Mathf.angle(x, y),e.fslope() * 17 + 2);
	     });
	 });
	  
		re_thunder_charging = new Effect(25f, e -> {
		 Draw.color(Pal.lancerLaser, Color.white,e.fin());
	     randLenVectors(e.id, 2, 1f + 20f * e.fout(), e.rotation, 120f, (x, y) -> {
	    int edge = Mathf.random(1,3);//随机1到3
        Lines.stroke(e.fout() * 1.7f);
	    Lines.poly(e.x + x, e.y + y, 3 + edge,5);
	    Lines.poly(e.x - x, e.y - y, 3 + edge,5);
        });
		});
		
		
		
        highEnergyShockWave = new Effect(25f, e -> {
		 Draw.color(Pal.lancerLaser, Color.white,e.fin());
        randLenVectors(e.id, 10, 5 + 55 * e.fin(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, e.fout() * 5f);
					});
        Lines.stroke(e.fout() * 3f);
        Lines.circle(e.x, e.y, e.fin() * 100f);
		});
		
		
		
        grenadeLaunch = new Effect(32f, e -> {
					Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),e.fin());
					stroke(e.fout() * 2);
					circle(e.x, e.y, e.fin() * 40);
					Fill.circle(e.x, e.y, e.fout() * e.fout() * 10);
					randLenVectors(e.id, 10, 5 + 55 * e.fin(), (x, y) -> {
						Fill.circle(e.x + x, e.y + y, e.fout() * 5f);
					});
				});
				
		highlightBall = new Effect(64f, e -> {
					Draw.color(Pal.lancerLaser, Color.white,e.fin());
					Fill.circle(e.x, e.y, e.fout() * 15);
					Draw.color(Color.white,Pal.lancerLaser,e.fin());
					Fill.circle(e.x, e.y, e.fin() * 12);
					Drawf.tri(e.x, e.y, 16, 128 * e.fout(), e.rotation + 90);
					Drawf.tri(e.x, e.y, 16, 128 * e.fout(), e.rotation - 90);
					Drawf.tri(e.x, e.y, 8, 32 * e.fout(), 0 - Time.time);
					Drawf.tri(e.x, e.y, 8, 32 * e.fout(), 180 + Time.time);
					Drawf.tri(e.x, e.y, 8, 32 * e.fout(), 270 - Time.time);
					Drawf.tri(e.x, e.y, 8, 32 * e.fout(), 90 + Time.time);
				});

		smallHighlightBall = new Effect(64f, e -> {
		    //10为次数
		    randLenVectors(e.id, 5, 5 + 65 * e.fin(), (x, y) -> {
					Draw.color(Color.valueOf("FFFF8F"), Color.white,e.fin());
					Fill.circle(e.x + x, e.y + y, e.fout() * 1.628f);
					Draw.color(Color.white,Color.valueOf("FFFF8F"),e.fin());
					Fill.circle(e.x + x, e.y + y, e.fout() * 1.5f);
					Drawf.tri(e.x + x, e.y + y, 2, 16 * e.fout(), e.rotation + 180);
					Drawf.tri(e.x + x, e.y + y, 2, 16 * e.fout(), e.rotation);
		    });
					Lines.stroke(e.fout() * 3f);
                    Lines.circle(e.x, e.y, e.fin() * 25f);
				});
				
		smallShockWave = new Effect(25f, e -> {
		 Draw.color(Color.valueOf("FFFF8F"), Color.white,e.fin());
        randLenVectors(e.id, 10, 5 + 55 * e.fin(), (x, y) -> {
			Fill.circle(e.x + x, e.y + y, e.fout() * 2.5f);
					});
        Lines.stroke(e.fout() * 2f);
        Lines.circle(e.x, e.y, e.fin() * 10f);
		});
	}
}