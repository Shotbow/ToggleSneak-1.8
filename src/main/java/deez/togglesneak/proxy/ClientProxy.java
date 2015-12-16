package deez.togglesneak.proxy;

import api.player.client.ClientPlayerAPI;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import deez.togglesneak.PlayerBase;
import deez.togglesneak.RenderTextToHUD;
import deez.togglesneak.ToggleSneakMod;

public class ClientProxy extends CommonProxy
{
	public void registerEvents(FMLPreInitializationEvent event)
	{
		ToggleSneakMod.loadConfig(event.getSuggestedConfigurationFile());
		if (ToggleSneakMod.optionShowHUDText == true)
		{
			MinecraftForge.EVENT_BUS.register(RenderTextToHUD.instance);
		}
	}
	
	public void initMod()
	{
		if (ToggleSneakMod.optionShowHUDText)
		{
			RenderTextToHUD.SetHUDText(ToggleSneakMod.ModID + " for Forge - version " + ToggleSneakMod.ModVersion + " Beta!");
		}
		ClientPlayerAPI.register("ToggleSneak", PlayerBase.class);
	}
}