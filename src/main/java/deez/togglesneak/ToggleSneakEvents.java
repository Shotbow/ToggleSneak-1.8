package deez.togglesneak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraftforge.client.event.GuiOpenEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import deez.togglesneak.gui.GuiOptionsReplace;

public class ToggleSneakEvents
{
	public static ToggleSneakEvents instance = new ToggleSneakEvents();

	protected static final Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void GuiOpenEvent(GuiOpenEvent event)
	{
		if(event.gui instanceof GuiOptions && mc.theWorld != null)
		{
			event.gui = new GuiOptionsReplace(new GuiIngameMenu(), mc.gameSettings);
		}
	}
}