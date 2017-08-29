package ru.wtf.hud;

import ru.wtf.MyGdxGame;
import ru.wtf.sprites.Bullet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {

	public Stage stage;
	private Viewport viewport;
	
	private float score;
	
	//private Label countdownLbl;
	private Label scoreLabel;
	private Label speedLabel;
	private Label energyLabel;
	private Label healthLabel;

	private Bullet bullet;
	
	public Hud(SpriteBatch sb, Bullet bullet){
		this.bullet = bullet;

		score = 0;
		
		viewport = new FitViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT, new OrthographicCamera());
		
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		//countdownLbl = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label("DISTANCE: " + String.format("%06d", (new Float(score)).intValue()), new Label.LabelStyle(new BitmapFont(), Color.GREEN));

		speedLabel = new Label("SPEED: " + String.format("%03d", floatToInt(bullet.getSpeed())), new Label.LabelStyle(new BitmapFont(), Color.FIREBRICK));
		energyLabel = new Label("ENERGY: " + String.format("%03d", floatToInt(bullet.getEnergy())), new Label.LabelStyle(new BitmapFont(), Color.CYAN));
		healthLabel = new Label("HEALTH: " + String.format("%03d", floatToInt(bullet.getHealth())), new Label.LabelStyle(new BitmapFont(), Color.RED));

		//table.add(countdownLbl).expandX().padTop(10);
		table.add(scoreLabel).padTop(10).expandX();
		table.add(healthLabel).padTop(10).expandX();
		table.row();
		
		table.add(speedLabel).expandX();
		table.add(energyLabel).expandX();
		stage.addActor(table);
	}
	
	public void add(){
		score++;
	}
	
	private int floatToInt(float f){
		return (new Float(f)).intValue();
	}
	
	public void update(float dt){
		score+= dt;
		scoreLabel.setText("DISTANCE: " + String.format("%06d", (new Float(bullet.b2body.getPosition().y)).intValue()/10));
		speedLabel.setText("SPEED: " + String.format("%03d", floatToInt(bullet.getSpeed())));
		energyLabel.setText("ENERGY: " + String.format("%03d", floatToInt(bullet.getEnergy())));
		healthLabel.setText("HEALTH: " +String.format("%03d", floatToInt( bullet.getHealth())));
		
	}
	
}
