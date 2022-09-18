package ru.wtf.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import ru.wtf.MyGdxGame;
import ru.wtf.Utils;
import ru.wtf.sprites.SimpleWallPart;

public class ClowdManager {

	private ArrayList<SimpleWallPart> parts;
	private ArrayList<SimpleWallPart> toRemove;
	private Camera cam;
	private CombinedWallPool pool;

	public int currentPosition = 500;
	
	public ClowdManager(World world, Camera cam){
		this.pool = new CombinedWallPool(world);
		this.cam = cam;
		this.parts = new ArrayList<SimpleWallPart>();
		this.toRemove = new ArrayList<SimpleWallPart>();
	}
	
	public void buildRow(float y){
		int rnd = Utils.randInt(6, 10);
		for(int i = 0; i < MyGdxGame.WIDTH; i+=rnd){
			if(Utils.randInt(0, 3) > 0){
				if(Utils.randInt(0, 100) > 99){
					buildNextBody(i, y, 41f, WallType.GREY);
				} else {
					buildNextBody(i, y, 41f, WallType.GREY);
				}
			}
		}
	}
	
	private SimpleWallPart buildNextBody(float prevX, float prevY, float angle, WallType wallType){
		SimpleWallPart body = pool.obtain(wallType);
		body.addToWorld(prevX / MyGdxGame.PPM,prevY / MyGdxGame.PPM, angle);
		parts.add(body);
		return body;
	}
	
	public void update(float dt){
		if(parts.isEmpty()){
			return;
		}
		toRemove.clear();
		for(int i = 0; i < parts.size(); i++){
			SimpleWallPart simpleWallPart = parts.get(i);
			simpleWallPart.update(cam.position.y);
			
			if(simpleWallPart.getPosition().y < cam.position.y - 200){
				toRemove.add(simpleWallPart);
			}
		}
		for(SimpleWallPart i : toRemove){
			i.removeFromWorld();
			pool.free(i);
			parts.remove(i);
		}
	}
	
	public void draw(SpriteBatch batch){
		for(int i = 0; i < parts.size(); i++){
			parts.get(i).draw(batch);
		}
	}
	
}
