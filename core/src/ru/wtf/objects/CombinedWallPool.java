package ru.wtf.objects;

import com.badlogic.gdx.physics.box2d.World;

import ru.wtf.sprites.SimpleWallPart;

public class CombinedWallPool {

	WallPartPool softWalls;
	WallPartPool hardWalls;
	WallPartPool fireWalls;
	WallPartPool greyWalls;

	public CombinedWallPool(World world){
		this.softWalls = new WallPartPool(world, WallType.SOFT);
		this.hardWalls = new WallPartPool(world, WallType.HARD);
		this.fireWalls = new WallPartPool(world, WallType.FIRE);
		this.greyWalls = new WallPartPool(world, WallType.GREY);

	}
	
	public SimpleWallPart obtain(WallType type){
		switch (type) {
			case FIRE:
				return fireWalls.obtain();
			case HARD:
				return hardWalls.obtain();
			case SOFT:
				return softWalls.obtain();	
			case GREY:
				return greyWalls.obtain();	
			default: return null;
		}
	}
	
	public void free(SimpleWallPart p){
		switch(p.getType()){
			case FIRE:
				fireWalls.free(p);
				break;
			case HARD:
				hardWalls.free(p);
				break;
			case SOFT:
				softWalls.free(p);
				break;
			case GREY:
				greyWalls.free(p);
		}
	}
	
}
