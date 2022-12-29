package dev.rice.ui.hud.impl;

import java.util.Collection;

import dev.rice.Rice;
import dev.rice.events.impl.EventAttack;
import dev.rice.modules.impl.render.HUD;
import dev.rice.ui.hud.Hud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HudEffect extends Hud {
	
	public int ms;
	
	public HudEffect() {
		super("HudFps", 3, 30, true, true);
	}
	
	public void onUpdateAlways() {
		
	}
	
	public void draw() {
		fr.drawStringWithShadow("[FPS: " + mc.getDebugFPS() + "]", this.posX, this.posY, -1);
	}
}
