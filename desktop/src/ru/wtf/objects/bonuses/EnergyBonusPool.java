package ru.wtf.objects.bonuses;

import ru.wtf.screens.GameScreen;
import ru.wtf.sprites.EnergyBonus;

import com.badlogic.gdx.utils.Pool;

public class EnergyBonusPool extends Pool<EnergyBonus>{

	
	int cnt;
	private GameScreen screen;

	public EnergyBonusPool(GameScreen screen){
		this.screen = screen;
	}
	
	@Override
	protected EnergyBonus newObject() {
		return new EnergyBonus(screen, "EnergyBonus"+ cnt++);
	}

}
