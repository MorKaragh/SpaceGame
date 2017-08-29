package ru.wtf.sprites;

import ru.wtf.MyGdxGame;
import ru.wtf.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public abstract class Bonus extends Sprite{
	
	protected GameScreen gameScreen;
	protected World world;
	protected Texture texture;
	protected TextureRegion textureRegion; 
	protected String id;
	private Body body;
	
	private BodyDef bdef = new BodyDef();
	private PolygonShape shape = new PolygonShape();
	private FixtureDef fdef = new FixtureDef();
	private Fixture fixture;
	
	public Bonus(GameScreen gamescreen, String id){
		this.gameScreen = gamescreen;
		this.world = gamescreen.getWorld();
		this.id = id; 
		createSprites();
		buildBody();
	}
	
	
    private void createSprites() {
		texture = buildTexture();
    	textureRegion = buildTextureRegion();
    	setBounds(0, 0, texture.getWidth(), texture.getHeight());
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	setOrigin(0, 0);
        setTexture(texture);
    	setRegion(textureRegion);
    }
	
	private TextureRegion buildTextureRegion() {
		return new TextureRegion(texture,0,0,texture.getWidth(),texture.getHeight());
	}

	private Texture buildTexture() {
    	return new Texture(Gdx.files.internal(getTextureName()));
	}
	
	protected abstract String getTextureName();

	protected abstract void defineBonus();
	
	public void update(float camY){
		if(body != null){
			float x = (body.getWorldCenter().x - texture.getWidth()/2 ) * MyGdxGame.PPM;
			float y = ((body.getWorldCenter().y - camY) * MyGdxGame.PPM) + 300;
			setOrigin(texture.getWidth()/2, texture.getWidth()/2);
			setPosition(x + texture.getWidth()/2, y + texture.getWidth()*2 );
			setRotation((float) Math.toDegrees(body.getAngle()));
		}
	}
	
	
	private void buildBody() {
		bdef.type = BodyDef.BodyType.DynamicBody;
		shape.setAsBox(texture.getWidth()/(MyGdxGame.PPM*2), texture.getHeight()/(MyGdxGame.PPM*2));
		fdef.shape = shape;
		fdef.density = 0.2f;
		fdef.friction = 4f;
	}
	
	public void setBodyPosition(float x, float y){
		body.getPosition().x = x;
		body.getPosition().y = y;
	}
	
	public void addToWorld(float x, float y){
		bdef.position.set(x, y);
		setPosition(x, y);
		body = world.createBody(bdef);
		fixture = body.createFixture(fdef);
		fixture.setUserData(id);
	}
	
	public void deleteFromWorld() {
		world.destroyBody(body);		
	}
	
	public Vector2 getBodyPosition(){
		return body.getPosition();
	}


	public abstract void process();


	public boolean hasFixture(Fixture fixtureB) {
		return body != null && fixture != null && fixture.getUserData().equals(fixtureB.getUserData());
	}
	
}
