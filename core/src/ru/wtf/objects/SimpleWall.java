package ru.wtf.objects;

import java.util.ArrayList;

import ru.wtf.MyGdxGame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class SimpleWall implements Disposable{

	private World world;
	private ArrayList<Body> bodies;
	float deltaY;
	float deltaX;
	
	float deltaXrow;
	float deltaYrow;
	
	float maxY = 0;
		
	public SimpleWall(float x, float y, double angle, int thickness, float distance, World world){
		
		this.world = world;
		this.bodies = new ArrayList<Body>();
		
		deltaY = (float) (Math.sin(Math.toRadians(angle)) * distance);
		
		deltaX = (float) (Math.cos(Math.toRadians(angle)) * distance);
		
		deltaXrow = (float) (Math.cos(Math.toRadians(90-angle)) * distance);

		deltaYrow = (float) (Math.sin(Math.toRadians(90-angle)) * distance);

		for(int i = 0; i < thickness; i++){
			buildRow(x-(deltaXrow*i) / MyGdxGame.PPM,y+(deltaYrow*i) / MyGdxGame.PPM,100,angle);
		}
		
	}

	private void buildRow(float x, float y, float width, double angle) {
		Body body = buildBody(x, y);
		for(int i = 1; i < width; i++){
			body = buildNextBody(body.getPosition().x, body.getPosition().y, 5, angle);
		}
	}
	
	
	private Body buildNextBody(float prevX, float prevY, float distance, double angle){
		Body body = buildBody(prevX + deltaX / MyGdxGame.PPM,prevY + deltaY / MyGdxGame.PPM);
		return body;
	}

	private Body buildBody(float x, float y) {
		if(maxY < y){
			maxY = y;
		}
		BodyDef bdef = new BodyDef();
		CircleShape shape = new CircleShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(x, y);
		body = world.createBody(bdef);
		shape.setRadius(2 / MyGdxGame.PPM);
		fdef.shape = shape;
		fdef.density = 0.2f;
		fdef.friction = 4f;
		body.createFixture(fdef);
		bodies.add(body);
		return body;
	}
	
	public void update(){
    	for(Body i : bodies){
    		Vector2 velocity = i.getLinearVelocity();
    		if(velocity.x > 0){
    			velocity.x--;
    		}
    		if(velocity.y > 0){
    			velocity.y--;
    		}
    		
    		if(velocity.x < 0){
    			velocity.x++;
    		}
    		if(velocity.y < 0){
    			velocity.y++;
    		}
    		
    		if(Math.abs(velocity.y) > 0 && Math.abs(velocity.y) < 1){
    			velocity.y = 0;
    		}
    		if(Math.abs(velocity.x) > 0 && Math.abs(velocity.x) < 1){
    			velocity.x = 0;
    		}
    		i.setLinearVelocity(velocity);
    	}
	}

	@Override
	public void dispose() {
		for(Body b : bodies){
			for(Fixture f : b.getFixtureList()){
				b.destroyFixture(f);
			}
			world.destroyBody(b);
		}
		bodies = null;
	}

	public Object getPosition() {
		return null;
	}
	
	public float getMaxY(){
		return maxY;
	}
	
}
