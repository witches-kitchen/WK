package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.blockentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.BlockPos;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public interface WKBlockEntityTypes {

    List<ObjectDefinition<BlockEntityType<?>>> BLOCK_ENTITY_TYPES = new ArrayList<>();

    static <E extends BlockEntity> BlockEntityType<E> register(final String path, BiFunction<BlockPos, BlockState, E> factory, Block... blocks) {
        final BlockEntityType<E> type = QuiltBlockEntityTypeBuilder.create(factory::apply, blocks).build();
        BLOCK_ENTITY_TYPES.add(new ObjectDefinition<>(WitchesKitchen.id(path), type));
        return type;
    }

    static List<ObjectDefinition<BlockEntityType<?>>> getEntityTypes() {
        return Collections.unmodifiableList(BLOCK_ENTITY_TYPES);
    }

    static void init() {
        BLOCK_ENTITY_TYPES.forEach(type -> Registry.register(Registries.BLOCK_ENTITY_TYPE, type.id(), type.object()));
    }

    BlockEntityType<BrewingBarrelBlockEntity> BREWING_BARREL = register("brewing_barrel", BrewingBarrelBlockEntity::new, WKBlocks.OAK_BREWING_BARREL, WKBlocks.SPRUCE_BREWING_BARREL, WKBlocks.BIRCH_BREWING_BARREL, WKBlocks.JUNGLE_BREWING_BARREL, WKBlocks.ACACIA_BREWING_BARREL, WKBlocks.DARK_OAK_BREWING_BARREL, WKBlocks.CRIMSON_BREWING_BARREL, WKBlocks.WARPED_BREWING_BARREL);


    BlockEntityType<WitchesCauldronBlockEntity> WITCHES_CAULDRON = register("witches_cauldron", WitchesCauldronBlockEntity::new, WKBlocks.IRON_WITCHES_CAULDRON);


    BlockEntityType<TeapotBlockEntity> TEAPOT = register("teapot", TeapotBlockEntity::new, WKBlocks.TEAPOT, WKBlocks.COPPER_TEAPOT, WKBlocks.WAXED_COPPER_TEAPOT, WKBlocks.EXPOSED_COPPER_TEAPOT, WKBlocks.WAXED_EXPOSED_COPPER_TEAPOT, WKBlocks.WEATHERED_COPPER_TEAPOT, WKBlocks.WAXED_WEATHERED_COPPER_TEAPOT, WKBlocks.OXIDIZED_COPPER_TEAPOT, WKBlocks.WAXED_OXIDIZED_COPPER_TEAPOT);
    BlockEntityType<WitchesOvenBlockEntity> WITCHES_OVEN = register("witches_oven", WitchesOvenBlockEntity::new, WKBlocks.IRON_WITCHES_OVEN, WKBlocks.COPPER_WITCHES_OVEN, WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_OXIDIZED_COPPER_WITCHES_OVEN);
    BlockEntityType<GlyphBlockEntity> GLYPH = register("glyph", GlyphBlockEntity::new, WKBlocks.GLYPH);


}
