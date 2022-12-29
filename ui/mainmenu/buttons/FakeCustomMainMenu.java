package dev.rice.ui.mainmenu.buttons;

import java.awt.Color;
import java.io.IOException;
import java.net.Proxy;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import dev.rice.Rice;
import dev.rice.modules.Category;
import dev.rice.modules.Module;
import dev.rice.modules.ModuleManager;
import dev.rice.settings.Setting;
import dev.rice.ui.mainmenu.settings.GuiMainMenuSettings;
import dev.rice.utils.font.FontUtil;
import dev.rice.utils.render.RenderUtil;
import dev.rice.utils.render.RoundedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonLanguage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class FakeCustomMainMenu extends GuiScreen {
    private final GuiMainMenuSettings prevScreen;
    private String status = "";
    private int selectedButton;
    //max 657
    private int sliderWidth = 657;
    
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    
    private int oldPosX;
    private int oldPosY;
    
    public FakeCustomMainMenu(GuiMainMenuSettings prevScreen) {
        this.prevScreen = prevScreen;
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 69: {
                Rice.instance.saveload.saveClientOptions();
            	setStatus(this, "Saved!");
                break;
            }
            case 70: {
                this.mc.displayGuiScreen(this.prevScreen);
                break;
            }
            case 71: {
            	for(MainMenuButtons buttons : MainMenuButtons.values()) {
            		status = "You have not saved options!";
            		buttons.posX = buttons.defPosX;
            		buttons.posY = buttons.defPosY;
            		buttons.mode = buttons.defMode;
            	}
            }
        }
    }

    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
    	for(MainMenuButtons button : MainMenuButtons.values()) {
    		if(selectedButton == button.buttonId) {
    			switch(keyCode) {
    				case Keyboard.KEY_UP:
    					if(button.posY > 0) {
    						button.posY--;
    					}
    					break;
    				case Keyboard.KEY_DOWN:
    					if(button.posY < this.height) {
    						button.posY++;
    					}
    					break;
    				case Keyboard.KEY_LEFT:
    					if(button.posX > 0) {
    						button.posX--;
    					}
    					break;
    				case Keyboard.KEY_RIGHT:
    					if(button.posX < this.width) {
    						button.posX++;
    					}
    					break;
    			}
    		}
    	}
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	FontRenderer fr = mc.fontRendererObj;
		ScaledResolution sr = new ScaledResolution(this.mc);
    	
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Editing Buttons Positions...", width / 2, 20, -1);
        
        RoundedUtil.drawRect(this.width / 2 + 200, 0, this.width - (this.width / 2 + 200), this.height, 10, new Color(0, 0, 0, 0.5f));
        
        for(MainMenuButtons button : MainMenuButtons.values()) {
        	if(selectedButton == button.buttonId) {
        		sliderWidth = 197 * (button.edgeRadius / 20);
        		button.mouseOver = true;
        		String mode = button.getMode() == 1 ? "Large" : "Small";
        		int width = this.width / 2 + 220;
                fr.drawStringWithShadow(button.name, width, 50, -1);
                fr.drawStringWithShadow("PosX: " + button.posX, width, 64, -1);
                fr.drawStringWithShadow("PosY: " + button.posY, width, 78, -1);
                fr.drawStringWithShadow("Mode: " + mode, width, 92, -1);
                fr.drawStringWithShadow("EdgeRadius: " + button.edgeRadius, width, 106, -1);
        	}else {
            	button.mouseOver = false;
        	}
        	if(button.pressed) {
        		button.posX += mouseX - oldPosX;
        		button.posY += mouseY - oldPosY;
        	}
        	if(button.name == null)
				continue;
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
        }
        FontUtil.riceboldFont20.drawStringWithShadow("Options", this.width / 2 + (this.width / 4 + 125) - fr.getStringWidth("Options"), fr.FONT_HEIGHT, -1);
        
        fr.drawString(status, 3, sr.getScaledHeight() - fr.FONT_HEIGHT - 1, -1);
        
    	this.oldPosX = mouseX;
        this.oldPosY = mouseY;
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private void setStatus(FakeCustomMainMenu menu, String status) {
    	menu.status = status;
    }
    
    @Override
    public void initGui() {
    	FontRenderer fr = this.mc.fontRendererObj;
    	ScaledResolution sr = new ScaledResolution(this.mc);
    	this.buttonList.add(new GuiButton(71, this.width / 2 - 150, sr.getScaledHeight() - fr.FONT_HEIGHT - 15, 98, 20, "Reset"));
    	this.buttonList.add(new GuiButton(70, this.width / 2 - 50, sr.getScaledHeight() - fr.FONT_HEIGHT - 15, 98, 20, "Back"));
    	this.buttonList.add(new GuiButton(69, this.width / 2 + 50, sr.getScaledHeight() - fr.FONT_HEIGHT - 15, 98, 20, "Confirm"));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	FontRenderer fr = mc.fontRendererObj;
    	for(MainMenuButtons button : MainMenuButtons.values()) {
    		int posX = button.posX + (this.width - 683) / 2;
			int posY = button.posY + (this.height - 358) / 2;
    		if(mouseX >= posX && (button.mode == 1 ? mouseX <= posX + 200 : mouseX <= posX + 98) && mouseY >= posY && mouseY <= posY + 20) {
    			if(mouseButton == 0) {
    				button.pressed = true;
    				selectedButton = button.buttonId;
    			}
    			if(mouseButton == 1) {
    				selectedButton = button.buttonId;
    			}
    		}
    		if(mouseX >= this.width / 2 + 220 && mouseX <= this.width && mouseY >= 90 && mouseY <= 92 + fr.FONT_HEIGHT) {
    			if(selectedButton == button.buttonId && mouseButton == 0) {
    				button.cycle();
    			}
    		}
    		if(mouseX >= this.width / 2 + 223 && mouseX <= this.width / 2 + 420 && mouseY >= 120 && mouseY <= 125) {
    			if(selectedButton == button.buttonId && mouseButton == 0) {
    				button.edgeRadius += (mouseX - (this.width / 2 + 223)) / 197 * 20;
    			}
    		}
    	}
    	//staff only
    	//status = String.valueOf(mouseX);
		super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
    	for(MainMenuButtons buttons : MainMenuButtons.values()) {
    		buttons.pressed = false;
    	}
    }
}

