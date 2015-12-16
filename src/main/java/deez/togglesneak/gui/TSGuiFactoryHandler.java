package deez.togglesneak.gui;

import net.minecraftforge.fml.client.IModGuiFactory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Set;

public class TSGuiFactoryHandler implements IModGuiFactory
{
	public void initialize(Minecraft mcInstance)
	{		
	}
	
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return GuiTSConfig.class;
	}
	
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}
	
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		return null;
	}
}
