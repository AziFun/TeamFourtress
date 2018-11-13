package com.fourtress.model;

import com.fourtress.model.Sensor;
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
		if (fa.getBody().getUserData() != null && fa.getBody().getUserData().getClass() == Sensor.class) {
			System.out.println(((Sensor) fa.getBody().getUserData()).getMessage());
		}
		if (fb.getBody().getUserData() != null && fa.getBody().getUserData().getClass() == Sensor.class) {
			System.out.println(((Sensor) fb.getBody().getUserData()).getMessage());
		}
		if (fa.getBody().getUserData() == "finish" || fb.getBody().getUserData() == "finish") {
			parent.gameScreen.parent.changeScreen(ScreenType.FINISH);
		}
	}

	@Override
	public void endContact(Contact contact) {

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
