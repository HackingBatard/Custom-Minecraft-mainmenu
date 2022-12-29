package dev.rice.ui.hud;

import dev.rice.Rice;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class GuiHudConfig extends GuiScreen {
	
	private int oldPosX;
    private int oldPosY;
    private GuiScreen prevScreen;
    
    public GuiHudConfig(GuiScreen prevScreen) {
    	this.prevScreen = prevScreen;
    }
    
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		FontRenderer fr = mc.fontRendererObj;
		Gui.drawRect(0, 0, this.width, this.height, 0x60000000);
		
		for(Hud hud : HudManager.hud) {
			if(hud.isDragging()) {
				hud.posX += mouseX - this.oldPosX;
				hud.posY += mouseY - this.oldPosY;
			}else {
				Rice.saveload.saveClientOptions();
			}
			
			if(!hud.equals(Rice.instance.hudManager.getHud("HudKeyStroke"))) {
				if(hud.isRect()) {
					Gui.drawRect(hud.getPosX() - 2, hud.getPosY() - 2, hud.posX + fr.getStringWidth(hud.getName()) + 2, hud.posY + fr.FONT_HEIGHT + 1, 0x90000000);
				}
				fr.drawStringWithShadow(hud.getName(), hud.getPosX(), hud.getPosY(), -1);
			}else {
				hud.draw();
			}
		}
		this.oldPosX = mouseX;
		this.oldPosY = mouseY;
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		FontRenderer fr = mc.fontRendererObj;
		for(Hud hud : HudManager.hud) {
			if(!hud.equals(Rice.instance.hudManager.getHud("HudKeyStroke"))) {
				if(mouseX >= hud.posX - 2 && mouseX <= hud.posX + fr.getStringWidth(hud.getName()) + 2 && mouseY >= hud.posY - 2 && mouseY <= hud.posY + fr.FONT_HEIGHT + 1) {
					if(mouseButton == 0) {
						hud.setDragging(true);
					}
				}
			}else {
				if(mouseX >= hud.posX && mouseX <= hud.posX + 58 && mouseY >= hud.posY - 20 && mouseY <= hud.posY + 38) {
					if(mouseButton == 0) {
						hud.setDragging(true);
					}
				}
			}
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		FontRenderer fr = mc.fontRendererObj;
		for(Hud hud : HudManager.hud) {
			hud.setDragging(false);
		}
	}
	
	public void onGuiClosed() {
		FontRenderer fr = mc.fontRendererObj;
		for(Hud hud : HudManager.hud) {
			hud.setDragging(false);
		}
	}
}
