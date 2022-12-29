package dev.rice.ui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import dev.rice.modules.Module;
import dev.rice.Rice;
import dev.rice.autosave.SaveLoad;
import dev.rice.events.impl.EventAlwaysInGui;
import dev.rice.modules.Category;
import dev.rice.modules.ModuleManager;
import dev.rice.settings.Setting;
import dev.rice.settings.impl.BooleanSetting;
import dev.rice.settings.impl.ModeSetting;
import dev.rice.settings.impl.NumberSetting;
import dev.rice.settings.impl.StringSetting;
import dev.rice.ui.hud.GuiHudConfig;
import dev.rice.utils.font.FontUtil;
import dev.rice.utils.render.ColorUtil;
import dev.rice.utils.render.RoundedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class ClickGui extends GuiScreen {
	
	private double offset;
	private int integer;
	
    private int oldPosX;
    private int oldPosY;
    
    int maxLength = Minecraft.getMinecraft().fontRendererObj.getStringWidth("Movement");
    
    private static class ClickGuiComparator implements Comparator<Module>{
    	
		@Override
		public int compare(Module o1, Module o2) {
			return o1.name.compareTo(o2.name);
		}
	}
    
    @Override
    public void actionPerformed(GuiButton button) throws IOException {
    	if(button.id == 1) {
    		this.mc.displayGuiScreen(new GuiHudConfig(this));
    	}
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
    	
    	FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    	Collections.sort(ModuleManager.modules,new ClickGuiComparator());
    	
        int count = 0;
        float scale = 0.5f;
        
        Category[] values;
        for (int length = (values = Category.values()).length, i = 0; i < length; ++i) {
            final Category category = values[i];
            if (category.clicked) {
                final Category category2 = category;
                category2.posX += mouseX - this.oldPosX;
                final Category category3 = category;
                category3.posY += mouseY - this.oldPosY;
            }
            Color color = new Color(12, 80, 125);
            Gui.drawRect(category.posX - 16, category.posY - 3, category.posX + maxLength + 16, category.posY + fr.FONT_HEIGHT + 3, ColorUtil.getThemeColor(count));
            
            fr.drawStringWithShadow(category.name, category.posX - 12, category.posY, -1);
            
            count++;
            final int posX = category.posX;
            int posY = category.posY + 15;
            
            scale = 0.68f;
            int index = 0;
            for (final Module m : ModuleManager.modules) {
                if (m.getCategory() == category && category.showModules) {
                    Gui.drawRect(posX - 16, posY - 3, posX + 16 + maxLength, posY + fr.FONT_HEIGHT + 1, m.toggled ? ColorUtil.getThemeColor(count) : 0xff36393e);
                    Gui.drawRect(posX - 16, posY - 3, posX + 16 + maxLength, posY + fr.FONT_HEIGHT + 1, 0x20000000);
                   
                    GlStateManager.translate(posX - 10, category.posY + 15, 1);
                    GlStateManager.scale(scale, scale, 0);
                    GlStateManager.translate(-(posX - 10), -category.posY - 15, -1);
                    FontUtil.riceFont28.drawSmoothString(m.getName(), posX - 10, posY + index, -1);
                    GlStateManager.translate(posX - 10, category.posY + 15, 1);
                    GlStateManager.scale(1 / scale, 1 / scale, 0);
                    GlStateManager.translate(-(posX - 10), -category.posY - 15, -1);
                    if(!m.settings.isEmpty()) {
                        fr.drawString(m.expanded ? "-" : "+", posX + maxLength + 9, posY, -1);
                    }
                    
                    if (mouseX >= posX - 16 && mouseX <= posX + maxLength + 16 && mouseY >= posY - 2 && mouseY <= posY + 9) {
		            	Gui.drawRect(posX - 16, posY - 3, posX + 16 + maxLength, posY + fr.FONT_HEIGHT + 1, 0x40000000);
		            	if (mouseX >= posX + 16 && mouseX <= posX + maxLength + 16 && mouseY >= posY - 2 && mouseY <= posY + 9) {
		                	RoundedUtil.drawRect(category.posX + maxLength + 18, mouseY - 1, fr.getStringWidth(m.info) + 3, 1 + fr.FONT_HEIGHT, 4, new Color(54, 57, 62));
			            	fr.drawString(m.info, category.posX + maxLength + 20, mouseY, -1);
	                	}else if (mouseX >= posX - 16 && mouseX < posX + 16 && mouseY >= posY - 2 && mouseY <= posY + 9) {
		                	RoundedUtil.drawRect(category.posX - (fr.getStringWidth(m.info) + 3) - 18, mouseY - 1, fr.getStringWidth(m.info) + 3, 1 + fr.FONT_HEIGHT, 4, new Color(54, 57, 62));
			            	fr.drawString(m.info, category.posX - (fr.getStringWidth(m.info) + 18), mouseY, -1);
	                	}
		            }
                    posY += 12;
                    index+= 5;
                }
                
                if (m.getCategory() == category && category.showModules && m.expanded) {
                    for (final Setting set : m.settings) {
                    	if(set.hidden)
                    		continue;
                		if (set instanceof ModeSetting) {
                            final ModeSetting mode = (ModeSetting)set;
                            Gui.drawRect(posX - 16, posY - 3, posX + 16 + maxLength, posY + fr.FONT_HEIGHT + 1, 0xff282b30);
                            FontUtil.riceFont16.drawString(String.valueOf(set.name) + ": " + mode.getMode(), posX - 10, posY - 1, -1);
                        }
                        if (set instanceof NumberSetting) {
                            final NumberSetting number = (NumberSetting)set;
                            set.sliderWidth = (int) (posX - 15 + (75 * number.getValue() / number.getMaximum()));
                            Gui.drawRect(posX - 16, posY - 3, posX + 16 + maxLength, posY + fr.FONT_HEIGHT + 3, 0xff282b30);
                            Gui.drawRect(posX - 14, posY + 8, (int) set.sliderWidth, posY + 1 + fr.FONT_HEIGHT, -1);
                            FontUtil.riceFont16.drawString(String.valueOf(set.name) + ": " + number.getValue(), posX - 10, posY - 1, -1);
                            
                            if(Math.abs((int) ((mouseX - set.sliderWidth) / (number.increment))) == 0) {
                            	integer = 1;
                            }else {
                                integer = Math.abs((int) ((mouseX - set.sliderWidth) / (number.increment)));
                            }
                            
                            if(set.sliding) {
                            	if(number.increment > 1 || set.ass) {
                            		if(mouseX > set.sliderWidth) {
                                		for(double o = set.sliderWidth; o < mouseX - (integer * number.increment); o++) {
                                    		number.increment(true);
                                    	}
                                	}
                                	if(mouseX < set.sliderWidth) {
                                		for(double o = set.sliderWidth; o > mouseX + (integer * number.increment); o--) {
                                    		number.increment(false);
                                		}
                                	}
                            	}else {
                            		if(mouseX > set.sliderWidth) {
                                		for(double o = set.sliderWidth; o < mouseX; o++) {
                                    		number.increment(true);
                                    	}
                                	}
                                	if(mouseX < set.sliderWidth) {
                                		for(double o = set.sliderWidth; o > mouseX; o--) {
                                    		number.increment(false);
	                                    }
	                            	}
	                            }
                            }
                            posY += 3;
                            index+= 3;
                        }
                        if (set instanceof BooleanSetting) {
                            final BooleanSetting bool = (BooleanSetting)set;
                            Gui.drawRect(posX - 16, posY - 3, posX + 16 + maxLength, posY + fr.FONT_HEIGHT + 1, 0xff282b30);
                            FontUtil.riceFont16.drawString(String.valueOf(set.name), posX - 10, posY - 1, -1);
                            FontUtil.riceFont16.drawString(bool.isEnabled() ? "O" : "X", posX + maxLength + 8, posY - 1, -1);
                        }
                        if(set instanceof StringSetting) {
                        	final StringSetting string = (StringSetting) set;
                        	
                    		Gui.drawRect(posX - 16, posY - 3, posX + 16 + maxLength, posY + 2*(fr.FONT_HEIGHT + 1), 0xff282b30);
                    		FontUtil.riceFont16.drawString(set.name + ":", posX - 10, posY - 1, -1);
                    		FontUtil.riceFont16.drawString(string.messages, posX - 10, posY + fr.FONT_HEIGHT, -1);
                        	posY += 12;
                        	index += 5;
                        }
                        posY += 12;
                        index += 5;
                    }
                }
            }
        }
        
        this.oldPosX = mouseX;
        this.oldPosY = mouseY;
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        final FontRenderer fr = this.mc.fontRendererObj;
        Category[] values;
        for (int length = (values = Category.values()).length, j = 0; j < length; ++j) {
            final Category category = values[j];
            if (mouseX >= category.posX - 16 && mouseX <= category.posX + maxLength + 16 && mouseY >= category.posY && mouseY <= category.posY + 10) {
                if (mouseButton == 0) {
                    category.clicked = true;
                }
                if (mouseButton == 1) {
                    if (category.showModules) {
                        category.showModules = false;
                    }
                    else {
                        category.showModules = true;
                    }
                }
            }
            final int posX = category.posX;
            int posY = category.posY + 15;
            for (final Module m : ModuleManager.modules) { 
                if (m.getCategory() == category && category.showModules) {
                    if (mouseX >= posX - 16 && mouseX <= posX + maxLength + 16 && mouseY >= posY - 2 && mouseY <= posY + 9) {
                        if (mouseButton == 0) {
                            m.toggle();
                        }
                        if (mouseButton == 1) {
                        	if(!m.settings.isEmpty()) {
                        		if (m.expanded) {
                                    m.expanded = false;
                                }
                                else {
                                    m.expanded = true;
                                }
                        	}
                        }
                    }
                    posY += 12;
                }
                if (m.getCategory() == category && m.expanded && category.showModules) {
                    for (final Setting set : m.settings) {
                    	if(set.hidden)
                    		continue;
                		if (mouseX >= posX - 16 && mouseX <= posX + maxLength + 16  && mouseY >= posY - 2 && mouseY <= posY + 9) {
                            if (mouseButton == 0) {
                                if (set instanceof ModeSetting) {
                                    final ModeSetting mode = (ModeSetting)set;
                                    ((ModeSetting)set).cycle();
                                }
                                if (set instanceof BooleanSetting) {
                                    final BooleanSetting bool = (BooleanSetting)set;
                                    ((BooleanSetting)set).toggle();
                                }
                                if (set instanceof NumberSetting) {
                                    final NumberSetting number = (NumberSetting)set;
                                    set.sliding = true;
                                }
                            }
                            if (mouseButton == 1 ) {
                            	if (set instanceof ModeSetting) {
                                    final ModeSetting mode = (ModeSetting)set;
                                    ((ModeSetting)set).cycle1();
                                }
                            }
                        }
                        if(set instanceof NumberSetting) {
                        	posY += 3;
                        }
                        if(set instanceof StringSetting) {
                        	posY += 12;
                        }
                        posY += 12;
                    }
                }
            }
        }
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
        	Rice.saveload.save();
            this.mc.displayGuiScreen(null);
            ModuleManager.clickGUI.setToggle(false);
            Category[] values;
            for (int length = (values = Category.values()).length, i = 0; i < length; ++i) {
                final Category category = values[i];
                category.clicked = false;
            }
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        Category[] values;
        for (int length = (values = Category.values()).length, i = 0; i < length; ++i) {
            final Category category = values[i];
            category.clicked = false;
            for(Module m : ModuleManager.modules) {
            	for(Setting set : m.settings) {
            		set.sliding = false;
            	}
            }
        }
        
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
