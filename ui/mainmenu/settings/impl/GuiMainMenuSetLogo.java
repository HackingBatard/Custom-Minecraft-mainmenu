package dev.rice.ui.mainmenu.settings.impl;

import java.io.IOException;
import java.net.Proxy;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import dev.rice.Rice;
import dev.rice.ui.mainmenu.settings.GuiMainMenuSettings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;


public class GuiMainMenuSetLogo extends GuiScreen {
    private final GuiMainMenuSettings prevScreen;
    private GuiTextField niggaTextField;

    public GuiMainMenuSetLogo(GuiMainMenuSettings prevScreen) {
        this.prevScreen = prevScreen;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
            	mc.gameSettings.logo = this.niggaTextField.getText();
                break;
            }
            case 1: {
            	mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
        }
    }

    @Override
    public void drawScreen(int i2, int j2, float f2) {
        this.drawDefaultBackground();
        this.niggaTextField.drawTextBox();
        this.drawCenteredString(this.fontRendererObj, "Editing Logo...", width / 2, 20, -1);
        if (this.niggaTextField.getText().isEmpty() && !this.niggaTextField.isFocused()) {
            this.drawString(this.mc.fontRendererObj, "RICE", width / 2 - 96, 66, -7829368);
        }
        this.drawCenteredString(this.fontRendererObj, "Change the logo in Mainmenu", width / 2, 30, -1);
        super.drawScreen(i2, j2, f2);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92 + 12, "Confirm"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 116 + 12, "Back"));
        this.niggaTextField = new GuiTextField(this.eventButton, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.niggaTextField.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && (this.niggaTextField.isFocused())) {
            this.niggaTextField.setFocused(!this.niggaTextField.isFocused());
        }
        if (par1 == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.niggaTextField.mouseClicked(par1, par2, par3);
    }
}

