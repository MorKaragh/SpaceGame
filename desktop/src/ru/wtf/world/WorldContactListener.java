package ru.wtf.world;

import ru.wtf.objects.BonusManager;
import ru.wtf.sprites.EnergyBonus;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {


	}

	@Override
	public void endContact(Contact contact) {
		//System.out.println("endContact");
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		if(contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().toString().startsWith("EnergyBonus")){
			contact.getFixtureB().setSensor(true);
			BonusManager.processHit(contact.getFixtureB());
		}		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		//System.out.println("postSolve");
		
	}

}
