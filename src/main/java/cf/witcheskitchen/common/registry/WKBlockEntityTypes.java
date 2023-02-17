package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.blockentity.BrewingBarrelBlockEntity;
import cf.witcheskitchen.common.blockentity.WKTeapotEntity;
import cf.witcheskitchen.common.blockentity.WitchesCauldronBlockEntity;
import cf.witcheskitchen.common.blockentity.WitchesOvenBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class WKBlockEntityTypes {

    private static final List<ObjectDefinition<BlockEntityType<?>>> BLOCK_ENTITY_TYPES = new ArrayList<>();

    private static <E extends BlockEntity> BlockEntityType<E> register(final String path, BiFunction<BlockPos, BlockState, E> factory, Block... blocks) {
        // Throw if any of these is false
        Validate.isTrue(blocks.length > 0);
        Validate.isTrue(factory != null);
        final Identifier id = WK.id(path);
        final QuiltBlockEntityTypeBuilder<E> builder = QuiltBlockEntityTypeBuilder.create(factory::apply, blocks);
        final BlockEntityType<E> type = builder.build();
        BLOCK_ENTITY_TYPES.add(new ObjectDefinition<>(id, type));
        return type;
    }

    public static List<ObjectDefinition<BlockEntityType<?>>> getEntityTypes() {
        return Collections.unmodifiableList(BLOCK_ENTITY_TYPES);
    }

    public static final BlockEntityType<WitchesOvenBlockEntity> WITCHES_OVEN = register("witches_oven", WitchesOvenBlockEntity::new, WKBlocks.IRON_WITCHES_OVEN, WKBlocks.COPPER_WITCHES_OVEN, WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_OXIDIZED_COPPER_WITCHES_OVEN);

    public static void register() {
        BLOCK_ENTITY_TYPES.forEach(type -> Registry.register(Registry.BLOCK_ENTITY_TYPE, type.id(), type.object()));
        if (WKConfig.debugMode) {
            WK.LOGGER.info("Witches Kitchen Base Block Entities: Successfully Loaded");
        }
    }

    public static final BlockEntityType<BrewingBarrelBlockEntity> BREWING_BARREL = register("brewing_barrel", BrewingBarrelBlockEntity::new, WKBlocks.OAK_BREWING_BARREL, WKBlocks.SPRUCE_BREWING_BARREL, WKBlocks.BIRCH_BREWING_BARREL, WKBlocks.JUNGLE_BREWING_BARREL, WKBlocks.ACACIA_BREWING_BARREL, WKBlocks.DARK_OAK_BREWING_BARREL, WKBlocks.CRIMSON_BREWING_BARREL, WKBlocks.WARPED_BREWING_BARREL);


    public static final BlockEntityType<WitchesCauldronBlockEntity> WITCHES_CAULDRON = register("witches_cauldron", WitchesCauldronBlockEntity::new, WKBlocks.IRON_WITCHES_CAULDRON);
    public static final BlockEntityType<WKTeapotEntity> TEAPOT = register("teapot", WKTeapotEntity::new, WKBlocks.TEAPOT);


}
