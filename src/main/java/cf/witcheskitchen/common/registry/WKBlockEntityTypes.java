package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.blocks.entity.BrewingBarrelBlockEntity;
import cf.witcheskitchen.common.blocks.entity.WitchesCauldronBlockEntity;
import cf.witcheskitchen.common.blocks.entity.WitchesOvenBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKBlockEntityTypes {

    private static void registerBlockEntity(String id, BlockEntityType<? extends BlockEntity> blockEntity) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(WK.MODID, id), blockEntity);
    }

    public static void register() {
        registerBlockEntity("witches_oven", WITCHES_OVEN);
        registerBlockEntity("witches_cauldron", WITCHES_CAULDRON);
        registerBlockEntity("brewing_barrel", BREWING_BARREL);
    }

    public static final BlockEntityType<WitchesOvenBlockEntity> WITCHES_OVEN = FabricBlockEntityTypeBuilder.create(WitchesOvenBlockEntity::new, WKBlocks.IRON_WITCHES_OVEN, WKBlocks.COPPER_WITCHES_OVEN, WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN).build();
    public static final BlockEntityType<BrewingBarrelBlockEntity> BREWING_BARREL = FabricBlockEntityTypeBuilder.create(BrewingBarrelBlockEntity::new, WKBlocks.OAK_BREWING_BARREL, WKBlocks.SPRUCE_BREWING_BARREL, WKBlocks.BIRCH_BREWING_BARREL, WKBlocks.JUNGLE_BREWING_BARREL, WKBlocks.ACACIA_BREWING_BARREL, WKBlocks.DARK_OAK_BREWING_BARREL, WKBlocks.CRIMSON_BREWING_BARREL, WKBlocks.WARPED_BREWING_BARREL).build();
    public static final BlockEntityType<WitchesCauldronBlockEntity> WITCHES_CAULDRON = FabricBlockEntityTypeBuilder.create(WitchesCauldronBlockEntity::new, WKBlocks.IRON_WITCHES_CAULDRON).build();

}
