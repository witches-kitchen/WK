package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.screenhandler.BrewingBarrelScreenHandler;
import cf.witcheskitchen.common.screenhandler.WitchesOvenScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WKScreenHandlerTypes {

    List<ObjectDefinition<ScreenHandlerType<?>>> SCREEN_HANDLER_TYPES = new ArrayList<>();

    static <T extends ScreenHandler> ScreenHandlerType<T> register(final String name, final ScreenHandlerType.Factory<T> factory) {
        Validate.isTrue(factory != null);
        final ScreenHandlerType<T> handler = new ScreenHandlerType<>(factory, FeatureSet.empty());
        final Identifier id = WitchesKitchen.id(name);
        SCREEN_HANDLER_TYPES.add(new ObjectDefinition<>(id, handler));
        return handler;
    }

    static List<ObjectDefinition<ScreenHandlerType<?>>> getScreenHandlers() {
        return Collections.unmodifiableList(SCREEN_HANDLER_TYPES);
    }

    static void init() {
        SCREEN_HANDLER_TYPES.forEach(entry -> Registry.register(Registries.SCREEN_HANDLER, entry.id(), entry.object()));
    }

    ScreenHandlerType<WitchesOvenScreenHandler> WITCHES_OVEN = register("witches_oven", WitchesOvenScreenHandler::new);


    ScreenHandlerType<BrewingBarrelScreenHandler> BREWING_BARREL = register("brewing_barrel", BrewingBarrelScreenHandler::new);


}
