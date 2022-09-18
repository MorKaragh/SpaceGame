package ru.wtf.sprites;

import ru.wtf.screens.GameScreen;

public class EnergyBonus extends Bonus{
	public EnergyBonus(GameScreen gamescreen, String id) {
		super(gamescreen, id);
	}

	@Override
	protected void defineBonus() {

	}

	@Override
	protected String getTextureName() {
		return "blueSquare20.png";
	}

	@Override
	public void process() {
		Bullet.addEnergy(10f);
	}

	
}
