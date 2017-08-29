package ru.wtf.sprites;

import ru.wtf.MyGdxGame;
import ru.wtf.objects.BodyEditorLoader;
import ru.wtf.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Bullet extends Sprite{
		
	public enum State{OUT_OF_BORDER, STAYING, FLYING};
	public World world;
	public Body b2body;
	
	public State currentState = State.FLYING;
	public State prevState = State.FLYING;
	
	public Texture bulletTexture;
	private TextureRegion textureRegion;

	public Texture bulletTextureActive;
	private TextureRegion textureRegionActive;

	
    public OrthographicCamera cam;
    
    private float stateTimer;
	public int angularForce = 1500;
    private int forvardForce = 3000;
	private boolean isGoingForward;
	private float changeTimer = 0;
	private int mode = 1;
	
	private static float energy = 100;
	private static float health = 100;
	
	public Bullet(World world){
		this.world = world;
		redefine("spire2.json",0.1f, 0.3f, new Vector2(100,100));
		createSprites("bullet.png", "bulletActive.png");
		energy = 100;
		health = 100;
	}
	
	public void define(){
		
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("shell.json"));
		//"spire2.json"
		
	    BodyDef bd = new BodyDef();
	    bd.position.set(100, 100);
	    bd.type = BodyType.DynamicBody;
	 
	    FixtureDef fd = new FixtureDef();
	    fd.density = 0.1f;
	    fd.friction = 0.0005f;
	    fd.restitution = 0.3f;
	 
	    b2body = world.createBody(bd);
	    
	    loader.attachFixture(b2body, "Name", fd, 10);		
	}

	public void redefine(String json, float density, float restitution, Vector2 position){
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal(json));
		BodyDef bd = new BodyDef();
		bd.position.set(position);
		bd.type = BodyType.DynamicBody;
		 
	    FixtureDef fd = new FixtureDef();
	    fd.density = density;
	    fd.friction = 0.0005f;
	    fd.restitution = restitution;
	 
	    b2body = world.createBody(bd);
	    
	    loader.attachFixture(b2body, "Name", fd, 10);	
	}
	
    private void createSprites(String normal, String active) {
    	bulletTexture = new Texture(Gdx.files.internal(normal));
    	textureRegion = new TextureRegion(bulletTexture,0,0,bulletTexture.getWidth(),bulletTexture.getHeight());
    	
    	bulletTextureActive = new Texture(Gdx.files.internal(active));
    	textureRegionActive =  new TextureRegion(bulletTextureActive,0,0,bulletTextureActive.getWidth(),bulletTextureActive.getHeight());
    	
    	setBounds(0, 0, bulletTexture.getWidth(), bulletTexture.getHeight());
    	
    	
    	bulletTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	setOrigin(bulletTexture.getWidth()/2, bulletTexture.getHeight()/2);
        setTexture(bulletTexture);
    	setRegion(textureRegion);
    }
	
    public void rotateRight(){
    	b2body.applyForce(new Vector2(200,0), new Vector2(0,40),true );
    }
    
    public void rotateLeft(){
    	b2body.applyForce(new Vector2(200,0), new Vector2(0,40),true );
    }
    
	public void update(float dt){
		energy = 10000;
		world.step(1/60f, 6, 2);
		changeTimer += dt;
		//System.out.println("energy=" + energy + " force=" + forvardForce + " speed="+getSpeed());		
		setPosition(b2body.getPosition().x * MyGdxGame.PPM - bulletTexture.getWidth()/2, 100 + bulletTexture.getHeight()/2 + 5);		
		setRotation((float) Math.toDegrees(b2body.getAngle()));
		
		if(isGoingForward && energy > 0){
			energy -= dt*5;
			forward();
		}
		
		if(b2body.getLinearVelocity().y < 60 && b2body.getPosition().y > 300){
			if(currentState.equals(State.OUT_OF_BORDER)){
				health -= dt*10;
			} else {
				health -= dt*3;
			}
		} else if(b2body.getLinearVelocity().y > 100 && b2body.getPosition().y > 300 && health < 100){
			if(currentState.equals(State.OUT_OF_BORDER)){
				health -= dt*10;
			} else {
				health += dt/2;
			}
		}
				
		stateTimer += dt;
		
		if(b2body.getPosition().x < 0 || b2body.getPosition().x > MyGdxGame.WIDTH / MyGdxGame.PPM){
			setState(State.OUT_OF_BORDER);
			health = -1;
		} else {
			setState(State.FLYING);
		}
		
	}

	public void setState(State state){
		if(currentState.equals(state)){
			return;
		}
		prevState = currentState;
		currentState = state;
		stateTimer = 0;
	}
	
	public void right(){
	}
	
	public void stopRotating() {
    	b2body.setAngularVelocity(0f);
	}

	public float getSpeed(){
		return b2body.getLinearVelocity().y;
	}
	
	public void forward(){
		int f = forvardForce;
		b2body.applyForce(new Vector2(-b2body.getAngle()*f,f), b2body.getWorldCenter(), true);
	}

	public void setGoingForward(boolean isGoingForward){
		this.isGoingForward = isGoingForward;
		if(isGoingForward){
	    	setOrigin(bulletTextureActive.getWidth()/2, bulletTextureActive.getHeight()/2);
	        setTexture(bulletTextureActive);
	    	setRegion(textureRegionActive);
		} else {
	    	setOrigin(bulletTexture.getWidth()/2, bulletTexture.getHeight()/2);
	        setTexture(bulletTexture);
	    	setRegion(textureRegion);
		}
	}
	
	public float getStateTimer() {
		return stateTimer;
	}

	public State getState(){
		return currentState;
	}

	public float getEnergy() {
		return energy;
	}

	public static void addEnergy(float i) {
		energy += i;
	}
	
	public void switchMode(int bulletMode){
		System.out.println(bulletMode + " == " + mode + " changeTimer=" + changeTimer);
		if(mode == bulletMode || changeTimer  < 3){
			return;
		}
		mode  = bulletMode;
		changeTimer = 0;
		
		Vector2 linVel = b2body.getLinearVelocity();
		float anVel = b2body.getAngularVelocity();
		float angle = b2body.getAngle();		
		
		world.destroyBody(b2body);
		if(bulletMode == 1){
			createSprites("bullet.png", "bulletActive.png");
			redefine("spire2.json",0.1f, 0.3f, b2body.getPosition());
		} else if (bulletMode == 2){
			createSprites("shell.png", "shellActive.png");
			redefine("shell.json",0.35f, 0.1f, b2body.getPosition());
		}
		b2body.setAngularVelocity(anVel);
		b2body.setLinearVelocity(linVel);
		b2body.setTransform(b2body.getWorldCenter(), angle);
	}

	public float getHealth() {
		return health;
	}

	public void addHealth(float h) {
		Bullet.health += h ;
	}
	
}
