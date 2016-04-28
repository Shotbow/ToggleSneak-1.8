package deez.togglesneak.gui;

import deez.togglesneak.RenderTextToHUD;
import deez.togglesneak.ToggleSneakMod;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiTSConfig extends GuiScreen {
	private GuiScreen parentScreen;
	private GuiButton btnToggleSneak;
	private GuiButton btnToggleSprint;
	private GuiButton btnShowHUDText;
	private GuiButton btnDoubleTap;
	private GuiButton btnFlyBoost;
	private GuiButton btnOptionsButton;
	private GuiButton btnSaveSettings;
	private GuiButton btnCancelChanges;

	private GuiSlideControl sliderHUDTextPosX;
	private GuiSlideControl sliderHUDTextPosY;
	private GuiSlideControl sliderFlyBoostAmount;

	private GuiButton lastPressed;

	private boolean changedShowHUD;
	private boolean changedToggleSprint;

	private int headerPos;
	private int footerPos;

	private byte byte0 = -16;

	public GuiTSConfig(GuiScreen parent) {
		this.parentScreen = parent;
	}

	@Override
	public void initGui() {
		this.buttonList.clear();

		headerPos = this.height / 4 - 52;
		footerPos = this.height - 29;

		this.btnToggleSneak = new GuiButton(1, this.width / 2 - 98, getRowPos(1), 60, 20, String.valueOf(ToggleSneakMod.optionToggleSneak));
		this.btnToggleSprint = new GuiButton(2, this.width / 2 + 102, getRowPos(1), 60, 20, String.valueOf(ToggleSneakMod.optionToggleSprint));
		this.btnShowHUDText = new GuiButton(3, this.width / 2 + 2, getRowPos(2), 60, 20, String.valueOf(ToggleSneakMod.optionShowHUDText));

		this.sliderHUDTextPosX = new GuiSlideControl(50, this.width / 2 + 2, getRowPos(3), 150, 20, "X Pos: ", 1, width - 25, ToggleSneakMod.optionHUDTextPosX, true);
		this.sliderHUDTextPosY = new GuiSlideControl(60, this.width / 2 + 2, getRowPos(4), 150, 20, "Y Pos: ", 1, height - 8, ToggleSneakMod.optionHUDTextPosY, true);

		this.btnDoubleTap = new GuiButton(4, this.width / 2 + 2, getRowPos(5), 60, 20, String.valueOf(ToggleSneakMod.optionDoubleTap));
		this.btnFlyBoost = new GuiButton(5, this.width / 2 - 113, getRowPos(6), 60, 20, String.valueOf(ToggleSneakMod.optionEnableFlyBoost));

		this.sliderFlyBoostAmount = new GuiSlideControl(70, this.width / 2 + 57, getRowPos(6), 150, 20, "x", 0.0F, 10.0F, (float) ToggleSneakMod.optionFlyBoostAmount, false);

		this.btnOptionsButton = new GuiButton(6, this.width / 2 + 2, getRowPos(7), 60, 20, ToggleSneakMod.optionButtonPosition == 1 ? "Left" : "Right");

		this.btnSaveSettings = new GuiButton(100, this.width / 2 - 155, footerPos, 150, 20, "Save Settings");
		this.btnCancelChanges = new GuiButton(110, this.width / 2 + 5, footerPos, 150, 20, "Cancel Changes");

		this.buttonList.add(btnToggleSneak);
		this.buttonList.add(btnToggleSprint);
		this.buttonList.add(btnShowHUDText);

		this.buttonList.add(sliderHUDTextPosX);
		this.buttonList.add(sliderHUDTextPosY);

		this.buttonList.add(btnDoubleTap);
		this.buttonList.add(btnFlyBoost);
		this.buttonList.add(sliderFlyBoostAmount);

		this.buttonList.add(btnOptionsButton);

		this.buttonList.add(btnSaveSettings);
		this.buttonList.add(btnCancelChanges);
	}

	public int getRowPos(int rowNumber) {
		return this.height / 4 + ((24 * rowNumber) - 24) + byte0;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			for (int l = 0; l < this.buttonList.size(); ++l) {
				GuiButton guibutton = this.buttonList.get(l);

				if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
					lastPressed = guibutton;
					actionPerformed(guibutton);
				}
			}
		}
	}

	// Update values when sliding the SlideControl
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int which) {
		if (this.lastPressed != null && which == 0) {
			lastPressed.mouseReleased(mouseX, mouseY);

			actionPerformed_MouseUp(lastPressed);

			lastPressed = null;
		}
	}

	// Update values if the mouse was clicked and dragged
	protected void actionPerformed_MouseUp(GuiButton button) {
		if (button instanceof GuiSlideControl) {
			actionPerformed(button);
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			// btnToggleSneak
			case 1:
				ToggleSneakMod.optionToggleSneak = !ToggleSneakMod.optionToggleSneak;
				this.btnToggleSneak.displayString = String.valueOf(ToggleSneakMod.optionToggleSneak);
				break;

			// btnToggleSprint
			case 2:
				ToggleSneakMod.optionToggleSprint = !ToggleSneakMod.optionToggleSprint;
				this.btnToggleSprint.displayString = String.valueOf(ToggleSneakMod.optionToggleSprint);
				changedToggleSprint = true;
				break;

			// btnShowHUDText
			case 3:
				ToggleSneakMod.optionShowHUDText = !ToggleSneakMod.optionShowHUDText;
				this.btnShowHUDText.displayString = String.valueOf(ToggleSneakMod.optionShowHUDText);
				changedShowHUD = true;
				break;

			// btnDoubleTap
			case 4:
				ToggleSneakMod.optionDoubleTap = !ToggleSneakMod.optionDoubleTap;
				this.btnDoubleTap.displayString = String.valueOf(ToggleSneakMod.optionDoubleTap);
				break;

			// btnFlyBoost
			case 5:
				ToggleSneakMod.optionEnableFlyBoost = !ToggleSneakMod.optionEnableFlyBoost;
				this.btnFlyBoost.displayString = String.valueOf(ToggleSneakMod.optionEnableFlyBoost);
				break;

			//btnOptionsButton
			case 6:
				if (ToggleSneakMod.optionButtonPosition == 0) ToggleSneakMod.optionButtonPosition = 1;
				else if (ToggleSneakMod.optionButtonPosition == 1) ToggleSneakMod.optionButtonPosition = 0;

				this.btnOptionsButton.displayString = ToggleSneakMod.optionButtonPosition == 1 ? "Left" : "Right";
				break;

			// sliderHUDTextPosX
			case 50:
				ToggleSneakMod.optionHUDTextPosX = sliderHUDTextPosX.GetValueAsInt();
				break;

			// sliderHUDTextPosY
			case 60:
				ToggleSneakMod.optionHUDTextPosY = sliderHUDTextPosY.GetValueAsInt();
				break;

			// sliderFlyBoostAmount
			case 70:
				ToggleSneakMod.optionFlyBoostAmount = sliderFlyBoostAmount.GetValueAsFloat();
				break;

			// btnSaveSettings
			case 100:
				// Clear the HUD Text if HUD display was disabled in-game
				if (changedShowHUD) RenderTextToHUD.SetHUDText("");

				// Cancel ToggleSprint if active and update sprint status if disabled in-game
				if (changedToggleSprint && mc.theWorld != null) ToggleSneakMod.wasSprintDisabled = true;

				ToggleSneakMod.saveConfig();

				changedShowHUD = false;
				changedToggleSprint = false;

				this.mc.displayGuiScreen(this.parentScreen);
				break;

			// btnCancelChanges
			case 110:
				// Reload config from file to erase any changes made to values in memory
				ToggleSneakMod.reloadConfig();
				this.mc.displayGuiScreen(this.parentScreen);

				changedShowHUD = false;
				changedToggleSprint = false;
				break;
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		String lblToggleSneak = "Enable ToggleSneak";
		String lblToggleSprint = "Enable ToggleSprint";
		String lblShowHUDText = "Show status on HUD";
		String lblHUDTextPosX = "Horizontal HUD Location";
		String lblHUDTextPosY = "Vertical HUD Location";
		String lblDoubleTap = "Enable Double-Tapping";
		String lblFlyBoost = "Enable Fly Boost";
		String lblFlyBoostAmount = "Fly Boost Multiplier";
		String lblOptionsButton = "Options Button Location";

		this.drawDefaultBackground();

		this.drawCenteredString(this.fontRendererObj, "ToggleSneak Settings", this.width / 2, headerPos, 16777215);

		this.drawString(fontRendererObj, lblToggleSneak, this.width / 2 - 100 - this.fontRendererObj.getStringWidth(lblToggleSneak), getRowPos(1) + 6, 16777215);
		this.drawString(fontRendererObj, lblToggleSprint, this.width / 2 + 100 - this.fontRendererObj.getStringWidth(lblToggleSprint), getRowPos(1) + 6, 16777215);
		this.drawString(fontRendererObj, lblShowHUDText, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(lblShowHUDText), getRowPos(2) + 6, 16777215);

		this.drawString(fontRendererObj, lblHUDTextPosX, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(lblHUDTextPosX), getRowPos(3) + 6, 16777215);
		this.drawString(fontRendererObj, lblHUDTextPosY, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(lblHUDTextPosY), getRowPos(4) + 6, 16777215);

		this.drawString(fontRendererObj, lblDoubleTap, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(lblDoubleTap), getRowPos(5) + 6, 16777215);
		this.drawString(fontRendererObj, lblFlyBoost, this.width / 2 - 115 - this.fontRendererObj.getStringWidth(lblFlyBoost), getRowPos(6) + 6, 16777215);
		this.drawString(fontRendererObj, lblFlyBoostAmount, this.width / 2 + 50 - this.fontRendererObj.getStringWidth(lblFlyBoostAmount), getRowPos(6) + 6, 16777215);

		this.drawString(fontRendererObj, lblOptionsButton, this.width / 2 - 3 - this.fontRendererObj.getStringWidth(lblOptionsButton), getRowPos(7) + 6, 16777215);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}