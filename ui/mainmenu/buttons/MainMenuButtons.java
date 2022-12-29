package dev.rice.ui.mainmenu.buttons;

import dev.rice.utils.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public enum MainMenuButtons {
	SINGLE("Singleplayer", 1, 1),
	MULTI("Multiplayer", 2, 1),
	ALT("Altmanager", 15, 1),
	REALM("Minecraft Realms", 14, 1),
	OPTIONS("Options", 0, 2),
	QUIT("Quit", 4, 2),
	LANGUAGE(null, 5, 0);
	
	public String name;
	public int buttonId;
	public int mode;
	public int posX;
	public int posY;
	public int defPosX;
	public int defPosY;
	public int defMode;
	public int edgeRadius;
	public boolean mouseOver;
	public boolean pressed;
	
	MainMenuButtons(String name, int buttonId, int defMode) {
		this.name = name;
		this.buttonId = buttonId;
		this.mode = mode;
	}

	public void cycle() {
		if(mode == 1) {
			setMode(2);
		}else if(mode == 2){
			setMode(1);
		}
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public int getMode() {
		return mode;
	}
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}
