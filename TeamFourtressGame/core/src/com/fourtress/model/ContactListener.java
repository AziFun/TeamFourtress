package com.fourtress.model;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.fourtress.ScreenType;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {

	private Box2dModel parent;

	public ContactListener(Box2dModel parent) {
		this.parent = parent;
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		// Apply small opposite force
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if (fa.getBody().getType() == BodyType.DynamicBody) {
			// apply small force opposite to angle of contact
		}
		if (fb.getBody().getType() == BodyType.DynamicBody) {
			// apply small force opposite to angle of contact
		}

		if (fa.getBody().getUserData() != null && fa.getBody().getUserData() instanceof InteractableEntity
				&& fb.getBody().getUserData() != null && fb.getBody().getUserData().equals("Player")) {
			parent.setPlayerAction((InteractableEntity) fa.getBody().getUserData());
		}
		if (fb.getBody().getUserData() != null && fb.getBody().getUserData() instanceof InteractableEntity
				&& fa.getBody().getUserData() != null && fa.getBody().getUserData().equals("Player")) {
			parent.setPlayerAction((InteractableEntity) fa.getBody().getUserData());
		}
		if (fa.getBody().getUserData() != null && fb.getBody().getUserData() != null) {
			if ((fa.getBody().getUserData() == "finish" && fb.getBody().getUserData().equals("Player"))
					|| (fb.getBody().getUserData() == "finish" && fa.getBody().getUserData().equals("Player"))) {
				//parent.gameScreen.parent.changeScreen(ScreenType.FINISH);
				System.out.println(parent.gameScreen.getLevel().isLastLevel());
				
				if (parent.gameScreen.getLevel().isLastLevel()) {
				parent.gameScreen.parent.changeScreen(ScreenType.FINISH);
			}
				else {
					parent.gameScreen.setupNextLevel();
				}
				
		}
	
		}}

	@Override
	public void endContact(Contact contact) {

		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		if (fa.getBody().getUserData() != null && fa.getBody().getUserData() instanceof InteractableEntity) {
			parent.endPlayerAction();
		}
		if (fb.getBody().getUserData() != null && fb.getBody().getUserData() instanceof InteractableEntity) {
			parent.endPlayerAction();
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
