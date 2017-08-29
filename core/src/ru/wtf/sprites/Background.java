package ru.wtf.sprites;

import java.util.Vector;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Background {

	private Texture texture1 = new Texture("bg.png");
	private Texture texture2 = new Texture("bg.png");
	private Vector2 position1 = new Vector2();	
	private Vector2 position2 = new Vector2();
	private Vector2 velocity = new Vector2(0,-5);
	
	
	public Background(){
		position1.x = 0;
		position1.y = 0;
		
		position2.x = 0;
		position2.y = texture2.getHeight();
	}
	
	
	public void update(float rate){
		position1.add(velocity);
		position2.add(velocity);
		if((position1.y + texture1.getHeight()) < 0){
			position1.y = position2.y + texture2.getHeight();
		}
		
		if((position2.y + texture2.getHeight()) < 0){
			position2.y = position1.y + texture1.getHeight();
		}
	}


	public Texture getTexture1() {
		return texture1;
	}

	public Texture getTexture2() {
		return texture2;
	}

	public Vector2 getPosition1() {
		return position1;
	}

	public Vector2 getPosition2() {
		return position2;
	}

	
}

