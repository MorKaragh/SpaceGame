package ru.wtf.objects;

import java.util.ArrayList;
import java.util.LinkedList;

import ru.wtf.MyGdxGame;
import ru.wtf.Utils;
import ru.wtf.sprites.SimpleWallPart;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class SimpleWallManager {

	private CombinedWallPool pool;
	private float deltaYrow;
	private float deltaXrow;
	private float deltaY;
	private float deltaX;
	
	private Camera cam;
		
	private ArrayList<SimpleWallPart> parts;
	private ArrayList<SimpleWallPart> toRemove;
	
	public SimpleWallManager(World world, Camera cam){
		this.cam = cam;
		this.pool = new CombinedWallPool(world);
		this.parts = new ArrayList<SimpleWallPart>();
		this.toRemove = new ArrayList<SimpleWallPart>();
	}
	
	public void buildWall(float x, float y, float angle, int thickness, float distance, WallType wallType){
		deltaY = (float) (Math.sin(Math.toRadians(angle)) * distance);
		deltaX = (float) (Math.cos(Math.toRadians(angle)) * distance);
		deltaXrow = (float) (Math.cos(Math.toRadians(90-angle)) * distance);
		deltaYrow = (float) (Math.sin(Math.toRadians(90-angle)) * distance);
		
		for(int i = 0; i < thickness; i++){
			buildRow(x-(deltaXrow*i) / MyGdxGame.PPM,y+(deltaYrow*i) / MyGdxGame.PPM,100,angle, wallType);
		}
	}
	
	private void buildRow(float x, float y, float width, float angle, WallType wallType) {
		SimpleWallPart body = pool.obtain(wallType);	
		body.addToWorld(x, y, angle);
		parts.add(body);

		while(body.getPosition().x < MyGdxGame.WIDTH/2){
			body = buildNextBody(body.getPosition().x, body.getPosition().y, 15, angle, wallType);
		}
	}
	
	private SimpleWallPart buildNextBody(float prevX, float prevY, float distance, float angle, WallType wallType){
		SimpleWallPart body = pool.obtain(wallType);
		body.addToWorld(prevX + deltaX / MyGdxGame.PPM,prevY + deltaY / MyGdxGame.PPM, angle);
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
