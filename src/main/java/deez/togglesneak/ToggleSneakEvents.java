package deez.togglesneak;

import deez.togglesneak.gui.GuiOptionsReplace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ToggleSneakEvents {
    private static final Logger log = LogManager.getLogger(References.MOD_ID);
    public static ToggleSneakEvents instance = new ToggleSneakEvents();

	private static final Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void GuiOpenEvent(GuiOpenEvent event) {
		if(event.gui instanceof GuiOptions && mc.theWorld != null) {
			event.gui = new GuiOptionsReplace(new GuiIngameMenu(), mc.gameSettings);
		}
	}

    @Mod.EventHandler
    public void connectToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        log.info("connected to server, fly speed: " + mc.thePlayer.capabilities.getFlySpeed());
    }
}