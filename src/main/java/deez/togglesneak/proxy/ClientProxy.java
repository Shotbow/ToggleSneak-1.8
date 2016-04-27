package deez.togglesneak.proxy;

import api.player.client.ClientPlayerAPI;

import deez.togglesneak.*;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerEvents(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(RenderTextToHUD.instance);
		MinecraftForge.EVENT_BUS.register(ToggleSneakEvents.instance);
	}
	
	@Override
	public void initMod()
	{
		RenderTextToHUD.SetHUDText(References.MOD_ID + " for Forge - version " + References.MOD_VERSION + " Beta!");
		ClientPlayerAPI.register("ToggleSneak", PlayerBase.class);
	}
}