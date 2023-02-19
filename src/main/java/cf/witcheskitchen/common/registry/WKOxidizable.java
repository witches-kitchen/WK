package cf.witcheskitchen.common.registry;

import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;

public interface WKOxidizable {

    static void init(){
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WKBlocks.COPPER_WITCHES_OVEN, WKBlocks.EXPOSED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WEATHERED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WKBlocks.COPPER_WITCHES_OVEN, WKBlocks.WAXED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_EXPOSED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_WEATHERED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_OXIDIZED_COPPER_WITCHES_OVEN);
    }
}
