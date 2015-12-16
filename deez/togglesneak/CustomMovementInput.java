package deez.togglesneak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInputFromOptions;

/*
 *		ToggleSneak's replacement for MovementInputFromOptions
 */
public class CustomMovementInput
{
	public boolean sprint = false;

	private long lastPressed;
	private long lastSprintPressed;
	private boolean handledSneakPress;
	private boolean handledSprintPress;
	private boolean holdingSneak;
	private boolean holdingSprint;
	private boolean wasRiding;

	/*
	 * 		MovementInputFromOptions.updatePlayerMoveState()
	 */
	public void update(Minecraft mc, MovementInputFromOptions options, EntityPlayerSP thisPlayer)
	{
		options.moveStrafe = 0.0F;
		options.moveForward = 0.0F;

		GameSettings settings = mc.gameSettings;

		if(settings.keyBindForward.getIsKeyPressed())
		{
			++options.moveForward;
		}

		if(settings.keyBindBack.getIsKeyPressed())
		{
			--options.moveForward;
		}

		if(settings.keyBindLeft.getIsKeyPressed())
		{
			++options.moveStrafe;
		}

		if(settings.keyBindRight.getIsKeyPressed())
		{
			--options.moveStrafe;
		}

		options.jump = settings.keyBindJump.getIsKeyPressed();

		//
		// Sneak Toggle - Essentially the same as old ToggleSprint
		//
		if (settings.keyBindSneak.getIsKeyPressed() && !this.handledSneakPress)
        {       	
        	if(thisPlayer.isRiding() || thisPlayer.capabilities.isFlying)
        	{
        		options.sneak = true;
        		this.wasRiding = thisPlayer.isRiding();
        		UpdateHUD("");
        	}
        	else
        	{
        		options.sneak = !options.sneak;
        		UpdateHUD(options.sneak ? "Sneaking..." : "");
        	}
        	
        	this.lastPressed = System.currentTimeMillis();
        	this.handledSneakPress = true;
        }
		
		if(!this.holdingSneak && settings.keyBindSneak.getIsKeyPressed() && this.handledSneakPress)
		{
			if (System.currentTimeMillis() - this.lastPressed > 300L && !thisPlayer.capabilities.isFlying)
			{
				UpdateHUD("Sneaking (Key Held)...");
				this.holdingSneak = true;
			}
		}

        if (!settings.keyBindSneak.getIsKeyPressed() && this.handledSneakPress)
        {
        	if(thisPlayer.capabilities.isFlying)
        	{
        		UpdateHUD("");
        		options.sneak = false;
        	}
        	else if (this.wasRiding)
        	{
        		UpdateHUD("");
        		options.sneak = false;
        		this.wasRiding = false;
        	}
        	else if(System.currentTimeMillis() - this.lastPressed > 300L)
        	{
        		UpdateHUD("");
        		options.sneak = false;
        	}
        	
        	if(!thisPlayer.capabilities.isFlying) UpdateHUD(options.sneak ? "Sneaking (Toggled)..." : "");
        	this.handledSneakPress = false;
        	this.holdingSneak = false;
        }

		if(options.sneak)
		{
			options.moveStrafe = (float)((double)options.moveStrafe * 0.3D);
			options.moveForward = (float)((double)options.moveForward * 0.3D);
		}
		
		//
		//  Sprint Toggle - Added 4/1/2014
		//
		if(settings.keyBindSprint.getIsKeyPressed() && !this.handledSprintPress)
		{
			this.sprint = !this.sprint;
			if (!thisPlayer.capabilities.isFlying) UpdateHUD(this.sprint ? "Sprinting..." : "");
			this.lastSprintPressed = System.currentTimeMillis();
			this.handledSprintPress = true;
		}
		
		if(!this.holdingSprint && settings.keyBindSprint.getIsKeyPressed() && this.handledSprintPress)
		{
			if (System.currentTimeMillis() - this.lastSprintPressed > 300L && !thisPlayer.capabilities.isFlying)
			{
				UpdateHUD("Sprinting (Key Held)...");
				this.holdingSprint = true;
			}
		}
		
		if(!settings.keyBindSprint.getIsKeyPressed() && this.handledSprintPress)
		{
			if(System.currentTimeMillis() - this.lastSprintPressed > 300L)
			{
				this.sprint = false;
			}
			
			UpdateHUD(this.sprint ? "Sprinting (Toggled)..." : "");
			this.handledSprintPress = false;
			this.holdingSprint = false;
		}
	}

	private static void UpdateHUD(String message)
	{
		if (ToggleSneakMod.optionShowHUDText)	RenderTextToHUD.SetHUDText(message);
	}
}