package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.screenhandler.BrewingBarrelScreenHandler;
import cf.witcheskitchen.common.screenhandler.WitchesOvenScreenHandler;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WKScreenHandlerTypes {

    List<ObjectDefinition<MenuType<?>>> SCREEN_HANDLER_TYPES = new ArrayList<>();
    MenuType<WitchesOvenScreenHandler> WITCHES_OVEN = register("witches_oven", WitchesOvenScreenHandler::new);
    MenuType<BrewingBarrelScreenHandler> BREWING_BARREL = register("brewing_barrel", BrewingBarrelScreenHandler::new);

    static <T extends AbstractContainerMenu> MenuType<T> register(final String name, final MenuType.MenuSupplier<T> factory) {
        Validate.isTrue(factory != null);
        final MenuType<T> handler = new MenuType<>(factory, FeatureFlagSet.of());
        final ResourceLocation id = WitchesKitchen.id(name);
        SCREEN_HANDLER_TYPES.add(new ObjectDefinition<>(id, handler));
        return handler;
    }

    static List<ObjectDefinition<MenuType<?>>> getScreenHandlers() {
        return Collections.unmodifiableList(SCREEN_HANDLER_TYPES);
    }

    static void init() {
        SCREEN_HANDLER_TYPES.forEach(entry -> Registry.register(BuiltInRegistries.MENU, entry.id(), entry.object()));
    }


}
