package ru.wtf.objects;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

import ru.wtf.objects.bonuses.EnergyBonusPool;
import ru.wtf.screens.GameScreen;
import ru.wtf.sprites.Bonus;
import ru.wtf.sprites.EnergyBonus;

public class BonusManager {
	
	private GameScreen screen;
	private Camera cam;
	private static LinkedList<Bonus> activeBonuses = new LinkedList<Bonus>();
	private static LinkedList<Bonus> hitBonuses = new LinkedList<Bonus>();
	private World world;
	
	private Pool<EnergyBonus> energyPool;
	
	public BonusManager(GameScreen screen, Camera gamecam){
		this.screen = screen;
		this.world = screen.getWorld();
		this.cam = gamecam;
		
		this.energyPool = new EnergyBonusPool(screen);
		
	}
	
	public void update(float dt){	
		for(Bonus b : hitBonuses){
			b.process();
			removeBouns(b);
		}
		
		hitBonuses.clear();
		
		for (int i = 0; i < activeBonuses.size(); i++){
			Bonus b = activeBonuses.get(i);
			b.update(cam.position.y);
			if ( b.getY() < 0 ){
				removeBouns(b);
			}
		}
	}

	private void removeBouns(Bonus b) {
		if ( b instanceof EnergyBonus ){
			energyPool.free((EnergyBonus) b);
		}
		b.deleteFromWorld();
		activeBonuses.remove(b);
	}

	public void buildBonus(float x, float y, String type) {
		if ("energy".equals(type)){
			EnergyBonus b = energyPool.obtain(); 
			activeBonuses.add(b);
			b.addToWorld(x, y);
		}
	}

	public void draw(SpriteBatch batch) {
		for (Bonus b : activeBonuses){
			b.draw(batch);
		}	
	}

	public static void processHit(Fixture fixtureB) {
		for(Bonus i : activeBonuses){
			if(i.hasFixture(fixtureB)){
				hitBonuses.add(i);
			}
		}
	}

}
