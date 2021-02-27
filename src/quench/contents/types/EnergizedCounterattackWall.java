package quench.contents.types;

import mindustry.world.*;
import mindustry.entities.*;
import mindustry.game.*;
import arc.*;
import arc.math.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.ui.*;
import mindustry.ctype.*;
import mindustry.content.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.defense.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.campaign.*;
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
import quench.contents.effects.*;

public class EnergizedCounterattackWall extends Wall{
    public BulletType ownerBullet = null;
    public BulletType smallBullet = null;
    private float bulletAngle = 0;
    public float range = 200f;
    public EnergizedCounterattackWall(String name){
        super(name);
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        buildCostMultiplier = 5f;
    }

    @Override
    public void load(){
        super.load();
    }
    
    @Override
	public void setBars(){
		super.setBars();
		bars.add(Core.bundle.get("other.energy"), 
			(EnergizedCounterattackWallBuild entity) -> new Bar(
				() -> Core.bundle.get("other.energy"),
				() -> Color.valueOf("#6495ED"),
				() -> entity.energy / entity.maxEnergy
			)
		);
	}
	 
    public class EnergizedCounterattackWallBuild extends WallBuild{
        public float maxEnergy = 100f;
        public float energy = 0;
        
        @Override
        public void update(){
        }

        @Override
        public void draw(){
            super.draw();
            Teamc target = Units.closestTarget(team, x, y,range);
        if(target == null&&energy>0&&healthf()<maxHealth()){
            heal(energy);
            energy=0;
            QUFx.re_thunder_charging.at(this);
        }
        }

        @Override
        public boolean collision(Bullet bullet){
            super.collision(bullet);
            if(energy<maxEnergy&&energy+bullet.damage()<=maxEnergy){
            energy+=bullet.damage();
            }else if(energy+bullet.damage()>maxEnergy){
                energy=maxEnergy;
            }
            bulletAngle = bullet.rotation() + 180f;
            Teamc target = Units.closestTarget(team, x, y,range);
            if(energy>=maxEnergy&&ownerBullet!=null){
            if(target!=null){
                ownerBullet.create(this,x,y,bulletAngle);
                energy=0;
            }
        }else if(smallBullet!=null){
            smallBullet.create(this,x+Mathf.random(2,5),y+Mathf.random(2,5),bulletAngle);
        }
            return true;
        }
    }
}
