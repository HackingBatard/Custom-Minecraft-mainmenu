package dev.rice.ui.hud;

import dev.rice.events.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class Hud {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	public static FontRenderer fr = mc.fontRendererObj;
	public static ScaledResolution sr = new ScaledResolution(mc);
	
	public String name;
	public String displayName;
	public int posX, posY;
	public boolean dragging;
	public boolean rect;
	public boolean visible;
	public String getName() {
		return name;
	}
	
	public Hud(String name, int posX, int posY, boolean rect, boolean visible) {
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		this.rect = rect;
		this.visible = visible;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public boolean isDragging() {
		return dragging;
	}
	
	public boolean isRect() {
		return rect;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setDisplayName(String name) {
		this.displayName = name;
	}
	
	public void onEvent(Event e) {
		
	}
	
	public void onUpdate() {
		
	}
	
	public void onUpdateAlways() {
		
	}
	
	public void draw() {
		
	}
}
