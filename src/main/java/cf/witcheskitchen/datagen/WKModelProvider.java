package cf.witcheskitchen.datagen;

import cf.witcheskitchen.common.crop.AmaranthCropBlock;
import cf.witcheskitchen.common.registry.WKBlocks;
import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.*;
import net.minecraft.state.property.BooleanProperty;
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
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

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
