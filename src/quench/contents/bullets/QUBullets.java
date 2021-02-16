package quench.contents.bullets;
 
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

import quench.contents.effects.*;

import static mindustry.Vars.*;
import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
 
public class QUBullets implements ContentList {
	public static
	BulletType curvebomb, smallcurvebomb,curvedmissile,bigCircularMissile,circularMissile,smallCircularMissile;
 
	@Override
	public void load() {
        curvebomb = new ArtilleryBulletType(4f, 200f) {
			@Override
			public void init(Bullet b) {
				if (b == null)return;
				b.data(new Vec2(b.x, b.y));
			}
 
			@Override
			public void update(Bullet b) {
 
			}
 
			@Override
			public void draw(Bullet b) {
				Vec2 from = (Vec2)b.data();
				float angle = b.angleTo(from.x, from.y) - 180;
				float dst = b.dst(from.x, from.y);
 
				Vec2
				vec1 = new Vec2().trns(angle, dst / 3),
				vec2 = new Vec2().trns(angle, dst / 3 * 2);
 
				Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),b.fin());
				stroke(5f * b.fout());
 
				float len = Mathf.curve(b.fslope(), 0.1f, 0.8f) * 60 + b.fin() * 50;
				randLenVectors(b.id, 2, len, (x, y) -> {
					randLenVectors(b.id / 2 + 12, 1, len, (x2, y2) -> {
						curve(
							from.x,  		 	from.y,
							from.x + vec1.x + x,  from.y + vec1.y + y,
							from.x + vec2.x + x2, from.y + vec2.y + y2,
							b.x, b.y,
							16
						);
        Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 5* b.finpow(), 15* b.finpow(), 90);
		Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 5* b.finpow(), 15* b.finpow(), 180);
		Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 5* b.finpow(), 15* b.finpow(), 270);
		Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 5* b.finpow(), 15* b.finpow(), 360);
					});
				});
		Fill.circle(from.x, from.y, 3.5f * b.fout() * getStroke() / 2f);
		Drawf.tri(from.x, from.y, 5 * b.fout() * getStroke() / 2f, 15 * b.fout() * getStroke() / 2f, 90);
		Drawf.tri(from.x, from.y, 5 * b.fout() * getStroke() / 2f, 15 * b.fout() * getStroke() / 2f, 180);
		Drawf.tri(from.x, from.y, 5 * b.fout() * getStroke() / 2f, 15 * b.fout() * getStroke() / 2f, 270);
		Drawf.tri(from.x, from.y, 5 * b.fout() * getStroke() / 2f, 15 * b.fout() * getStroke() / 2f, 360);
		Lines.stroke(b.fin() * 0.9f);
		Lines.circle(from.x, from.y, b.fout() * 40);
		Lines.circle(from.x, from.y, b.fin() * 40);
		
		Drawf.tri(b.x, b.y, 5* b.finpow(), 15* b.finpow(), 90);
		Drawf.tri(b.x, b.y, 5* b.finpow(), 15* b.finpow(), 180);
		Drawf.tri(b.x, b.y, 5* b.finpow(), 15* b.finpow(), 270);
		Drawf.tri(b.x, b.y, 5* b.finpow(), 15* b.finpow(), 360);
		Lines.stroke(b.fin() * 0.9f);
		Lines.circle(b.x, b.y, b.fout() * 20);
		Lines.circle(b.x, b.y, b.fin() * 20);
				reset();
			}
 
			@Override
			public void despawned(Bullet b) {
				Effect.shake(3f, 1f,b);
				despawnEffect.at(b);
                                new Effect(32f, e -> {
					Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),e.fin());
	randLenVectors(e.id, 5, 5 + 15 * e.fin(), (x, y) -> {
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9* e.fout(), 90);
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9*e.fout(), 180);
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9* e.fout(), 270);
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9* e.fout(), 360);
					});
				}).at(b);
				//Sounds.explosionbig.at(b, Mathf.random(0.9f, 1.1f));
           for (int num = 0; num < 3; num ++) {
		Lightning.create(b.team(), Color.valueOf("#FFFF8F"), 50, b.x, b.y, Mathf.random(360), Mathf.random(3, 6));
	   }
				Damage.damage(b.team(), b.x, b.y, this.splashDamageRadius, this.splashDamage * b.damageMultiplier());
			}
 
			{
				drawSize = 400;
 
				shootEffect = new Effect(12f, e -> {
                Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),e.fin());
                Drawf.tri(e.x, e.y, 5, 15, 90);
		Drawf.tri(e.x, e.y, 5, 15, 180);
		Drawf.tri(e.x, e.y, 5, 15, 270);
		Drawf.tri(e.x, e.y, 5, 15, 360);
                Draw.reset();
				});
 
				despawnEffect = new Effect(32f, e -> {
					Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),e.fin());
					stroke(e.fout() * 2);
					circle(e.x, e.y, e.fin() * 40);
					Fill.circle(e.x, e.y, e.fout() * e.fout() * 10);
					randLenVectors(e.id, 10, 5 + 55 * e.fin(), (x, y) -> {
						Fill.circle(e.x + x, e.y + y, e.fout() * 5f);
					});
				});
 
				smokeEffect = new Effect(12f, e -> {
                Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),e.fin());
                Drawf.tri(e.x, e.y, 5, 15, 90);
		Drawf.tri(e.x, e.y, 5, 15, 180);
		Drawf.tri(e.x, e.y, 5, 15, 270);
		Drawf.tri(e.x, e.y, 5, 15, 360);
                Draw.reset();
				});
				pierce = false;
				hitSize = 10;
				splashDamage = 200f;
			}
 
		};
		
//=========================================
        smallcurvebomb = new ArtilleryBulletType(4f, 35f) {
			@Override
			public void init(Bullet b) {
				if (b == null)return;
				b.data(new Vec2(b.x, b.y));
			}
 
			@Override
			public void update(Bullet b) {
 
			}
 
			@Override
			public void draw(Bullet b) {
				Vec2 from = (Vec2)b.data();
				float angle = b.angleTo(from.x, from.y) - 180;
				float dst = b.dst(from.x, from.y);
 
				Vec2
				vec1 = new Vec2().trns(angle, dst / 3),
				vec2 = new Vec2().trns(angle, dst / 3 * 2);
 
				Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),b.fin());
				stroke(2f * b.fout());
 
				float len = Mathf.curve(b.fslope(), 0.1f, 0.8f) * 60 + b.fin() * 50;
				randLenVectors(b.id, 2, len, (x, y) -> {
					randLenVectors(b.id / 2 + 12, 1, len, (x2, y2) -> {
						curve(
							from.x,  		 	from.y,
							from.x + vec1.x + x,  from.y + vec1.y + y,
							from.x + vec2.x + x2, from.y + vec2.y + y2,
							b.x, b.y,
							10
						);
        Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 2* b.finpow(), 7* b.finpow(), 90);
		Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 2* b.finpow(), 7* b.finpow(), 180);
		Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 2* b.finpow(), 7* b.finpow(), 270);
		Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 2* b.finpow(), 7* b.finpow(), 360);
					});
				});
		Fill.circle(from.x, from.y, 2f * b.fout() * getStroke() / 1f);
		Drawf.tri(from.x, from.y, 2 * b.fout() * getStroke() / 2f, 7 * b.fout() * getStroke() / 2f, 90);
		Drawf.tri(from.x, from.y, 2 * b.fout() * getStroke() / 2f, 7 * b.fout() * getStroke() / 2f, 180);
		Drawf.tri(from.x, from.y, 2 * b.fout() * getStroke() / 2f, 7 * b.fout() * getStroke() / 2f, 270);
		Drawf.tri(from.x, from.y, 2 * b.fout() * getStroke() / 2f, 7 * b.fout() * getStroke() / 2f, 360);
		Lines.stroke(b.fin() * 0.9f);
		Lines.circle(from.x, from.y, b.fout() * 40);
		Lines.circle(from.x, from.y, b.fin() * 40);
		
		Drawf.tri(b.x, b.y, 2* b.finpow(), 7* b.finpow(), 90);
		Drawf.tri(b.x, b.y, 2* b.finpow(), 7* b.finpow(), 180);
		Drawf.tri(b.x, b.y, 2* b.finpow(), 7* b.finpow(), 270);
		Drawf.tri(b.x, b.y, 2* b.finpow(), 7* b.finpow(), 360);
		Lines.stroke(b.fin() * 0.9f);
		Lines.circle(b.x, b.y, b.fout() * 10);
		Lines.circle(b.x, b.y, b.fin() * 10);
				reset();
			}
 
			@Override
			public void despawned(Bullet b) {
				Effect.shake(3f, 1f,b);
				despawnEffect.at(b);
				//Sounds.explosionbig.at(b, Mathf.random(0.9f, 1.1f));
           for (int num = 0; num < 3; num ++) {
		Lightning.create(b.team(), Color.valueOf("#FFFF8F"), 25, b.x, b.y, Mathf.random(360), Mathf.random(3, 6));
	   }
				Damage.damage(b.team(), b.x, b.y, this.splashDamageRadius, this.splashDamage * b.damageMultiplier());
			}
 
			{
				drawSize = 400;
 
				shootEffect = new Effect(12f, e -> {
                Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),e.fin());
                Drawf.tri(e.x, e.y, 5, 15, 90);
		Drawf.tri(e.x, e.y, 5, 15, 180);
		Drawf.tri(e.x, e.y, 5, 15, 270);
		Drawf.tri(e.x, e.y, 5, 15, 360);
                Draw.reset();
				});
 
				despawnEffect = new Effect(32f, e -> {
					Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),e.fin());
					stroke(e.fout() * 2);
					circle(e.x, e.y, e.fin() * 20);
					Fill.circle(e.x, e.y, e.fout() * e.fout() * 5);
					randLenVectors(e.id, 10, 5 + 55 * e.fin(), (x, y) -> {
						Fill.circle(e.x + x, e.y + y, e.fout() * 2.5f);
					});
				});
 
				smokeEffect = new Effect(12f, e -> {
                Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),e.fin());
                Drawf.tri(e.x, e.y, 5, 15, 90);
		Drawf.tri(e.x, e.y, 5, 15, 180);
		Drawf.tri(e.x, e.y, 5, 15, 270);
		Drawf.tri(e.x, e.y, 5, 15, 360);
                Draw.reset();
				});
				pierce = false;
				hitSize = 10;
				splashDamage = 50f;
			}
 
		};
//=========================================
        curvedmissile = new BulletType(6f, 4f) {
			@Override
			public void init(Bullet b) {
				if (b == null)return;
				b.data(new Vec2(b.x, b.y));
			}
 
			@Override
			public void update(Bullet b) {
			Vec2 from = (Vec2)b.data();
			}
 
			@Override
			public void draw(Bullet b) {
				Vec2 from = (Vec2)b.data();
				float angle = b.angleTo(from.x, from.y) - 180;
				float dst = b.dst(from.x, from.y);
 
				Vec2
				vec1 = new Vec2().trns(angle, dst / 3),
				vec2 = new Vec2().trns(angle, dst / 3 * 2);
 
				Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),b.fin());
				stroke(3f * b.fout());
 
				float len = Mathf.curve(b.fslope(), 0.1f, 0.8f) * 60 + b.fin() * 50;
				randLenVectors(b.id, 2, len, (x, y) -> {
					randLenVectors(b.id / 2 + 12, 1, len, (x2, y2) -> {
						curve(
							from.x,  		 	from.y,
							from.x + vec1.x + x,  from.y + vec1.y + y,
							from.x + vec2.x + x2, from.y + vec2.y + y2,
							b.x, b.y,
							5
						);
        Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 2* b.finpow(), 5* b.finpow(), 90);
		Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 2* b.finpow(), 5* b.finpow(), 180);
		Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 2* b.finpow(), 5* b.finpow(), 270);
		Drawf.tri(from.x + vec1.x + x, from.y + vec1.y + y, 2* b.finpow(), 5* b.finpow(), 360);
					});
				});
		Fill.circle(from.x, from.y, 3.5f * b.fout() * getStroke() / 2f);
		Drawf.tri(from.x, from.y, 2 * b.fout() * getStroke() / 2f, 5 * b.fout() * getStroke() / 2f, 90);
		Drawf.tri(from.x, from.y, 2 * b.fout() * getStroke() / 2f, 5 * b.fout() * getStroke() / 2f, 180);
		Drawf.tri(from.x, from.y, 2 * b.fout() * getStroke() / 2f, 5 * b.fout() * getStroke() / 2f, 270);
		Drawf.tri(from.x, from.y, 2 * b.fout() * getStroke() / 2f, 5 * b.fout() * getStroke() / 2f, 360);
		Lines.stroke(b.fin() * 0.9f);
		Lines.circle(from.x, from.y, b.fout() * 10);
		Lines.circle(from.x, from.y, b.fin() * 10);
		
		Drawf.tri(b.x, b.y, 2* b.finpow(), 5* b.finpow(), 90);
		Drawf.tri(b.x, b.y, 2* b.finpow(), 5* b.finpow(), 180);
		Drawf.tri(b.x, b.y, 2* b.finpow(), 5* b.finpow(), 270);
		Drawf.tri(b.x, b.y, 2* b.finpow(), 5* b.finpow(), 360);
		reset();
			}
			
			{
				drawSize = 40;
				shootEffect = new Effect(12f, e -> {
                Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),e.fin());
                Drawf.tri(e.x, e.y, 2, 5, 90);
		Drawf.tri(e.x, e.y, 2, 5, 180);
		Drawf.tri(e.x, e.y, 2, 5, 270);
		Drawf.tri(e.x, e.y, 2, 5, 360);
                Draw.reset();
				});
				smokeEffect = new Effect(12f, e -> {
                Draw.color(Color.valueOf("D3806A"), Color.valueOf("FFFF8F"),e.fin());
                Drawf.tri(e.x, e.y, 5, 15, 90);
		Drawf.tri(e.x, e.y, 2, 5, 180);
		Drawf.tri(e.x, e.y, 2, 5, 270);
		Drawf.tri(e.x, e.y, 2, 5, 360);
                Draw.reset();
				});
				homingPower = 0.08f;
				homingRange = 360;
				pierce = false;
				lifetime = 108f;
				hitSize = 10;
			}
 
		};

//=========================================
        bigCircularMissile = new BulletType(4f, 80f) {

            @Override
            public void update(Bullet b){
              new Effect(25f, e -> {
	Draw.color(Pal.lancerLaser);
    Fill.circle(e.x, e.y, e.fout() * 14);
    Draw.color(Color.white);
    Fill.circle(e.x, e.y, e.fout() * 7f);
        }).at(b);
            }

			@Override
			public void draw(Bullet b) {
				Draw.color(Pal.lancerLaser, Color.white,b.fin());
			Fill.circle(b.x, b.y,14f);
				reset();
			}
 
			@Override
			public void despawned(Bullet b) {
				Effect.shake(6f, 2f,b);
				despawnEffect.at(b);
                                new Effect(32f, e -> {
					Draw.color(Pal.lancerLaser, Color.white,e.fin());
	randLenVectors(e.id, 5, 5 + 15 * e.fin(), (x, y) -> {
		Drawf.tri(e.x + x, e.y + y, 8* e.fout(), 18* e.fout(), 90);
		Drawf.tri(e.x + x, e.y + y, 8* e.fout(), 18*e.fout(), 180);
		Drawf.tri(e.x + x, e.y + y, 8* e.fout(), 18* e.fout(), 270);
		Drawf.tri(e.x + x, e.y + y, 8* e.fout(), 18* e.fout(), 360);
					});
				}).at(b);
				//Sounds.explosionbig.at(b, Mathf.random(0.9f, 1.1f));
           for (int num = 0; num < 4; num ++) {
		Lightning.create(b.team(), Pal.lancerLaser, 50, b.x, b.y, Mathf.random(360), Mathf.random(6, 12));
		smallCircularMissile.create(b,b.x,b.y,90*num);
	   }
				Damage.damage(b.team(), b.x, b.y, this.splashDamageRadius, this.splashDamage * b.damageMultiplier());
			}
 
			{
				drawSize = 400;
 
				shootEffect = new Effect(12f, e -> {
                Draw.color(Pal.lancerLaser, Color.white,e.fin());
                Drawf.tri(e.x, e.y, 10, 30, 90);
		Drawf.tri(e.x, e.y, 10, 30, 180);
		Drawf.tri(e.x, e.y, 10, 30, 270);
		Drawf.tri(e.x, e.y, 10, 30, 360);
                Draw.reset();
				});
 
				despawnEffect = new Effect(32f, e -> {
					Draw.color(Pal.lancerLaser, Color.white,e.fin());
					stroke(e.fout() * 4);
					circle(e.x, e.y, e.fin() * 80);
					Fill.circle(e.x, e.y, e.fout() * e.fout() * 20);
					randLenVectors(e.id, 10, 5 + 55 * e.fin(), (x, y) -> {
						Fill.circle(e.x + x, e.y + y, e.fout() * 10f);
					});
				});
 
				smokeEffect = new Effect(12f, e -> {
                Draw.color(Pal.lancerLaser, Color.white,e.fin());
                Drawf.tri(e.x, e.y, 10, 30, 90);
		Drawf.tri(e.x, e.y, 10, 30, 180);
		Drawf.tri(e.x, e.y, 10, 30, 270);
		Drawf.tri(e.x, e.y, 10, 30, 360);
                Draw.reset();
				});
				pierce = false;
				lifetime = 120;
				hitSize = 10;
				splashDamage = 200f;
			}
 
		};

//=========================================
        circularMissile = new BulletType(4f, 50f) {

            @Override
            public void update(Bullet b){
              new Effect(25f, e -> {
	Draw.color(Pal.lancerLaser);
    Fill.circle(e.x, e.y, e.fout() * 7);
    Draw.color(Color.white);
    Fill.circle(e.x, e.y, e.fout() * 3.5f);
        }).at(b);
            }

			@Override
			public void draw(Bullet b) {
				Draw.color(Pal.lancerLaser, Color.white,b.fin());
			Fill.circle(b.x, b.y,7f);
				reset();
			}
 
			@Override
			public void despawned(Bullet b) {
				Effect.shake(3f, 1f,b);
				despawnEffect.at(b);
                                new Effect(32f, e -> {
					Draw.color(Pal.lancerLaser, Color.white,e.fin());
	randLenVectors(e.id, 5, 5 + 15 * e.fin(), (x, y) -> {
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9* e.fout(), 90);
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9*e.fout(), 180);
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9* e.fout(), 270);
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9* e.fout(), 360);
					});
				}).at(b);
				//Sounds.explosionbig.at(b, Mathf.random(0.9f, 1.1f));
           for (int num = 0; num < 3; num ++) {
		Lightning.create(b.team(), Pal.lancerLaser, 50, b.x, b.y, Mathf.random(360), Mathf.random(3, 6));
	   }
				Damage.damage(b.team(), b.x, b.y, this.splashDamageRadius, this.splashDamage * b.damageMultiplier());
			}
 
			{
				drawSize = 400;
 
				shootEffect = new Effect(12f, e -> {
                Draw.color(Pal.lancerLaser, Color.white,e.fin());
                Drawf.tri(e.x, e.y, 5, 15, 90);
		Drawf.tri(e.x, e.y, 5, 15, 180);
		Drawf.tri(e.x, e.y, 5, 15, 270);
		Drawf.tri(e.x, e.y, 5, 15, 360);
                Draw.reset();
				});
 
				despawnEffect = new Effect(32f, e -> {
					Draw.color(Pal.lancerLaser, Color.white,e.fin());
					stroke(e.fout() * 2);
					circle(e.x, e.y, e.fin() * 40);
					Fill.circle(e.x, e.y, e.fout() * e.fout() * 10);
					randLenVectors(e.id, 10, 5 + 55 * e.fin(), (x, y) -> {
						Fill.circle(e.x + x, e.y + y, e.fout() * 5f);
					});
				});
 
				smokeEffect = new Effect(12f, e -> {
                Draw.color(Pal.lancerLaser, Color.white,e.fin());
                Drawf.tri(e.x, e.y, 5, 15, 90);
		Drawf.tri(e.x, e.y, 5, 15, 180);
		Drawf.tri(e.x, e.y, 5, 15, 270);
		Drawf.tri(e.x, e.y, 5, 15, 360);
                Draw.reset();
				});
				pierce = false;
				hitSize = 10;
				splashDamage = 100f;
			}
 
		};
//=========================================
smallCircularMissile = new BulletType(4f, 5f) {

            @Override
            public void update(Bullet b){
              new Effect(25f, e -> {
	Draw.color(Pal.lancerLaser);
    Fill.circle(e.x, e.y, e.fout() * 3.5f);
    Draw.color(Color.white);
    Fill.circle(e.x, e.y, e.fout() * 1.75f);
        }).at(b);
            }

			@Override
			public void draw(Bullet b) {
				Draw.color(Pal.lancerLaser, Color.white,b.fin());
			Fill.circle(b.x, b.y,3.5f);
				reset();
			}
 
			@Override
			public void despawned(Bullet b) {
				Effect.shake(3f, 1f,b);
				despawnEffect.at(b);
                                new Effect(32f, e -> {
					Draw.color(Pal.lancerLaser, Color.white,e.fin());
	randLenVectors(e.id, 5, 5 + 15 * e.fin(), (x, y) -> {
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9* e.fout(), 90);
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9*e.fout(), 180);
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9* e.fout(), 270);
		Drawf.tri(e.x + x, e.y + y, 4* e.fout(), 9* e.fout(), 360);
					});
				}).at(b);
				//Sounds.explosionbig.at(b, Mathf.random(0.9f, 1.1f));
           for (int num = 0; num < 3; num ++) {
		Lightning.create(b.team(), Pal.lancerLaser, 50, b.x, b.y, Mathf.random(360), Mathf.random(3, 6));
	   }
				Damage.damage(b.team(), b.x, b.y, this.splashDamageRadius, this.splashDamage * b.damageMultiplier());
			}
 
			{
				drawSize = 400;
 
				shootEffect = new Effect(12f, e -> {
                Draw.color(Pal.lancerLaser, Color.white,e.fin());
                Drawf.tri(e.x, e.y, 5, 15, 90);
		Drawf.tri(e.x, e.y, 5, 15, 180);
		Drawf.tri(e.x, e.y, 5, 15, 270);
		Drawf.tri(e.x, e.y, 5, 15, 360);
                Draw.reset();
				});
 
				despawnEffect = new Effect(32f, e -> {
					Draw.color(Color.white, Pal.lancerLaser,e.fin());
					stroke(e.fout() * 2);
					circle(e.x, e.y, e.fin() * 40);
					Fill.circle(e.x, e.y, e.fout() * e.fout() * 10);
					randLenVectors(e.id, 10, 5 + 55 * e.fin(), (x, y) -> {
						Fill.circle(e.x + x, e.y + y, e.fout() * 5f);
					});
				});
 
				smokeEffect = new Effect(12f, e -> {
                Draw.color(Pal.lancerLaser, Color.white,e.fin());
                Drawf.tri(e.x, e.y, 5, 15, 90);
		Drawf.tri(e.x, e.y, 5, 15, 180);
		Drawf.tri(e.x, e.y, 5, 15, 270);
		Drawf.tri(e.x, e.y, 5, 15, 360);
                Draw.reset();
				});
				pierce = false;
				hitSize = 10;
				splashDamage = 50f;
			}
 
		};
}
}