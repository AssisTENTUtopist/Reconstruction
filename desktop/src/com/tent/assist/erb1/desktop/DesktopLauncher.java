package com.tent.assist.erb1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tent.assist.erb1.GdxErb;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GdxErb(), config);
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
	}
}
