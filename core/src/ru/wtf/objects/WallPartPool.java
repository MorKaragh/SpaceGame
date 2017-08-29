package ru.wtf.objects;

import ru.wtf.sprites.SimpleWallPart;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

public class WallPartPool extends Pool<SimpleWallPart> {

	private World world;
	private WallType type;	
	
	public WallPartPool(World world, WallType type){
		this.world = world;
		this.type = type;
	}
	
	@Override 
	public void free(SimpleWallPart object) {
		super.free(object);
	};
	
	
	@Override
	protected SimpleWallPart newObject() {
		return new SimpleWallPart(world, type);
	}

}
