package ru.wtf.screens;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import ru.wtf.MyGdxGame;
import ru.wtf.Utils;
import ru.wtf.hud.Hud;
import ru.wtf.input.BulletInputProcessor;
import ru.wtf.objects.BonusManager;
import ru.wtf.objects.ClowdManager;
import ru.wtf.objects.SimpleWall;
import ru.wtf.objects.SimpleWallManager;
import ru.wtf.objects.WallType;
import ru.wtf.sprites.Background;
import ru.wtf.sprites.Bonus;
import ru.wtf.sprites.Bullet;
import ru.wtf.sprites.EnergyBonus;
import ru.wtf.sprites.SimpleWallPart;
import ru.wtf.world.WorldContactListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.PerformanceCounter;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

	// private GameStateManager gsm;
	private OrthographicCamera gamecam;
	// PerformanceCounter upc = new PerformanceCounter("updateGameScreen");

	private Viewport gamePort;

	private MyGdxGame game;

	private Bullet bullet;

	private Sprite bg;

	private Hud hud;
	private World world;
	private Box2DDebugRenderer debugRenderer;

	private float wallGenerationFactor = 300;

	private SimpleWallManager wallManager;
	private BonusManager bonusManager;
	private ClowdManager clowdManager;

	public GameScreen(MyGdxGame game) {
		this.game = game;

		this.gamecam = new OrthographicCamera();
		this.gamePort = new FitViewport(MyGdxGame.WIDTH / MyGdxGame.PPM,
				MyGdxGame.HEIGHT / MyGdxGame.PPM, gamecam);

		this.world = new World(new Vector2(0, 0), true);
		this.wallManager = new SimpleWallManager(world, gamecam);
		this.bonusManager = new BonusManager(this, gamecam);
		this.clowdManager = new ClowdManager(world, gamecam);

		this.bullet = new Bullet(world);

		bg = new Sprite(new Texture("bgBlack.png"));

		this.debugRenderer = new Box2DDebugRenderer();

		hud = new Hud(game.batch, bullet);

		gamecam.position.set(gamePort.getWorldWidth() / 2,
				gamePort.getWorldHeight() / 2, 0);

		Gdx.input.setInputProcessor(new BulletInputProcessor(bullet));

		world.setContactListener(new WorldContactListener());

		buildFirstWalls();

	}

	private void buildFirstWalls() {
		wallManager.buildWall(0, 200, 0, 1, 6, WallType.SOFT);
		bonusManager.buildBonus(100, 250, "energy");

		wallManager.buildWall(0, 300, 0, 1, 6, WallType.HARD);
		bonusManager.buildBonus(100, 350, "energy");

		wallManager.buildWall(0, 400, 0, 1, 6, WallType.FIRE);
		bonusManager.buildBonus(100, 450, "energy");
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public void wallGeneration() {
		if (gamecam.position.y > wallGenerationFactor) {
			wallGenerationFactor += 300;
			int thickness = randInt(1, 2);
			int angle = randInt(-35, 35);
			wallManager.buildWall(0, wallGenerationFactor, angle, thickness, 6,
					getRandomWalltype());
			int bonusX = randInt(20, MyGdxGame.WIDTH / 2 - 20);
			if (Utils.randInt(1, 2) == 2) {
				bonusManager.buildBonus(bonusX, wallGenerationFactor + 100,
						"energy");
			}
		}
	}

	int counter = 0;
	public void clowdGeneration() {
		if (counter++ == 3) {
			counter = 0;
			clowdManager.buildRow(gamecam.position.y*MyGdxGame.PPM + MyGdxGame.HEIGHT);
		}

	}

	private WallType getRandomWalltype() {
		int wt = randInt(1, 15);
		if (wt < 6) {
			return WallType.SOFT;
		} else if (wt >= 6 && wt <= 10) {
			return WallType.HARD;
		} else {
			return WallType.FIRE;
		}

	}

	public void update(float dt) {
		wallManager.update(dt);
		clowdManager.update(dt);
		bonusManager.update(dt);
		bullet.update(dt);
		gamecam.position.y = bullet.b2body.getPosition().y + 200
				/ MyGdxGame.PPM;
		gamecam.update();
		game.batch.setProjectionMatrix(gamecam.combined);
		hud.update(dt);
	}

	public void handleInput() {

	}

	@Override
	public void render(float delta) {
		update(delta);

		// Gdx.gl.glClearColor(109/255.0f, 112/255.0f, 62/255.0f, 1f);
		Gdx.gl.glClearColor(0 / 255.0f, 0 / 255.0f, 0 / 255.0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		wallGeneration();
		clowdGeneration();
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

		game.batch.begin();
		bg.draw(game.batch);
		bullet.draw(game.batch);
		wallManager.draw(game.batch);
		clowdManager.draw(game.batch);
		bonusManager.draw(game.batch);
		game.batch.end();

		hud.stage.draw();
		// debugRenderer.render(world, gamecam.combined);

		if (gameOver()) {
			game.setScreen(new GameOverScreen(game));
			dispose();
		}

	}

	private float getBgColor() {
		float factor = 221;
		if (bullet.getState().equals(Bullet.State.OUT_OF_BORDER)) {
			factor = (float) (10 * Math.floor(bullet.getStateTimer()));
		}
		return factor / 255.0f;
	}

	private boolean gameOver() {
		// return bullet.getStateTimer() > 1 &&
		// bullet.getState().equals(Bullet.State.OUT_OF_BORDER);
		return bullet.getHealth() < 0 || bullet.getEnergy() < 0;
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}

	@Override
	public void show() {
		Gdx.app.log("GameScreen", "show called");
	}

	@Override
	public void hide() {
		Gdx.app.log("GameScreen", "hide called");
	}

	@Override
	public void pause() {
		Gdx.app.log("GameScreen", "pause called");
	}

	@Override
	public void resume() {
		Gdx.app.log("GameScreen", "resume called");
	}

	@Override
	public void dispose() {
		// Leave blank
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
