package dev.rice.ui.mainmenu.settings;

import dev.rice.ui.mainmenu.CustomMainMenu;
import dev.rice.ui.mainmenu.buttons.FakeCustomMainMenu;
import dev.rice.ui.mainmenu.settings.impl.GuiMainMenuSetLogo;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import optifine.GuiOptionButtonOF;
import optifine.GuiOptionSliderOF;
import optifine.TooltipManager;

public class GuiMainMenuSettings extends GuiScreen {
	
	public GuiButton logo;
	public GuiButton pos;
	
	public String title = "Main Menu";
	public String status = "";
	private GuiScreen prevScreen;
	private static GameSettings.Options[] mainMenuOptions = new GameSettings.Options[] {GameSettings.Options.MAIN_MENU};
	private GameSettings settings;
	private TooltipManager tooltipManager = new TooltipManager(this);
	
	public GuiMainMenuSettings(GuiScreen prevScreen, GameSettings gameSettings) {
		this.prevScreen = prevScreen;
		this.settings = gameSettings;
	}
	
	public void initGui() {
		this.buttonList.clear();

        for (int i = 0; i < mainMenuOptions.length; ++i)
        {
            GameSettings.Options gamesettings$options = mainMenuOptions[i];
            int j = this.width / 2 - 155 + i % 2 * 160;
            int k = this.height / 6 + 21 * (i / 2) - 12;

            if (!gamesettings$options.getEnumFloat())
            {
                this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
            }
            else
            {
                this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
            }
        }
        
        int i = mainMenuOptions.length;
        int j = this.width / 2 - 155 + i % 2 * 160;
        int k = this.height / 6 + 21 * (i / 2) - 12;
        int widthIn = 150, heightIn = 20;
        this.logo = new GuiButton(300, j, k, widthIn, heightIn, "Edit Logo");
        this.buttonList.add(this.logo);
        i++;
        j = this.width / 2 - 155 + i % 2 * 160;
        k = this.height / 6 + 21 * (i / 2) - 12;
        this.pos = new GuiButton(301, j, k, widthIn, heightIn, "Button Positions");
        this.buttonList.add(this.pos);
        i++;
        j = this.width / 2 - 155 + i % 2 * 160;
        k = this.height / 6 + 21 * (i / 2) - 12;
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
	}
	
	protected void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
            if (button.id < 200 && button instanceof GuiOptionButton)
            {
                this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
                button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
            }

            if (button.id == 200)
            {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.prevScreen);
            }
            if(button.id == 300) {
            	this.mc.gameSettings.saveOptions();
            	this.mc.displayGuiScreen(new GuiMainMenuSetLogo(this));
            }
            if(button.id == 301) {
            	this.mc.gameSettings.saveOptions();
            	this.mc.displayGuiScreen(new FakeCustomMainMenu(this));
            }
        }
    }
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		FontRenderer fr = this.mc.fontRendererObj;
		
		this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
        fr.drawString(status, 3, this.height - fr.FONT_HEIGHT - 1, -1);
        
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
        
        boolean flag = (mc.gameSettings.mainMenuSetting == 1) ? true : false;
        
        this.logo.enabled = flag;
        this.pos.enabled = flag;
	}
}
