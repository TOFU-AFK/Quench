package quench.contents.types;

import mindustry.entities.*;
import arc.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
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

import static mindustry.Vars.*;

//冲击工厂(碳纤维加工厂)
public class QUImpactFactory extends GenericCrafter{
    public final int timerUse = timers++;

    public float warmupSpeed = 0.001f;
    public float itemDuration = 60f;
    public int explosionRadius = 50;
    public int explosionDamage = 2000;

    public Color plasma1 = Color.valueOf("ffd06b"), plasma2 = Color.valueOf("ff361b");

    public @Load("@-bottom") TextureRegion bottomRegion;
    public @Load(value = "@-plasma-#", length = 4) TextureRegion[] plasmaRegions;

    public QUImpactFactory(String name){
        super(name);
        hasPower = true;
        hasItems = true;
        flags = EnumSet.of(BlockFlag.reactor, BlockFlag.generator);
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{bottomRegion, region};
    }

    public class QUAdvancedFactory extends QUAdvancedFactoryBuild{
        public float warmup;

        @Override
        public void updateTile(){
            if(consValid() && power.status >= 0.99f){

                warmup = Mathf.lerpDelta(warmup, 1f, warmupSpeed * timeScale);
                if(Mathf.equal(warmup, 1f, 0.001f)){
                    warmup = 1f;
                }

                if(timer(timerUse, itemDuration / timeScale)){
                    consume();
                }
            }else{
                warmup = Mathf.lerpDelta(warmup, 0f, 0.01f);
            }
        }

        @Override
        public float ambientVolume(){
            return warmup;
        }

        @Override
        public void draw(){
            Draw.rect(bottomRegion, x, y);

            for(int i = 0; i < plasmaRegions.length; i++){
                float r = size * tilesize - 3f + Mathf.absin(Time.time, 2f + i * 1f, 5f - i * 0.5f);

                Draw.color(plasma1, plasma2, (float)i / plasmaRegions.length);
                Draw.alpha((0.3f + Mathf.absin(Time.time, 2f + i * 2f, 0.3f + i * 0.05f)) * warmup);
                Draw.blend(Blending.additive);
                Draw.rect(plasmaRegions[i], x, y, r, r, Time.time * (12 + i * 6f) * warmup);
                Draw.blend();
            }

            Draw.color();

            Draw.rect(region, x, y);

            Draw.color();
        }

        @Override
        public void drawLight(){
            Drawf.light(team, x, y, (110f + Mathf.absin(5, 5f)) * warmup, Tmp.c1.set(plasma2).lerp(plasma1, Mathf.absin(7f, 0.2f)), 0.8f * warmup);
        }
        
        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.heat) return warmup;
            return super.sense(sensor);
        }

        @Override
        public void onDestroyed(){
            super.onDestroyed();

            if(warmup < 0.4f || !state.rules.reactorExplosions) return;

            Sounds.explosionbig.at(tile);

            Effect.shake(6f, 16f, x, y);
            Fx.impactShockwave.at(x, y);
            for(int i = 0; i < 6; i++){
                Time.run(Mathf.random(80), () -> Fx.impactcloud.at(x, y));
            }

            Damage.damage(x, y, explosionRadius * tilesize, explosionDamage * 4);


            for(int i = 0; i < 20; i++){
                Time.run(Mathf.random(80), () -> {
                    Tmp.v1.rnd(Mathf.random(40f));
                    Fx.explosion.at(Tmp.v1.x + x, Tmp.v1.y + y);
                });
            }

            for(int i = 0; i < 70; i++){
                Time.run(Mathf.random(90), () -> {
                    Tmp.v1.rnd(Mathf.random(120f));
                    Fx.impactsmoke.at(Tmp.v1.x + x, Tmp.v1.y + y);
                });
            }
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(warmup);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            warmup = read.f();
        }
    }
}
