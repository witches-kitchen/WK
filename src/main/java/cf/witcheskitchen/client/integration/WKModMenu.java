package cf.witcheskitchen.client.integration;

import cf.witcheskitchen.WitchesKitchen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.gui.screen.Screen;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class WKModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) screen -> MidnightConfig.getScreen(screen, WitchesKitchen.MODID);
    }
}
