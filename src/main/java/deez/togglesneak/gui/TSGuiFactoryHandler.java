package deez.togglesneak.gui;

import net.minecraftforge.fml.client.IModGuiFactory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Set;

@SuppressWarnings("unused")
public class TSGuiFactoryHandler implements IModGuiFactory {
    @Override
    public void initialize(Minecraft mcInstance) {
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return GuiTSConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }
}
