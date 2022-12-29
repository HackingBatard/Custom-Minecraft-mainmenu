package dev.rice.ui.hud;

import java.util.concurrent.CopyOnWriteArrayList;

import dev.rice.ui.hud.impl.*;

public class HudManager {
	
	public static CopyOnWriteArrayList<Hud> hud = new CopyOnWriteArrayList<Hud>();
	
	public HudManager() {
		hud.add(new HudReach());
		hud.add(new HudFps());
		hud.add(new HudKeyStroke());
		hud.add(new HudSprint());
	}
	
	public Hud getHud(String name) {
		for(Hud hud : hud) {
			if(hud.getName().equalsIgnoreCase(name)) {
				return hud;
			}
		}
		return null;
	}
}
