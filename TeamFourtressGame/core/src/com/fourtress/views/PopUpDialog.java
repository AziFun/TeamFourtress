package com.fourtress.views;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PopUpDialog extends Dialog {

	public PopUpDialog(String title, Skin skin) {
		super(title, skin);
		button("Close",true);
		text("Are you sure?");
		setPosition(140, 10);
	    setWidth(300);
		setHeight(80);

	}
	
	@Override
	protected void result(Object object) {
		super.result(object);
		if(object.equals(true)) {
			this.hide();
		}
	}
	
	

}
