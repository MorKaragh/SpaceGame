package ru.wtf.sprites;

import ru.wtf.MyGdxGame;
import ru.wtf.objects.WallType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class SimpleWallPart extends Sprite{
	
	private World world;
	private BodyDef bdef = new BodyDef();
	private CircleShape shape = new CircleShape();
	private FixtureDef fdef = new FixtureDef();
	private Body body;
	

	public Texture texture;
	private TextureRegion textureRegion;
	private WallType wallType;
	
	
    private void createSprites(String path) {
    	texture = new Texture(Gdx.files.internal(path));
    	textureRegion = new TextureRegion(texture,0,0,texture.getWidth(),texture.getHeight());
    	setBounds(0, 0, texture.getWidth(), texture.getHeight());
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	setOrigin(texture.getWidth()/2, texture.getHeight()/2);
        setTexture(texture);
    	setRegion(textureRegion);
    }
	
	
	public SimpleWallPart(World world, WallType wallType){
		this.world = world;
		this.wallType = wallType;
		buildBody();
		createSprites(wallType.getPath());
	}
	
	private void buildBody() {
		bdef.type = BodyDef.BodyType.DynamicBody;
		shape.setRadius(2 / MyGdxGame.PPM);
		fdef.shape = shape;
		fdef.density = wallType.getDensity();
		fdef.friction = 4f;
	}
	
	public void setBodyPosition(float x, float y){
		body.getPosition().x = x;
		body.getPosition().y = y;
	}
	
	public void addToWorld(float x, float y, float angle){
		bdef.position.set(x, y);
		body = world.createBody(bdef);
		body.setTransform(body.getPosition(), (float) Math.toRadians(angle));

		Fixture f = body.createFixture(fdef);
		f.setUserData("Wallpart");
	}
	
	public void removeFromWorld(){
		world.destroyBody(body);
	}

	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public void update(float camY){
		Vector2 velocity = body.getLinearVelocity();
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
		body.setLinearVelocity(velocity);
		
		float angular = body.getAngularVelocity();
		
		
		if(angular != 0){
			if(angular > 0 && angular > 0.05f || (angular < 0 && angular < 0.05f)){
				body.setAngularVelocity(angular/2);
			} else if ((angular > 0 && angular <= 0.05f) || (angular < 0 && angular >= 0.05f)){
				body.setAngularVelocity(0);
			}
		}
		

		
		setPosition((body.getPosition().x - texture.getWidth()/2 ) * MyGdxGame.PPM
				, (body.getPosition().y - camY + 171) * MyGdxGame.PPM + texture.getHeight()/3  );
		setRotation((float) Math.toDegrees(body.getAngle()));
		
	}


	private void setWallType(WallType wallType2) {
		this.wallType = wallType2;
		fdef.density = wallType2.getDensity();
		createSprites(wallType2.getPath());
	}
	
	public WallType getType(){
		return this.wallType;
	}

}
