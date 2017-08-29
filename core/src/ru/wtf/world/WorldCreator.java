package ru.wtf.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class WorldCreator {
	

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    

	public WorldCreator(){
	
	for(MapObject m : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
		
		
		mapLoader = new TmxMapLoader();
		map = mapLoader.load("level1.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		Rectangle rect = ((RectangleMapObject)m).getRectangle();
		
//		bdef.type = BodyDef.BodyType.StaticBody;
//		bdef.position.set(rect.getX() + rect.getWidth() /2, rect.getY() + rect.getHeight() / 2 );
//		
//		body = world.createBody(bdef);
//		shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
//		
//		fdef.shape = shape;
//		body.createFixture(fdef);
		
		
		
		float y = rect.getY();

		//walls.add(new SimpleWall(0, y, 12d, 1, 5, world));


	}
	}

	//in the update
    //mapRenderer.render();

}
