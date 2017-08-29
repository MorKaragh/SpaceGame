package ru.wtf.states;

import ru.wtf.MyGdxGame;
import ru.wtf.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {

	private Texture playBtn;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		this.playBtn = new Texture("play.png");
	}

	@Override
	public void handleInput() {
		if(Gdx.input.justTouched()){
			dispose();
		}
	}

	@Override
	public void update(float rate) {
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(playBtn,(MyGdxGame.WIDTH/2) - (playBtn.getWidth()/2), MyGdxGame.HEIGHT/3);
		sb.end();
	}

	@Override
	public void dispose() {
		playBtn.dispose();
	}

}
