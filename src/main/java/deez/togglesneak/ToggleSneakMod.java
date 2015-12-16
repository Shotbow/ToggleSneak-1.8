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
	public static final String ModVersion = "2.3";
	
	public static Configuration config = null;
	public static String optionTextLocation = "topleft";
	public static boolean optionShowHUDText = true;
	public static boolean optionToggleSprint = true;
	public static boolean optionDoubleTap = false;
	public static boolean optionEnableFlyBoost = false;
	public static double optionFlyBoostAmount = 4.0;
	
	
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
		proxy.initMod();
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
		property.comment = "Placeholder Option - Not yet implemented!";
		optionTextLocation = property.getString();
		
		property = config.get("ToggleSneak", "optionToggleSprint", optionToggleSprint);
		property.comment = "If true, use Sprint Toggling - If false, use vanilla sprinting";
		optionToggleSprint = property.getBoolean(true);
		
		property = config.get("ToggleSneak", "optionDoubleTap", optionDoubleTap);
		property.comment = "Allow double-tapping the forward key (W) to begin sprinting";
		optionDoubleTap = property.getBoolean(false);
		
		property = config.get("ToggleSneak", "optionEnableFlyBoost", optionEnableFlyBoost);
		property.comment = "Enable speed boost when flying in creative mode";
		optionEnableFlyBoost = property.getBoolean(false);
		
		property = config.get("ToggleSneak", "optionFlyBoostAmount", optionFlyBoostAmount);
		property.comment = "The multiplier to use when boosting fly speed";
		optionFlyBoostAmount = property.getDouble(4.0);
		
		config.save();
	}
}