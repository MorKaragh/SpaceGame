package ru.wtf;

import ru.wtf.screens.GameScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game{

	public enum State {
		FLYING, DEAD
	};
	
	public static final int WIDTH = 480;
    public static final int HEIGHT = 700;
    public static final float PPM = 2;
    
    public static int lol = 0;
    
    public SpriteBatch batch;
    
    
    
	@Override
	public void create() {
		Gdx.app.log("LOL","lol");
		this.batch = new SpriteBatch();
		setScreen(new GameScreen(this));
	}
	
	@Override
	public void render(){
		super.render();
	}

	public void refresh() {
		
	}

}
