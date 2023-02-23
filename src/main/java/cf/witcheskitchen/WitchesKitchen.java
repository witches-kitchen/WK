package cf.witcheskitchen;

import cf.witcheskitchen.common.registry.*;
import cf.witcheskitchen.data.DimColorReloadListener;
import cf.witcheskitchen.data.worldgen.WKConfiguredFeatures;
import cf.witcheskitchen.data.worldgen.WKFoliagePlacers;
import cf.witcheskitchen.data.worldgen.WKPlacedFeatures;
import net.fabricmc.api.EnvType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.quiltmc.qsl.resource.loader.api.reloader.IdentifiableResourceReloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WitchesKitchen implements ModInitializer {

    public static final String MODID = "witcheskitchen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final ItemGroup GENERAL_TAB = QuiltItemGroup.builder(new Identifier(MODID, "general")).icon(() -> new ItemStack(WKBlocks.IRON_WITCHES_OVEN.asItem())).build();
    public static final ItemGroup FOOD_TAB = QuiltItemGroup.builder(new Identifier(MODID, "food")).icon(() -> new ItemStack(WKItems.ELDER_TEA)).build();
    public static final ItemGroup GINGER_TAB = QuiltItemGroup.builder(new Identifier(MODID, "ginger")).icon(() -> new ItemStack(WKBlocks.GINGERBREAD_BLOCK.asItem())).build();

    @Override
    public void onInitialize(ModContainer mod) {
        WitchesKitchenConfig.init(MODID, WitchesKitchenConfig.class);
        WKPacketTypes.init(EnvType.SERVER);
        WKBlocks.init();
        WKItems.init();
        WKPotions.init();
        WKFoodComponents.init();
        WKTags.init();
        WKBlockEntityTypes.init();
        WKRecipeTypes.init();
        WKScreenHandlerTypes.init();
        WKParticleTypes.init();
        WKFoliagePlacers.init();
        WKConfiguredFeatures.init();
        WKPlacedFeatures.init();
        WKEventsRegistry.init(EnvType.SERVER);
        WKStatusEffects.init();
        WKSoundEvents.init();
        WKEntityTypes.init();
        WKDamageSources.init();
        WKMemoryModuleTypes.init();
        WKSensorTypes.init();

        ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(new DimColorResourceReloader());
    }

    public static Identifier id(String name){
        return new Identifier(MODID, name);
    }

    public static class DimColorResourceReloader extends DimColorReloadListener implements IdentifiableResourceReloader {
        @Override
        public Identifier getQuiltId() {
            return new Identifier(MODID, "dimension_color");
        }
    }
}
