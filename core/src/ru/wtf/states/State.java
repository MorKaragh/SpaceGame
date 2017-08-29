package ru.wtf.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
	protected OrthographicCamera cam;
	protected Vector3 mouse;
	protected GameStateManager gsm;
	
	
	public State(GameStateManager gsm){
		this.gsm = gsm;
		this.mouse = new Vector3();
		this.cam = new OrthographicCamera();
	}
	
	public abstract void handleInput();
	public abstract void update(float rate);
	public abstract void render(SpriteBatch sprite);
	public abstract void dispose();
}
