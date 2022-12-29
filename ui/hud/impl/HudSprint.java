package dev.rice.ui.hud.impl;

import dev.rice.Rice;
import dev.rice.events.impl.EventAttack;
import dev.rice.modules.impl.render.HUD;
import dev.rice.ui.hud.Hud;
import net.minecraft.client.Minecraft;

public class HudSprint extends Hud {
	
	public String status;
	
	public HudSprint() {
		super("Sprint", 3, 120, true, true);
	}
	
	public void onUpdateAlways() {
		if(mc.gameSettings.isKeyDown(mc.gameSettings.keyBindSprint)) {
			status = "KeyPressed";
		}else if(Rice.instance.moduleManager.isModuleEnable("Sprint")) {
			status = "Toggled";
		}
	}
	
	public void draw() {
		final boolean sprinting = mc.thePlayer.isSprinting();
		if(sprinting) {
			fr.drawStringWithShadow("[Sprinting (" + this.status + ")]", this.posX, this.posY, -1);
		}
	}
}
