package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.client.gui.screen.handler.BrewingBarrelScreenHandler;
import cf.witcheskitchen.client.gui.screen.handler.WitchesOvenScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class WKScreenHandlerTypes {

    public static <T extends ScreenHandler> ScreenHandlerType<T> register(final String name, final ScreenHandlerRegistry.SimpleClientHandlerFactory<T> factory) {
        return ScreenHandlerRegistry.registerSimple(new Identifier(WK.MODID, name), factory);
    }

    public static final ScreenHandlerType<WitchesOvenScreenHandler> WITCHES_OVEN = register("witches_oven", WitchesOvenScreenHandler::new);
    public static final ScreenHandlerType<BrewingBarrelScreenHandler> BREWING_BARREL = register("brewing_barrel", BrewingBarrelScreenHandler::new);

    static {
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Screen Handlers: Successfully Loaded");
        }
    }


}
