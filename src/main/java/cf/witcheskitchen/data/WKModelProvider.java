package cf.witcheskitchen.data;

import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.*;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;

public class WKModelProvider extends FabricModelProvider {
    public WKModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        //registerTallCrops(generator, WKBlocks.AMARANTH, AmaranthCropBlock.AGE, 0,1,2,3,4,5,6);
        registerStairs(generator, WKBlocks.RAW_GINGERBREAD_BLOCK_STAIRS, WKBlocks.RAW_GINGERBREAD_BLOCK);
        registerStairs(generator, WKBlocks.RAW_CHISELED_GINGERBREAD_BLOCK_STAIRS, WKBlocks.RAW_CHISELED_GINGERBREAD_BLOCK);
        registerStairs(generator, WKBlocks.GINGERBREAD_BEVELED_BLOCK_STAIRS, WKBlocks.GINGERBREAD_BEVELED_BLOCK);
        registerStairs(generator, WKBlocks.GINGERBREAD_BLOCK_STAIRS, WKBlocks.GINGERBREAD_BLOCK);
        registerStairs(generator, WKBlocks.RAW_GINGERBREAD_BEVELED_BLOCK_STAIRS, WKBlocks.RAW_GINGERBREAD_BEVELED_BLOCK);

        registerStairs(generator, WKBlocks.FROSTED_GINGERBREAD_BLOCK_STAIRS, WKBlocks.FROSTED_GINGERBREAD_BLOCK);
        registerStairs(generator, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_STAIRS, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK);
        registerStairs(generator, WKBlocks.CHISELED_GINGERBREAD_BLOCK_STAIRS, WKBlocks.CHISELED_GINGERBREAD_BLOCK);
        registerStairs(generator, WKBlocks.FROSTING_BLOCK_STAIRS, WKBlocks.FROSTING_BLOCK);
        registerStairs(generator, WKBlocks.FROSTED_GINGERBREAD_TILED_BLOCK_STAIRS, WKBlocks.FROSTED_GINGERBREAD_TILED_BLOCK);
        registerStairs(generator, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW_STAIRS, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW);
        registerStairs(generator, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_RED_STAIRS, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_RED);
        registerStairs(generator, WKBlocks.RAW_GINGERBREAD_TILED_BLOCK_STAIRS, WKBlocks.RAW_GINGERBREAD_TILED_BLOCK);
        registerStairs(generator, WKBlocks.GINGERBREAD_TILED_BLOCK_STAIRS, WKBlocks.GINGERBREAD_TILED_BLOCK);

        registerStairs(generator, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE_STAIRS, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE);
        registerStairs(generator, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN_STAIRS, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN);
        registerStairs(generator, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW_STAIRS, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW);
        registerStairs(generator, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_RED_STAIRS, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_RED);
        registerStairs(generator, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE_STAIRS, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE);
        registerStairs(generator, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_GREEN_STAIRS, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_GREEN);
        registerStairs(generator, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT_STAIRS, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT);

        registerSlab(generator, WKBlocks.RAW_GINGERBREAD_BLOCK_SLAB, WKBlocks.RAW_GINGERBREAD_BLOCK);
        registerSlab(generator, WKBlocks.RAW_CHISELED_GINGERBREAD_BLOCK_SLAB, WKBlocks.RAW_CHISELED_GINGERBREAD_BLOCK);
        registerSlab(generator, WKBlocks.GINGERBREAD_BEVELED_BLOCK_SLAB, WKBlocks.GINGERBREAD_BEVELED_BLOCK);

        registerSlab(generator, WKBlocks.GINGERBREAD_BLOCK_SLAB, WKBlocks.GINGERBREAD_BLOCK);
        registerSlab(generator, WKBlocks.RAW_GINGERBREAD_BEVELED_BLOCK_SLAB, WKBlocks.RAW_GINGERBREAD_BEVELED_BLOCK);
        registerSlab(generator, WKBlocks.FROSTED_GINGERBREAD_BLOCK_SLAB, WKBlocks.FROSTED_GINGERBREAD_BLOCK);
        registerSlab(generator, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_SLAB, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK);
        registerSlab(generator, WKBlocks.CHISELED_GINGERBREAD_BLOCK_SLAB, WKBlocks.CHISELED_GINGERBREAD_BLOCK);
        registerSlab(generator, WKBlocks.RAW_GINGERBREAD_TILED_BLOCK_SLAB, WKBlocks.RAW_GINGERBREAD_TILED_BLOCK);
        registerSlab(generator, WKBlocks.GINGERBREAD_TILED_BLOCK_SLAB, WKBlocks.GINGERBREAD_TILED_BLOCK);
        registerSlab(generator, WKBlocks.FROSTING_BLOCK_SLAB, WKBlocks.FROSTING_BLOCK);
        registerSlab(generator, WKBlocks.FROSTED_GINGERBREAD_TILED_BLOCK_SLAB, WKBlocks.FROSTED_GINGERBREAD_TILED_BLOCK);

        registerSlab(generator, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW_SLAB, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW);
        registerSlab(generator, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_RED_SLAB, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_RED);
        registerSlab(generator, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN_SLAB, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN);
        registerSlab(generator, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE_SLAB, WKBlocks.FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE);
        registerSlab(generator, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW_SLAB, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW);
        registerSlab(generator, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_RED_SLAB, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_RED);
        registerSlab(generator, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_GREEN_SLAB, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_GREEN);
        registerSlab(generator, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE_SLAB, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE);
        registerSlab(generator, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT_SLAB, WKBlocks.FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT);

        generator.registerFlowerPotPlant(WKBlocks.BLACKTHORN_SAPLING, WKBlocks.POTTED_BLACKTHORN_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerFlowerPotPlant(WKBlocks.ELDER_SAPLING, WKBlocks.POTTED_ELDER_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerFlowerPotPlant(WKBlocks.HAWTHORN_SAPLING, WKBlocks.POTTED_HAWTHORN_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerFlowerPotPlant(WKBlocks.JUNIPER_SAPLING, WKBlocks.POTTED_JUNIPER_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerFlowerPotPlant(WKBlocks.ROWAN_SAPLING, WKBlocks.POTTED_ROWAN_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerFlowerPotPlant(WKBlocks.SUMAC_SAPLING, WKBlocks.POTTED_SUMAC_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

    }

    public static void registerSlab(BlockStateModelGenerator blockStateModelGenerator, Block slab, Block source) {
        TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(source);
        BlockStateModelGenerator.BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
        pool.base(source, Models.CUBE_ALL);
        pool.slab(slab);
    }

    public void registerStairs(BlockStateModelGenerator blockStateModelGenerator, Block stairBlock, Block source) {
        Texture stairTextureMap = Texture.all(Texture.getId(source));
        Identifier regularModelId = Models.STAIRS.upload(stairBlock, stairTextureMap, blockStateModelGenerator.modelCollector);
        Identifier innerModelId = Models.INNER_STAIRS.upload(stairBlock, stairTextureMap, blockStateModelGenerator.modelCollector);
        Identifier outerModelId = Models.OUTER_STAIRS.upload(stairBlock, stairTextureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(stairBlock, innerModelId, regularModelId, outerModelId));
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(WKItems.BLACKBERRY_TEA, Models.GENERATED);
        generator.register(WKItems.CHAMOMILE_TEA, Models.GENERATED);
        generator.register(WKItems.DOGROSE_TEA, Models.GENERATED);
        generator.register(WKItems.ECHINACEA_TEA, Models.GENERATED);
        generator.register(WKItems.ELDER_TEA, Models.GENERATED);
        generator.register(WKItems.GINGER_TEA, Models.GENERATED);
        generator.register(WKItems.HAWTHORN_TEA, Models.GENERATED);
        generator.register(WKItems.ST_JOHNS_WORT_TEA, Models.GENERATED);
        generator.register(WKItems.MINT_TEA, Models.GENERATED);
        generator.register(WKItems.SUMAC_TEA, Models.GENERATED);
        generator.register(WKItems.YARROW_TEA, Models.GENERATED);

        generator.register(WKItems.BONE_NEEDLE, Models.GENERATED);
        generator.register(WKItems.TAGLOCK, Models.GENERATED);
        generator.register(WKItems.ENCHANTED_CHALK, Models.GENERATED);
        generator.register(WKItems.CHALK, Models.GENERATED);

        generator.register(WKItems.CALEFACTION_BUNDLE, Models.GENERATED);
        generator.register(WKItems.CURSE_OF_MIDAS_BUNDLE, Models.GENERATED);
        generator.register(WKItems.FEAR_BUNDLE, Models.GENERATED);
        generator.register(WKItems.FIELD_GEISTER_HEX_BUNDLE, Models.GENERATED);
        generator.register(WKItems.HUNGRY_POCKETS_BUNDLE, Models.GENERATED);
        generator.register(WKItems.INEPTITUDE_BUNDLE, Models.GENERATED);
        generator.register(WKItems.MISPLACEMENT_BUNDLE, Models.GENERATED);
        generator.register(WKItems.NULLARDOR_BUNDLE, Models.GENERATED);
        generator.register(WKItems.PARANOIA_BUNDLE, Models.GENERATED);
        generator.register(WKItems.PERUNS_JEST_BUNDLE, Models.GENERATED);

        generator.register(WKItems.ABSINTHE, Models.GENERATED);
        generator.register(WKItems.BLACKBERRY_LIQUEUR, Models.GENERATED);
        generator.register(WKItems.BRINJEVEC, Models.GENERATED);
        generator.register(WKItems.HOLUNDERSEKT, Models.GENERATED);
        generator.register(WKItems.JUNIPER_MEAD, Models.GENERATED);
        generator.register(WKItems.RUM, Models.GENERATED);
        generator.register(WKItems.TRAVARICA, Models.GENERATED);
        generator.register(WKItems.GROUND_MUTTON, Models.GENERATED);
        generator.register(WKItems.GROUND_BEEF, Models.GENERATED);
        generator.register(WKItems.GROUND_PORK, Models.GENERATED);
        generator.register(WKItems.HEART_PIE, Models.GENERATED);
        generator.register(WKItems.DEMONIC_STEW, Models.GENERATED);
        generator.register(WKItems.MEATY_STEW, Models.GENERATED);
        generator.register(WKItems.VEGETABLE_STEW, Models.GENERATED);
        generator.register(WKItems.ROOTS_PLATTER, Models.GENERATED);
    }

    public final void registerTallCrops(BlockStateModelGenerator generator, Block crop, Property<Integer> ageProperty, int... ageTextureIndices) {
        if (ageProperty.getValues().size() != ageTextureIndices.length) {
            throw new IllegalArgumentException();
        } else {
            BlockStateVariantMap blockStateVariantMap = BlockStateVariantMap.create(ageProperty, Properties.DOUBLE_BLOCK_HALF)
                    .register((integer, half) -> {
                                int i = ageTextureIndices[integer];
                                Identifier identifier =  generator.createSubModel(crop,"_" + half + "_stage" + i, Models.CROP, Texture::crop);
                                return BlockStateVariant.create().put(VariantSettings.MODEL, identifier);
                    });
            generator.registerItemModel(crop.asItem());
            generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(crop)))
                    .coordinate(blockStateVariantMap)
            );
        }
    }
}
