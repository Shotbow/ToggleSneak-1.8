package deez.togglesneak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.ResourceLocation;

import api.player.client.ClientPlayerAPI;
import api.player.client.ClientPlayerBase;

public class PlayerBase extends ClientPlayerBase
{
	private Minecraft mc = Minecraft.getMinecraft();
	private CustomMovementInput customMovementInput = new CustomMovementInput();
	
	public PlayerBase(ClientPlayerAPI api)
	{
		super(api);
	}
	
	/*
	 * 		EntityPlayerSP.onLivingUpdate() - Adapted to PlayerAPI
	 */
	public void onLivingUpdate()
	{
		if(this.player.sprintingTicksLeft > 0)
		{
			--this.player.sprintingTicksLeft;
			if(this.player.sprintingTicksLeft == 0)
			{
				this.player.setSprinting(false);
			}
		}

		if(this.playerAPI.getSprintToggleTimerField() > 0)
		{
			this.playerAPI.setSprintToggleTimerField(this.playerAPI.getSprintToggleTimerField() - 1);
		}

		if(this.mc.playerController.enableEverythingIsScrewedUpMode())
		{
			this.player.posX = this.player.posZ = 0.5D;
			this.player.posX = 0.0D;
			this.player.posZ = 0.0D;
			this.player.rotationYaw = (float)this.player.ticksExisted / 12.0F;
			this.player.rotationPitch = 10.0F;
			this.player.posY = 68.5D;
		}
		else
		{
			this.player.prevTimeInPortal = this.player.timeInPortal;
			if(this.playerAPI.getInPortalField())
			{
				if(this.mc.currentScreen != null)
				{
					this.mc.displayGuiScreen((GuiScreen)null);
				}

				if(this.player.timeInPortal == 0.0F)
				{
					this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), this.player.getRNG().nextFloat() * 0.4F + 0.8F));
				}

				this.player.timeInPortal += 0.0125F;

				if(this.player.timeInPortal >= 1.0F)
				{
					this.player.timeInPortal = 1.0F;
				}

				this.playerAPI.setInPortalField(false);
			}
			else if(this.player.isPotionActive(Potion.confusion) && this.player.getActivePotionEffect(Potion.confusion).getDuration() > 60)
			{
				this.player.timeInPortal += 0.006666667F;
				if(this.player.timeInPortal > 1.0F)
				{
					this.player.timeInPortal = 1.0F;
				}
			}
			else
			{
				if(this.player.timeInPortal > 0.0F)
				{
					this.player.timeInPortal -= 0.05F;
				}

				if(this.player.timeInPortal < 0.0F)
				{
					this.player.timeInPortal = 0.0F;
				}
			}


			if(this.player.timeUntilPortal > 0)
			{
				--this.player.timeUntilPortal;
			}

			boolean isJumping = this.player.movementInput.jump;
			
			float minSpeed = 0.8F;
			boolean isMovingForward = this.player.movementInput.moveForward >= minSpeed;
			this.customMovementInput.update(this.mc, (MovementInputFromOptions)this.player.movementInput, this.player);

			if(this.player.isUsingItem() && !this.player.isRiding())
			{
				this.player.movementInput.moveStrafe *= 0.2F;
				this.player.movementInput.moveForward *= 0.2F;
				this.playerAPI.setSprintToggleTimerField(0);
			}

			if(this.player.movementInput.sneak && this.player.ySize < 0.2F)
			{
				this.player.ySize = 0.2F;
			}

			this.playerAPI.localPushOutOfBlocks(this.player.posX - (double)this.player.width * 0.35D, this.player.boundingBox.minY + 0.5D, this.player.posZ + (double)this.player.width * 0.35D);
			this.playerAPI.localPushOutOfBlocks(this.player.posX - (double)this.player.width * 0.35D, this.player.boundingBox.minY + 0.5D, this.player.posZ - (double)this.player.width * 0.35D);
			this.playerAPI.localPushOutOfBlocks(this.player.posX + (double)this.player.width * 0.35D, this.player.boundingBox.minY + 0.5D, this.player.posZ - (double)this.player.width * 0.35D);
			this.playerAPI.localPushOutOfBlocks(this.player.posX + (double)this.player.width * 0.35D, this.player.boundingBox.minY + 0.5D, this.player.posZ + (double)this.player.width * 0.35D);
			boolean enoughHunger = (float)this.player.getFoodStats().getFoodLevel() > 6.0F || this.player.capabilities.isFlying;
			
			/*
			 * 		Begin ToggleSneak Changes
			 */
			
			boolean state = this.customMovementInput.sprint;
			
//			if (!this.player.capabilities.isFlying  && ((MovementInputFromOptions) this.player.movementInput).sneak)
//			{
//				state = false;
//			}
			
			if(this.player.onGround && enoughHunger && !this.player.isUsingItem() && !this.player.isPotionActive(Potion.blindness))
			{
				this.player.setSprinting(state);
			}
			
			if(this.player.isSprinting() && (this.player.movementInput.moveForward < minSpeed || this.player.isCollidedHorizontally || !enoughHunger))
			{
				this.player.setSprinting(false);
			}
			
			/*
			 * 		End ToggleSneak Changes
			 */
			
			if(this.player.capabilities.allowFlying && !isJumping && this.player.movementInput.jump)
			{
				if(this.playerAPI.getFlyToggleTimerField() == 0)
				{
					this.playerAPI.setFlyToggleTimerField(7);
				}
				else
				{
					this.player.capabilities.isFlying = !this.player.capabilities.isFlying;
					this.player.sendPlayerAbilities();
					this.playerAPI.setFlyToggleTimerField(0);
				}
			}

			if(this.player.capabilities.isFlying)
			{
				if(this.player.movementInput.sneak)
				{
					this.player.motionY -= 0.15D;
				}
				if(this.player.movementInput.jump)
				{
					this.player.motionY += 0.15D;
				}
			}

			if(this.player.isRidingHorse())
			{
				if(this.playerAPI.getHorseJumpPowerCounterField() < 0)
				{
					this.playerAPI.setHorseJumpPowerCounterField(this.playerAPI.getHorseJumpPowerCounterField() + 1);
					if(this.playerAPI.getHorseJumpPowerCounterField() == 0)
					{
						this.playerAPI.setHorseJumpPowerField(0.0F);
					}
				}

				if(isJumping && !this.player.movementInput.jump)
				{
					this.playerAPI.setHorseJumpPowerCounterField(this.playerAPI.getHorseJumpPowerCounterField() - 10);
					this.playerAPI.setHorseJumpPowerCounterField(-10);
					((EntityClientPlayerMP)this.player).sendQueue.addToSendQueue(new C0BPacketEntityAction(this.player, 6, (int)(this.player.getHorseJumpPower() * 100.0F)));
				}
				else if(!isJumping && this.player.movementInput.jump)
				{
					this.playerAPI.setHorseJumpPowerCounterField(0);
					this.playerAPI.setHorseJumpPowerField(0.0F);
				}
				else if(isJumping)
				{
					this.playerAPI.setHorseJumpPowerCounterField(this.playerAPI.getHorseJumpPowerCounterField() + 1);
					if(this.playerAPI.getHorseJumpPowerCounterField() < 10)
					{
						this.playerAPI.setHorseJumpPowerField((float)this.playerAPI.getHorseJumpPowerCounterField() * 0.1F);
					}
					else
					{
						this.playerAPI.setHorseJumpPowerField(0.8F + 2.0F / (float)(this.playerAPI.getHorseJumpPowerCounterField() - 9) * 0.1F);
					}
				}
			}
			else
			{
				this.playerAPI.setHorseJumpPowerField(0.0F);
			}

			this.playerAPI.superOnLivingUpdate();
			if(this.player.onGround && this.player.capabilities.isFlying)
			{
				this.player.capabilities.isFlying = false;
				this.player.sendPlayerAbilities();
			}
		}
	}
}