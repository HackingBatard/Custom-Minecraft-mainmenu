package dev.rice.ui.mainmenu;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Mouse;

import dev.rice.ui.mainmenu.buttons.MainMenuButtons;
import dev.rice.utils.alt.GuiAltManager;
import dev.rice.utils.font.FontUtil;
import dev.rice.utils.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;

public class CustomMainMenu extends GuiScreen implements GuiYesNoCallback{
	
	public static String file;
	private GuiButton realmsButton;
	
	boolean focus = false;
	
	//default setting
	public CustomMainMenu() {
		file = "default";
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		FontRenderer fr = mc.fontRendererObj;
		ScaledResolution sr = new ScaledResolution(this.mc);
		
		GlStateManager.disableAlpha();
		this.drawDefaultBackground();
		GlStateManager.enableAlpha();
		
		this.drawLogo(mc.gameSettings.logo, 60 + (this.height - 358) / 2, 1.9f);
        
		for(MainMenuButtons button : MainMenuButtons.values()) {
			if(button.name == null)
				continue;
			//make sure the buttons are at the center of the screen all the time, so we use (posX + (current width - normal width) / 2) as the button position
			int posX = button.posX + (this.width - 683) / 2;
			int posY = button.posY + (this.height - 358) / 2;
			switch(button.mode) {
				case 1:
					RenderUtil.drawButton(posX, posY, 200, 20, button.name, new Color(30,33,36), button.edgeRadius, true, button.mouseOver);
					break;
				case 2:
					RenderUtil.drawButton(posX, posY, 98, 20, button.name, new Color(30,33,36), button.edgeRadius, true, button.mouseOver);
					break;
			}
			if(mouseX >= posX && (button.mode == 1 ? mouseX <= posX + 200 : mouseX <= posX + 98) && mouseY >= posY && mouseY <= posY + 20) {
				button.mouseOver = true;
			}else {
				button.mouseOver = false;
			}
		}
		
		//debuger
		//fr.drawString(String.valueOf(this.height), 3, sr.getScaledHeight() - fr.FONT_HEIGHT - 1, -1);
		
        if(mc.gameSettings.mainMenuSetting != 1) {
        	fr.drawString("You need to restart the game to change the Mainmenu", 3, sr.getScaledHeight() - fr.FONT_HEIGHT - 1, -1);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
    	
    	super.actionPerformed(button);
    }
	
	private void drawLogo(String title, int titleHeight, float scale) {
		GlStateManager.translate(this.width / 2, titleHeight, 0);
        GlStateManager.scale(scale, scale, 1);
        GlStateManager.translate(-(this.width / 2), -titleHeight, 0);
        if(title == null || title.equalsIgnoreCase("rice") || title.equalsIgnoreCase("null")) {
        	FontUtil.riceFont100.drawSmoothString("RICE", this.width / 2 - (FontUtil.riceFont100.getStringWidth("RICE") / 2) + 1, titleHeight + 1, Color.gray.getRGB());
            FontUtil.riceFont100.drawSmoothString("\2473" + "R" + "\247f" + "ICE", this.width / 2 - (FontUtil.riceFont100.getStringWidth("RICE") / 2), titleHeight, -1);
        }else {
        	FontUtil.riceFont100.drawSmoothString(title, this.width / 2 - (FontUtil.riceFont100.getStringWidth(title) / 2) + 1, titleHeight + 1, Color.gray.getRGB());
            FontUtil.riceFont100.drawSmoothString(title, this.width / 2 - (FontUtil.riceFont100.getStringWidth(title) / 2), titleHeight, -1);
        }
        GlStateManager.translate(this.width / 2, titleHeight, 0);
        GlStateManager.scale((1 / scale), (1 / scale), 1);
        GlStateManager.translate(-(this.width / 2), -titleHeight, 0);
	}
	
	private void switchToRealms() {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }
	
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for(MainMenuButtons button : MainMenuButtons.values()) {
			int posX = button.posX + (this.width - 683) / 2;
			int posY = button.posY + (this.height - 358) / 2;
			if(mouseX >= posX && (button.mode == 1 ? mouseX <= posX + 200 : mouseX <= posX + 98) && mouseY >= posY && mouseY <= posY + 20) {
				if(mouseButton == 0) {
					switch(button.buttonId) {
		    		case 0:
		    			mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		    			break;
		    		case 1:
		    			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		    			break;
		    		case 2:
		    			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		    			break;
		    		case 4:
		    			this.mc.shutdown();
		    			break;
		    		case 5:
		    			this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
		    			break;
		    		case 11:
		    			this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
		    			break;
		    		case 12:
		    			ISaveFormat isaveformat = this.mc.getSaveLoader();
		                WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

		                if (worldinfo != null)
		                {
		                    GuiYesNo guiyesno = GuiSelectWorld.func_152129_a(this, worldinfo.getWorldName(), 12);
		                    this.mc.displayGuiScreen(guiyesno);
		                }
		                break;
		    		case 14:
		    			this.switchToRealms();
		    			break;
		    		case 15:
		    			this.mc.displayGuiScreen(new GuiAltManager());
		    			break;
		    		
					}
				}
			}
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	public void onGuiClosed() {
		
	}
}
