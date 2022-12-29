package dev.rice.ui.hud.impl;

import dev.rice.Rice;
import dev.rice.events.impl.EventAttack;
import dev.rice.modules.impl.render.HUD;
import dev.rice.ui.hud.Hud;
import net.minecraft.client.Minecraft;

public class HudFps extends Hud {
	
	public int ms;
	
	public HudFps() {
		super("Fps", 3, 50, true, true);
	}
	
	public void onUpdateAlways() {
		
	}
	
	public void draw() {
		fr.drawStringWithShadow("[FPS: " + mc.getDebugFPS() + "]", this.posX, this.posY, -1);
	}
}
