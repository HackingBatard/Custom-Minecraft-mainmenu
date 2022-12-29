package dev.rice.ui.hud.impl;

import dev.rice.Rice;
import dev.rice.events.impl.EventAttack;
import dev.rice.modules.impl.render.HUD;
import dev.rice.ui.hud.Hud;
import net.minecraft.client.Minecraft;

public class HudReach extends Hud {
	
	public HudReach() {
		super("Reach", 3, 30, true, true);
	}
	
	public void onUpdateAlways() {
		
	}
	
	public void draw() {
		fr.drawStringWithShadow("[" + Rice.instance.distance + " blocks]", this.posX, this.posY, -1);
	}
}
