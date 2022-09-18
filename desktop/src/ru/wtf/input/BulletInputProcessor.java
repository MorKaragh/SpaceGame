package ru.wtf.input;

import ru.wtf.MyGdxGame;
import ru.wtf.sprites.Bullet;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class BulletInputProcessor implements InputProcessor{

	private Bullet bullet;
	
	public BulletInputProcessor(Bullet b) {
		this.bullet = b;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
			case Keys.RIGHT:
				bullet.b2body.applyAngularImpulse(-bullet.angularForce / MyGdxGame.PPM, true);
				break;
			case Keys.LEFT:
				bullet.b2body.applyAngularImpulse(bullet.angularForce / MyGdxGame.PPM, true);
				break;
			case Keys.UP:
				//bullet.b2body.applyForce(new Vector2(-bullet.b2body.getAngle()*500,100), bullet.b2body.getWorldCenter(), true);
				//bullet.b2body.setLinearVelocity(velo);
				bullet.setGoingForward(true);
				break;
			case Keys.A:
				bullet.switchMode(1);
				break;
			case Keys.D:
				bullet.switchMode(2);
				break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
			case Keys.RIGHT:
				bullet.stopRotating();
				break;
			case Keys.LEFT:
				bullet.stopRotating();
				break;
			case Keys.UP:
				//bullet.b2body.applyForce(new Vector2(-bullet.b2body.getAngle()*500,100), bullet.b2body.getWorldCenter(), true);
				//bullet.b2body.setLinearVelocity(velo);
				bullet.setGoingForward(false);
				break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {		
		
		if(screenX > MyGdxGame.WIDTH*3/4){
				bullet.b2body.applyAngularImpulse(-300f, true);
		} else if (screenX < MyGdxGame.WIDTH / 4) {
				bullet.b2body.applyAngularImpulse(300f, true);
		} else {
			bullet.setGoingForward(true);

		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		bullet.stopRotating();
		bullet.setGoingForward(false);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}


}
