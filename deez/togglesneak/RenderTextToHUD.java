package deez.togglesneak;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class RenderTextToHUD
{
	public static RenderTextToHUD instance = new RenderTextToHUD();
	
	private static String TextForHUD = "";
	
    @SubscribeEvent
    public void RenderGameOverlayEvent(RenderGameOverlayEvent event)
    {
    	if(event.type == RenderGameOverlayEvent.ElementType.TEXT)
    	{
    		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(TextForHUD, 1, 1, 0xffffff);
    	}
    }
    
    public static void SetHUDText(String text)
    {
    	TextForHUD = text;
    }
}