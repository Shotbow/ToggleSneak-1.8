package deez.togglesneak.gui;

import java.text.DecimalFormat;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiSlideControl extends GuiButton
{
	public String	label;			// Text displayed next to value
	public double	curValue;		// Current Slider position, from 0 to 1
	public double	minValue;		// Smallest value represented by the Slider
	public double	maxValue;		// Largest value represented by the Slider
	public boolean	isSliding;		// Is Control currently being manipulated?
	public boolean	useIntegers;	// Use Integers to display values
	
	private static DecimalFormat numFormat = new DecimalFormat("#.00");
	
	public GuiSlideControl(int id, int x, int y, int width, int height, String displayString, double minVal, double maxVal, double curVal, boolean useInts)
	{
		super(id, x, y, width, height, (useInts ? displayString + ((int)curVal) : displayString + numFormat.format(curVal)));
		
		this.label			=	displayString;
		this.minValue		=	minVal;
		this.maxValue		=	maxVal;
		this.curValue		=	(curVal - minVal) / (maxVal - minVal);
		this.useIntegers	=	useInts;
	}

	public double GetValueAsDouble()
	{
		return Double.valueOf(numFormat.format((maxValue - minValue) * curValue + minValue));
	}
	
	public int GetValueAsInt()
	{
		return (int)((maxValue - minValue) * curValue + minValue);
	}
	
	public String GetLabel()
	{
		if(useIntegers)		return label + GetValueAsInt();
		else				return label + numFormat.format(GetValueAsDouble());
	}
	
	protected void SetLabel()
	{
		displayString = GetLabel();
	}
	
	protected int getHoverState(boolean isMouseOver)
	{
		return 0;
	}
	
	protected void mouseDragged(Minecraft mc, int mousePosX, int mousePosY)
	{
		if(visible)
		{
			if(isSliding)
			{
				curValue = (double)(mousePosX - (xPosition + 4)) / (double)(width - 8);
				
				if(curValue < 0.0D)
				{
					curValue = 0.0D;
				}
				if(curValue > 1.0D)
				{
					curValue = 1.0D;
				}
				
				SetLabel();
			}
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(xPosition + (int)(curValue * (float)(width - 8)), yPosition, 0, 66, 4, 20);
			this.drawTexturedModalRect(xPosition + (int)(curValue * (float)(width - 8)) + 4, yPosition, 196, 66, 4, 20);
		}
	}
	
	public boolean mousePressed(Minecraft mc, int mousePosX, int mousePosY)
	{
		if(super.mousePressed(mc, mousePosX, mousePosY))
		{
			curValue = (double)(mousePosX - (xPosition + 4)) / (double)(width - 8);
			
			if(curValue < 0.0D)
			{
				curValue = 0.0D;
			}
			if(curValue > 1.0D)
			{
				curValue = 1.0D;
			}
			
			SetLabel();
			isSliding = true;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void mouseReleased(int mousePosX, int mousePosY)
	{
		isSliding = false;
	}
}