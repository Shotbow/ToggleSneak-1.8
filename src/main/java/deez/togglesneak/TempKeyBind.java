package deez.togglesneak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/**
 * Created by Douwe Koopmans on 28-4-16.
 */
public class TempKeyBind {
    private KeyBinding keyBinding;

    public TempKeyBind() {
        keyBinding = new KeyBinding("temp test", 72, References.MOD_ID);
    }

    @SubscribeEvent
    public void keyPress(InputEvent.KeyInputEvent event) {
        if (keyBinding.isPressed()) {
            EntityPlayerSP playerSP = Minecraft.getMinecraft().thePlayer;
            playerSP.sendChatMessage("fly speed is: " + playerSP.capabilities.getFlySpeed());
        }
    }

    public KeyBinding getKeyBinding() {
        return keyBinding;
    }
}
