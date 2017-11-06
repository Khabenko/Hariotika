package com.hariotika.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hariotika.Hariotika;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = new Hariotika().HEIGHT;
		config.width = new Hariotika().WIDTH;
		config.title = new Hariotika().TITLE;
		new LwjglApplication(new Hariotika(), config);
	}
}
