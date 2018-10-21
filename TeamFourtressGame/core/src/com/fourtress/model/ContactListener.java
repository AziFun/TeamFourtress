package com.fourtress.model;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

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
		if (fa.getBody().getUserData() == "sensor") {
			parent.controller.enableSwitch();
		}
		if (fb.getBody().getUserData() == "sensor") {
			parent.controller.enableSwitch();
		}
	}

	@Override
	public void endContact(Contact contact) {

		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		// TODO Auto-generated method stub
		if (fa.getBody().getUserData() == "sensor") {
			parent.controller.disableSwitch();
		}
		if (fb.getBody().getUserData() == "sensor") {
			parent.controller.disableSwitch();
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
