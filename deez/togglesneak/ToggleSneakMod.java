package deez.togglesneak;

import java.io.File;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import deez.togglesneak.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import api.player.client.ClientPlayerAPI;

@Mod(modid = ToggleSneakMod.ModID, name = ToggleSneakMod.ModName, version = ToggleSneakMod.ModVersion)

public class ToggleSneakMod
{
	public static final String ModID = "ToggleSneak";
	public static final String ModName= "ToggleSneak";
	public static final String ModVersion = "2.0 for Forge - BETA!";
	
	public static Configuration config = null;
	public static String optionTextLocation = "topleft";
	public static boolean optionShowHUDText = true;
	
	@Instance("ToggleSneak")
	public static ToggleSneakMod instance;
	
	@SidedProxy(
			      clientSide = "deez.togglesneak.proxy.ClientProxy",
			      serverSide = "deez.togglesneak.proxy.CommonProxy"
			   )
	public static CommonProxy proxy;
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		proxy.registerEvents(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (optionShowHUDText)
		{
			RenderTextToHUD.SetHUDText(ModID + " " + ModVersion);
		}
		ClientPlayerAPI.register("ToggleSneak", PlayerBase.class);
	}
	
	public static void loadConfig(File configFile)
	{
		Property property;
		
		config = new Configuration(configFile);
		config.load();
		
		property = config.get("ToggleSneak", "optionShowHUDText", optionShowHUDText);
		property.comment = "Show movement status (Sneaking, Sprinting, etc) on the HUD.";
		optionShowHUDText = property.getBoolean(true);
		
		property = config.get("ToggleSneak", "optionTextLocation", optionTextLocation);
		property.comment = "Where to display the HUD info. Possible values: topleft, topright, bottomleft, bottomright";
		optionTextLocation = property.getString();
		
		config.save();
	}
}