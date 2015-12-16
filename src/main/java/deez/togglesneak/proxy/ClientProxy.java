package deez.togglesneak.proxy;

import api.player.client.ClientPlayerAPI;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.common.MinecraftForge;

import deez.togglesneak.PlayerBase;
import deez.togglesneak.RenderTextToHUD;
import deez.togglesneak.ToggleSneakEvents;
import deez.togglesneak.ToggleSneakMod;

public class ClientProxy extends CommonProxy
{
	public void registerEvents(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(RenderTextToHUD.instance);
		MinecraftForge.EVENT_BUS.register(ToggleSneakEvents.instance);
	}
	
	public void initMod()
	{
		RenderTextToHUD.SetHUDText(ToggleSneakMod.ModID + " for Forge - version " + ToggleSneakMod.ModVersion + " Beta!");
		ClientPlayerAPI.register("ToggleSneak", PlayerBase.class);
	}
}