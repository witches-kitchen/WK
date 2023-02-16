package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.client.gui.screen.handler.BrewingBarrelScreenHandler;
import cf.witcheskitchen.client.gui.screen.handler.WitchesOvenScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WKScreenHandlerTypes {

    private static final List<ObjectDefinition<ScreenHandlerType<?>>> SCREEN_HANDLER_TYPES = new ArrayList<>();

    static {
        if (WKConfig.debugMode) {
            WK.LOGGER.info("Witches Kitchen Base Screen Handlers: Successfully Loaded");
        }
    }

    static <T extends ScreenHandler> ScreenHandlerType<T> register(final String name, final ScreenHandlerType.Factory<T> factory) {
        Validate.isTrue(factory != null);
        final ScreenHandlerType<T> handler = new ScreenHandlerType<>(factory);
        final Identifier id = WK.id(name);
        SCREEN_HANDLER_TYPES.add(new ObjectDefinition<>(id, handler));
        return handler;
    }

    public static List<ObjectDefinition<ScreenHandlerType<?>>> getScreenHandlers() {
        return Collections.unmodifiableList(SCREEN_HANDLER_TYPES);
    }

    public static void register() {
        SCREEN_HANDLER_TYPES.forEach(entry -> Registry.register(Registry.SCREEN_HANDLER, entry.id(), entry.object()));
    }

    public static final ScreenHandlerType<WitchesOvenScreenHandler> WITCHES_OVEN = register("witches_oven", WitchesOvenScreenHandler::new);


    public static final ScreenHandlerType<BrewingBarrelScreenHandler> BREWING_BARREL = register("brewing_barrel", BrewingBarrelScreenHandler::new);


}
