package deez.togglesneak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Douwe Koopmans on 27-4-16.
 */
public class FlySpeedRegulator {
    private final Minecraft mc = Minecraft.getMinecraft();
    /**
     * the default fly speed for any client
     */
    private final float vanillaSpeed = 0.05F;

    private float lastClientFlySpeed = vanillaSpeed;
    private float lastServerFlySpeed = vanillaSpeed;
    private boolean speedModified = false;
    private GameType oldGt = GameType.NOT_SET;

    /**
     * <p>
     * checks if we are allowed to change our fly speed right now, if we are not allowed the provided speed will be saved
     * and applied when we are allowed to change our fly speed.
     * </p>
     *
     * <p>
     * if we are allowed to change our fly speed, the provided speed will be set as the new fly speed.
     * if the old speed was set by the server this value will be saved for when we go into a state where we are
     * not allowed to have a modified speed
     * </p>
     * @param speed the preferred fly speed
     */
    public void updateFlySpeed(float speed){
        final EntityPlayerSP player = mc.thePlayer;
        if (player.capabilities.getFlySpeed() != lastClientFlySpeed) {
            lastServerFlySpeed = player.capabilities.getFlySpeed();
        }

        if (canSetFlySpeed()) {
            lastClientFlySpeed = speed;
            setClientFlySpeed();
            speedModified = true;
        } else {
            lastClientFlySpeed = speed;
            speedModified = false;
        }
    }

    // forge doesn't have an event for when your gamemode updates, so we'll have to check every tick if our gm has changed
    @SubscribeEvent
    public void ticker(TickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END) && event.side.equals(Side.CLIENT)) {
            mc.mcProfiler.endStartSection(References.MOD_ID);
            GameType currentGameType = mc.playerController.getCurrentGameType();
            if (!currentGameType.equals(this.oldGt)) {
                onGameModeChange(this.oldGt, currentGameType);
                oldGt = currentGameType;
            }
            mc.mcProfiler.endStartSection(References.MOD_ID);
        }
    }

    private void setClientFlySpeed() {
        final EntityPlayerSP player = mc.thePlayer;

        if (player.movementInput.sneak) player.motionY -= 0.15D * ToggleSneakMod.optionFlyBoostAmount;
        if (player.movementInput.jump) player.motionY += 0.15D * ToggleSneakMod.optionFlyBoostAmount;
        player.capabilities.setFlySpeed(lastClientFlySpeed);
    }

    private void onGameModeChange(GameType oldGt, GameType newGt) {
        final EntityPlayerSP player = mc.thePlayer;

        // has the fly speed been modified and is the new gamemode not creative or spectator
        if (player.capabilities.getFlySpeed() == lastClientFlySpeed && speedModified && !canSetFlySpeed()) {
            player.capabilities.setFlySpeed(lastServerFlySpeed);
            speedModified = false;
        // was the old gamemode not creative or spectator and can we modify our fly speed right now
        } else if (!(oldGt.equals(GameType.CREATIVE) || oldGt.equals(GameType.SPECTATOR)) && canSetFlySpeed()) {
            //modify the fly speed, this will first do some check to make sure we are allow to and will save the servers fly speed
            updateFlySpeed(lastClientFlySpeed);
        }
    }

    /**
     * are we currently allowed to have a modified fly speed
     * @return if we are allowed to modify our fly speed
     */
    private boolean canSetFlySpeed() {
        GameType currentGameType = mc.playerController.getCurrentGameType();
        return currentGameType.equals(GameType.CREATIVE) || currentGameType.equals(GameType.SPECTATOR);
    }
}
