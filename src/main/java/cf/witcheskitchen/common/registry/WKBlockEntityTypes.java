package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.WKIdentifier;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.blocks.entity.BrewingBarrelBlockEntity;
import cf.witcheskitchen.common.blocks.entity.WKTeapotEntity;
import cf.witcheskitchen.common.blocks.entity.WitchesCauldronBlockEntity;
import cf.witcheskitchen.common.blocks.entity.WitchesOvenBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class WKBlockEntityTypes {

    private static final List<ObjectDefinition<BlockEntityType<?>>> BLOCK_ENTITY_TYPES = new ArrayList<>();

    static <E extends BlockEntity> BlockEntityType<E> register(final String path, BiFunction<BlockPos, BlockState, E> factory, Block... blocks) {
        // Throw if any of these is false
        Validate.isTrue(blocks.length > 0);
        Validate.isTrue(factory != null);
        final Identifier id = new WKIdentifier(path);
        final FabricBlockEntityTypeBuilder<E> builder = FabricBlockEntityTypeBuilder.create(factory::apply, blocks);
        final BlockEntityType<E> type = builder.build();
        BLOCK_ENTITY_TYPES.add(new ObjectDefinition<>(id, type));
        return type;
    }

    public static List<ObjectDefinition<BlockEntityType<?>>> getEntityTypes() {
        return Collections.unmodifiableList(BLOCK_ENTITY_TYPES);
    }    public static final BlockEntityType<WitchesOvenBlockEntity> WITCHES_OVEN = register("witches_oven", WitchesOvenBlockEntity::new, WKBlocks.IRON_WITCHES_OVEN, WKBlocks.COPPER_WITCHES_OVEN, WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN);

    public static void register() {
        for (ObjectDefinition<BlockEntityType<?>> entry : BLOCK_ENTITY_TYPES) {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, entry.id(), entry.object());
        }
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Block Entities: Successfully Loaded");
        }
    }

    public static final BlockEntityType<BrewingBarrelBlockEntity> BREWING_BARREL = register("brewing_barrel", BrewingBarrelBlockEntity::new, WKBlocks.OAK_BREWING_BARREL, WKBlocks.SPRUCE_BREWING_BARREL, WKBlocks.BIRCH_BREWING_BARREL, WKBlocks.JUNGLE_BREWING_BARREL, WKBlocks.ACACIA_BREWING_BARREL, WKBlocks.DARK_OAK_BREWING_BARREL, WKBlocks.CRIMSON_BREWING_BARREL, WKBlocks.WARPED_BREWING_BARREL);



    public static final BlockEntityType<WitchesCauldronBlockEntity> WITCHES_CAULDRON = register("witches_cauldron", WitchesCauldronBlockEntity::new, WKBlocks.IRON_WITCHES_CAULDRON);
    public static final BlockEntityType<WKTeapotEntity> TEAPOT = register("teapot", WKTeapotEntity::new, WKBlocks.TEAPOT);


}
