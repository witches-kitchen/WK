package cf.witcheskitchen.client.data;

import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class WKModelProvider extends FabricModelProvider {
    public WKModelProvider(FabricDataOutput output) {
        super(output);
    }

    public static void registerSlab(BlockModelGenerators blockStateModelGenerator, Block slab, Block source) {
        TexturedModel texturedModel = TexturedModel.CUBE.get(source);
        BlockModelGenerators.BlockFamilyProvider pool = blockStateModelGenerator.new BlockFamilyProvider(texturedModel.getMapping());
        pool.fullBlock(source, ModelTemplates.CUBE_ALL);
        pool.slab(slab);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {

        generator.createPlant(WKBlocks.BLACKTHORN_SAPLING, WKBlocks.POTTED_BLACKTHORN_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
        generator.createPlant(WKBlocks.ELDER_SAPLING, WKBlocks.POTTED_ELDER_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
        generator.createPlant(WKBlocks.HAWTHORN_SAPLING, WKBlocks.POTTED_HAWTHORN_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
        generator.createPlant(WKBlocks.JUNIPER_SAPLING, WKBlocks.POTTED_JUNIPER_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
        generator.createPlant(WKBlocks.ROWAN_SAPLING, WKBlocks.POTTED_ROWAN_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);
        generator.createPlant(WKBlocks.SUMAC_SAPLING, WKBlocks.POTTED_SUMAC_SAPLING, BlockModelGenerators.PlantType.NOT_TINTED);

        generator.woodProvider(WKBlocks.BLACKTHORN_LOG).log(WKBlocks.BLACKTHORN_LOG).wood(WKBlocks.BLACKTHORN_WOOD);
        generator.woodProvider(WKBlocks.ELDER_LOG).log(WKBlocks.ELDER_LOG).wood(WKBlocks.ELDER_WOOD);
        generator.woodProvider(WKBlocks.HAWTHORN_LOG).log(WKBlocks.HAWTHORN_LOG).wood(WKBlocks.HAWTHORN_WOOD);
        generator.woodProvider(WKBlocks.JUNIPER_LOG).log(WKBlocks.JUNIPER_LOG).wood(WKBlocks.JUNIPER_WOOD);
        generator.woodProvider(WKBlocks.ROWAN_LOG).log(WKBlocks.ROWAN_LOG).wood(WKBlocks.ROWAN_WOOD);
        generator.woodProvider(WKBlocks.SUMAC_LOG).log(WKBlocks.SUMAC_LOG).wood(WKBlocks.SUMAC_WOOD);

        generator.woodProvider(WKBlocks.STRIPPED_BLACKTHORN_LOG).log(WKBlocks.STRIPPED_BLACKTHORN_LOG).wood(WKBlocks.STRIPPED_BLACKTHORN_WOOD);
        generator.woodProvider(WKBlocks.STRIPPED_ELDER_LOG).log(WKBlocks.STRIPPED_ELDER_LOG).wood(WKBlocks.STRIPPED_ELDER_WOOD);
        generator.woodProvider(WKBlocks.STRIPPED_HAWTHORN_LOG).log(WKBlocks.STRIPPED_HAWTHORN_LOG).wood(WKBlocks.STRIPPED_HAWTHORN_WOOD);
        generator.woodProvider(WKBlocks.STRIPPED_JUNIPER_LOG).log(WKBlocks.STRIPPED_JUNIPER_LOG).wood(WKBlocks.STRIPPED_JUNIPER_WOOD);
        generator.woodProvider(WKBlocks.STRIPPED_ROWAN_LOG).log(WKBlocks.STRIPPED_ROWAN_LOG).wood(WKBlocks.STRIPPED_ROWAN_WOOD);
        generator.woodProvider(WKBlocks.STRIPPED_SUMAC_LOG).log(WKBlocks.STRIPPED_SUMAC_LOG).wood(WKBlocks.STRIPPED_SUMAC_WOOD);

        generator.createTrivialBlock(WKBlocks.BLACKTHORN_LEAVES, TexturedModel.LEAVES);
        generator.createTrivialBlock(WKBlocks.ELDER_LEAVES, TexturedModel.LEAVES);
        generator.createTrivialBlock(WKBlocks.HAWTHORN_LEAVES, TexturedModel.LEAVES);
        generator.createTrivialBlock(WKBlocks.JUNIPER_LEAVES, TexturedModel.LEAVES);
        generator.createTrivialBlock(WKBlocks.ROWAN_LEAVES, TexturedModel.LEAVES);
        generator.createTrivialBlock(WKBlocks.SUMAC_LEAVES, TexturedModel.LEAVES);

        registerSlab(generator, WKBlocks.BLACKTHORN_SLAB, WKBlocks.BLACKTHORN_PLANKS);
        registerSlab(generator, WKBlocks.ELDER_SLAB, WKBlocks.ELDER_PLANKS);
        registerSlab(generator, WKBlocks.HAWTHORN_SLAB, WKBlocks.HAWTHORN_PLANKS);
        registerSlab(generator, WKBlocks.JUNIPER_SLAB, WKBlocks.JUNIPER_PLANKS);
        registerSlab(generator, WKBlocks.ROWAN_SLAB, WKBlocks.ROWAN_PLANKS);
        registerSlab(generator, WKBlocks.SUMAC_SLAB, WKBlocks.SUMAC_PLANKS);

        registerStairs(generator, WKBlocks.BLACKTHORN_STAIRS, WKBlocks.BLACKTHORN_PLANKS);
        registerStairs(generator, WKBlocks.ELDER_STAIRS, WKBlocks.ELDER_PLANKS);
        registerStairs(generator, WKBlocks.HAWTHORN_STAIRS, WKBlocks.HAWTHORN_PLANKS);
        registerStairs(generator, WKBlocks.JUNIPER_STAIRS, WKBlocks.JUNIPER_PLANKS);
        registerStairs(generator, WKBlocks.ROWAN_STAIRS, WKBlocks.ROWAN_PLANKS);
        registerStairs(generator, WKBlocks.SUMAC_STAIRS, WKBlocks.SUMAC_PLANKS);

        generator.createDoor(WKBlocks.BLACKTHORN_DOOR);
        generator.createDoor(WKBlocks.ELDER_DOOR);
        generator.createDoor(WKBlocks.HAWTHORN_DOOR);
        generator.createDoor(WKBlocks.JUNIPER_DOOR);
        generator.createDoor(WKBlocks.ROWAN_DOOR);
        generator.createDoor(WKBlocks.SUMAC_DOOR);

        registerPressurePlate(generator, WKBlocks.BLACKTHORN_PRESSURE_PLATE, WKBlocks.BLACKTHORN_PLANKS);
        registerPressurePlate(generator, WKBlocks.ELDER_PRESSURE_PLATE, WKBlocks.ELDER_PLANKS);
        registerPressurePlate(generator, WKBlocks.HAWTHORN_PRESSURE_PLATE, WKBlocks.HAWTHORN_PLANKS);
        registerPressurePlate(generator, WKBlocks.JUNIPER_PRESSURE_PLATE, WKBlocks.JUNIPER_PLANKS);
        registerPressurePlate(generator, WKBlocks.ROWAN_PRESSURE_PLATE, WKBlocks.ROWAN_PLANKS);
        registerPressurePlate(generator, WKBlocks.SUMAC_PRESSURE_PLATE, WKBlocks.SUMAC_PLANKS);

        registerButton(generator, WKBlocks.BLACKTHORN_BUTTON, WKBlocks.BLACKTHORN_PLANKS);
        registerButton(generator, WKBlocks.ELDER_BUTTON, WKBlocks.ELDER_PLANKS);
        registerButton(generator, WKBlocks.HAWTHORN_BUTTON, WKBlocks.HAWTHORN_PLANKS);
        registerButton(generator, WKBlocks.JUNIPER_BUTTON, WKBlocks.JUNIPER_PLANKS);
        registerButton(generator, WKBlocks.ROWAN_BUTTON, WKBlocks.ROWAN_PLANKS);
        registerButton(generator, WKBlocks.SUMAC_BUTTON, WKBlocks.SUMAC_PLANKS);

        registerFence(generator, WKBlocks.BLACKTHORN_FENCE, WKBlocks.BLACKTHORN_PLANKS);
        registerFence(generator, WKBlocks.ELDER_FENCE, WKBlocks.ELDER_PLANKS);
        registerFence(generator, WKBlocks.HAWTHORN_FENCE, WKBlocks.HAWTHORN_PLANKS);
        registerFence(generator, WKBlocks.JUNIPER_FENCE, WKBlocks.JUNIPER_PLANKS);
        registerFence(generator, WKBlocks.ROWAN_FENCE, WKBlocks.ROWAN_PLANKS);
        registerFence(generator, WKBlocks.SUMAC_FENCE, WKBlocks.SUMAC_PLANKS);

        registerFenceGate(generator, WKBlocks.BLACKTHORN_FENCE_GATE, WKBlocks.BLACKTHORN_PLANKS);
        registerFenceGate(generator, WKBlocks.ELDER_FENCE_GATE, WKBlocks.ELDER_PLANKS);
        registerFenceGate(generator, WKBlocks.HAWTHORN_FENCE_GATE, WKBlocks.HAWTHORN_PLANKS);
        registerFenceGate(generator, WKBlocks.JUNIPER_FENCE_GATE, WKBlocks.JUNIPER_PLANKS);
        registerFenceGate(generator, WKBlocks.ROWAN_FENCE_GATE, WKBlocks.ROWAN_PLANKS);
        registerFenceGate(generator, WKBlocks.SUMAC_FENCE_GATE, WKBlocks.SUMAC_PLANKS);
    }

    public void registerStairs(BlockModelGenerators blockStateModelGenerator, Block stairBlock, Block source) {
        TextureMapping stairTextureMap = TextureMapping.cube(TextureMapping.getBlockTexture(source));
        ResourceLocation regularModelId = ModelTemplates.STAIRS_STRAIGHT.create(stairBlock, stairTextureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation innerModelId = ModelTemplates.STAIRS_INNER.create(stairBlock, stairTextureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation outerModelId = ModelTemplates.STAIRS_OUTER.create(stairBlock, stairTextureMap, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createStairs(stairBlock, plainVariant(innerModelId), plainVariant(regularModelId), plainVariant(outerModelId)));
    }

    public void registerPressurePlate(BlockModelGenerators blockStateModelGenerator, Block pressureplateBlock, Block parentBlock) {
        TextureMapping textureMap = TextureMapping.cube(parentBlock);
        ResourceLocation pressureplate_up = ModelTemplates.PRESSURE_PLATE_UP.create(pressureplateBlock, textureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation pressureplate_down = ModelTemplates.PRESSURE_PLATE_DOWN.create(pressureplateBlock, textureMap, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(createPressurePlate(pressureplateBlock, plainVariant(pressureplate_up), plainVariant(pressureplate_down)));
    }

    public void registerFence(BlockModelGenerators blockStateModelGenerator, Block fenceBlock, Block parentBlock) {
        TextureMapping textureMap = TextureMapping.cube(parentBlock);
        ResourceLocation post = ModelTemplates.FENCE_POST.create(fenceBlock, textureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation side = ModelTemplates.FENCE_SIDE.create(fenceBlock, textureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.create(fenceBlock, textureMap, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(createFence(fenceBlock, plainVariant(post), plainVariant(side)));
        blockStateModelGenerator.registerSimpleItemModel(fenceBlock, inventory);
    }

    public void registerFenceGate(BlockModelGenerators blockStateModelGenerator, Block fencegateBlock, Block parentBlock) {
        TextureMapping textureMap = TextureMapping.cube(parentBlock);
        ResourceLocation fence_gate = ModelTemplates.FENCE_GATE_CLOSED.create(fencegateBlock, textureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation fence_gate_open = ModelTemplates.FENCE_GATE_OPEN.create(fencegateBlock, textureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation fence_gate_wall = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fencegateBlock, textureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation fence_gate_wall_open = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fencegateBlock, textureMap, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(createFenceGate(fencegateBlock, plainVariant(fence_gate_open), plainVariant(fence_gate), plainVariant(fence_gate_wall_open), plainVariant(fence_gate_wall), false));
    }

    public void registerButton(BlockModelGenerators blockStateModelGenerator, Block buttonBlock, Block parentBlock) {
        TextureMapping textureMap = TextureMapping.cube(parentBlock);
        ResourceLocation button = ModelTemplates.BUTTON.create(buttonBlock, textureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation pressed = ModelTemplates.BUTTON_PRESSED.create(buttonBlock, textureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation inventory = ModelTemplates.BUTTON_INVENTORY.create(buttonBlock, textureMap, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(createButton(buttonBlock, plainVariant(button), plainVariant(pressed)));
        blockStateModelGenerator.registerSimpleItemModel(buttonBlock, inventory);
    }

    public void registerSign(BlockModelGenerators blockStateModelGenerator, Block signBlock, Block signWallBlock, Block parentBlock) {
        TextureMapping textureMap = TextureMapping.cube(parentBlock);
        ResourceLocation sign = ModelTemplates.PARTICLE_ONLY.create(signBlock, textureMap, blockStateModelGenerator.modelOutput);
        ResourceLocation wallSign = ModelTemplates.PARTICLE_ONLY.create(signWallBlock, textureMap, blockStateModelGenerator.modelOutput);
        blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(signBlock, plainVariant(sign)));
        blockStateModelGenerator.blockStateOutput.accept(createSimpleBlock(signWallBlock, plainVariant(wallSign)));
        //blockStateModelGenerator.excludeFromSimpleItemModelGeneration(signBlock);
        //blockStateModelGenerator.excludeFromSimpleItemModelGeneration(signWallBlock);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        generator.generateFlatItem(WKItems.BLACKBERRY_TEA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.CHAMOMILE_TEA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.DOGROSE_TEA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.ECHINACEA_TEA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.ELDER_TEA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.GINGER_TEA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.HAWTHORN_TEA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.ST_JOHNS_WORT_TEA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.MINT_TEA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.SUMAC_TEA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.YARROW_TEA, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(WKItems.BONE_NEEDLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.TAGLOCK, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.ENCHANTED_CHALK, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.CHALK, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(WKItems.CALEFACTION_BUNDLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.CURSE_OF_MIDAS_BUNDLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.FEAR_BUNDLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.FIELD_GEISTER_HEX_BUNDLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.HUNGRY_POCKETS_BUNDLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.INEPTITUDE_BUNDLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.MISPLACEMENT_BUNDLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.NULLARDOR_BUNDLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.PARANOIA_BUNDLE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.PERUNS_JEST_BUNDLE, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(WKItems.ABSINTHE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.BLACKBERRY_LIQUEUR, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.BRINJEVEC, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.HOLUNDERSEKT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.JUNIPER_MEAD, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.RUM, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.TRAVARICA, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.GROUND_MUTTON, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.GROUND_BEEF, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.GROUND_PORK, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.HEART_PIE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.DEMONIC_STEW, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.MEATY_STEW, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.VEGETABLE_STEW, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.ROOTS_PLATTER, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(WKItems.HEART_OF_INNOCENCE, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.BELLADONNA_BLOSSOM, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.WORMWOOD_SPRIG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.ELDER_BLOSSOM, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.CONEFLOWER_BLOSSOM, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.SANGUINARY_BLOSSOM, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.ST_JOHNS_WORT_BLOSSOM, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.IRIS_BLOSSOM, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.CHAMOMILE_BLOSSOM, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.GINGER_ROOTS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.HELLEBORE_BLOSSOM, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.FOXGLOVE_BLOSSOM, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(WKItems.AMARANTH_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.BELLADONNA_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.BRIAR_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.CHAMOMILE_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.CAMELLIA_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.CONEFLOWER_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.FOXGLOVE_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.HELLEBORE_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.IRIS_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.SANGUINARY_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.ST_JOHNS_WORT_SEEDS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.WORMWOOD_SEEDS, ModelTemplates.FLAT_ITEM);

        generator.generateFlatItem(WKItems.AMARANTH_SPRIG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.MINT_SPRIG, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.ROWAN_BERRIES, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.SLOE_BERRIES, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.JUNIPER_BERRIES, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.BLACKBERRY, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.HAWTHORN_BERRIES, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.SUMAC_BERRIES, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.BRIAR_HIPS, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(WKItems.TEA_LEAF, ModelTemplates.FLAT_ITEM);
    }

    // TODO: port
    /*public final void registerTallCrops(BlockStateModelGenerator generator, Block crop, Property<Integer> ageProperty, int... ageTextureIndices) {
        if (ageProperty.getValues().size() != ageTextureIndices.length) {
            throw new IllegalArgumentException();
        } else {
            BlockStateVariantMap blockStateVariantMap = BlockStateVariantMap.models(ageProperty, Properties.DOUBLE_BLOCK_HALF)
                    .register((integer, half) -> {
                        int i = ageTextureIndices[integer];
                        Identifier identifier = generator.createSubModel(crop, "_" + half + "_stage" + i, Models.CROP, TextureMap::crop);
                        return BlockStateVariant.create().put(VariantSettings.MODEL, identifier);
                    });
            generator.registerItemModel(crop.asItem());
            generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(crop, createWeightedVariant(new ModelVariant(ModelIds.getBlockModelId(crop), ModelVariant.ModelState.DEFAULT)))
                    .coordinate(blockStateVariantMap)
            );
        }
    }*/
}
