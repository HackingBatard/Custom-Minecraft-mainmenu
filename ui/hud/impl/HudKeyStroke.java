package dev.rice.ui.hud.impl;

import dev.rice.Rice;
import dev.rice.events.impl.EventAttack;
import dev.rice.modules.impl.render.HUD;
import dev.rice.ui.hud.Hud;
import dev.rice.utils.render.ColorUtil;
import dev.rice.utils.render.RenderUtil;
import dev.rice.utils.render.RoundedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class HudKeyStroke extends Hud {
	
	public boolean wPressed;
	public boolean aPressed;
	public boolean sPressed;
	public boolean dPressed;
	public boolean attack;
	public boolean use;
	public boolean jump;
	
	public int ms;
	
	public HudKeyStroke() {
		super("HudKeyStroke", 3, 100, false, true);
	}
	
	public void onUpdateAlways() {
		wPressed = mc.gameSettings.isKeyDown(mc.gameSettings.keyBindForward);
		aPressed = mc.gameSettings.isKeyDown(mc.gameSettings.keyBindLeft);
		sPressed = mc.gameSettings.isKeyDown(mc.gameSettings.keyBindBack);
		dPressed = mc.gameSettings.isKeyDown(mc.gameSettings.keyBindRight);
		attack = mc.gameSettings.keyBindAttack.pressed;
		use = mc.gameSettings.keyBindUseItem.pressed;
		jump = mc.gameSettings.keyBindJump.pressed;
	}
	
	public void draw() {
		int scale = 18;
		
		int index = 0;
		
		int textColor = HUD.themeColor.isEnabled() ? ColorUtil.getThemeColor(index) : -1;
		//wasd
		RenderUtil.drawRect(this.posX, this.posY, scale, scale, aPressed ? -1 : 0x70000000);
		RenderUtil.drawRect(this.posX + scale + 2, this.posY, scale, scale, sPressed ? -1 : 0x70000000);
		RenderUtil.drawRect(this.posX + (scale + 2) * 2, this.posY, scale, scale, dPressed ? -1 : 0x70000000);
		RenderUtil.drawRect(this.posX + scale + 2, this.posY - scale - 2, scale, scale, wPressed ? -1 : 0x70000000);
		
		//jump
		RenderUtil.drawRect(this.posX, this.posY + 20, scale * 3 + 4, 10, jump ? -1 : 0x70000000);
		RenderUtil.drawRect(this.posX + 6, this.posY + 25, scale * 3 - 8, 1, jump ? 0x90000000 : textColor);
		if(!jump) RenderUtil.drawRect(this.posX + 7, this.posY + 26, scale * 3 - 8, 1, 0x90000000);
		
		//attack / use
		RenderUtil.drawRect(this.posX, this.posY + 32, scale * 3 / 2 + 1, scale, attack ? -1 : 0x70000000);
		RenderUtil.drawRect(this.posX + scale * 3 / 2 + 3, this.posY + 32, scale * 3 / 2 + 1, scale, use ? -1 : 0x70000000);
		
		fr.drawString("A", this.posX + 6, this.posY + 6, aPressed ? 0x50000000 : textColor, !aPressed);
		fr.drawString("S", this.posX + 8 + scale, this.posY + 6, sPressed ? 0x50000000 : textColor, !sPressed);
		fr.drawString("D", this.posX + 10 + scale * 2, this.posY + 6, dPressed ? 0x50000000 : textColor, !dPressed);
		index++;
		fr.drawString("W", this.posX + 8 + scale, this.posY - scale + 4, wPressed ? 0x50000000 : textColor, !wPressed);
		index++;
		fr.drawString("LMB", this.posX + 5, this.posY + scale + 20, attack ? 0x50000000 : textColor, !attack);
		fr.drawString("RMB", this.posX + scale * 3 / 2 + 8, this.posY + scale + 20, use ? 0x50000000 : textColor, !use);
		index++;
	}
}
