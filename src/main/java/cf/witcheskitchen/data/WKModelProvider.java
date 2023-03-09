package cf.witcheskitchen.data;

import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.*;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;

import static net.minecraft.data.client.model.BlockStateModelGenerator.*;

public class WKModelProvider extends FabricModelProvider {
    public WKModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {

        generator.registerFlowerPotPlant(WKBlocks.BLACKTHORN_SAPLING, WKBlocks.POTTED_BLACKTHORN_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerFlowerPotPlant(WKBlocks.ELDER_SAPLING, WKBlocks.POTTED_ELDER_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerFlowerPotPlant(WKBlocks.HAWTHORN_SAPLING, WKBlocks.POTTED_HAWTHORN_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerFlowerPotPlant(WKBlocks.JUNIPER_SAPLING, WKBlocks.POTTED_JUNIPER_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerFlowerPotPlant(WKBlocks.ROWAN_SAPLING, WKBlocks.POTTED_ROWAN_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerFlowerPotPlant(WKBlocks.SUMAC_SAPLING, WKBlocks.POTTED_SUMAC_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

        generator.registerLog(WKBlocks.BLACKTHORN_LOG).log(WKBlocks.BLACKTHORN_LOG).wood(WKBlocks.BLACKTHORN_WOOD);
        generator.registerLog(WKBlocks.ELDER_LOG).log(WKBlocks.ELDER_LOG).wood(WKBlocks.ELDER_WOOD);
        generator.registerLog(WKBlocks.HAWTHORN_LOG).log(WKBlocks.HAWTHORN_LOG).wood(WKBlocks.HAWTHORN_WOOD);
        generator.registerLog(WKBlocks.JUNIPER_LOG).log(WKBlocks.JUNIPER_LOG).wood(WKBlocks.JUNIPER_WOOD);
        generator.registerLog(WKBlocks.ROWAN_LOG).log(WKBlocks.ROWAN_LOG).wood(WKBlocks.ROWAN_WOOD);
        generator.registerLog(WKBlocks.SUMAC_LOG).log(WKBlocks.SUMAC_LOG).wood(WKBlocks.SUMAC_WOOD);

        generator.registerLog(WKBlocks.STRIPPED_BLACKTHORN_LOG).log(WKBlocks.STRIPPED_BLACKTHORN_LOG).wood(WKBlocks.STRIPPED_BLACKTHORN_WOOD);
        generator.registerLog(WKBlocks.STRIPPED_ELDER_LOG).log(WKBlocks.STRIPPED_ELDER_LOG).wood(WKBlocks.STRIPPED_ELDER_WOOD);
        generator.registerLog(WKBlocks.STRIPPED_HAWTHORN_LOG).log(WKBlocks.STRIPPED_HAWTHORN_LOG).wood(WKBlocks.STRIPPED_HAWTHORN_WOOD);
        generator.registerLog(WKBlocks.STRIPPED_JUNIPER_LOG).log(WKBlocks.STRIPPED_JUNIPER_LOG).wood(WKBlocks.STRIPPED_JUNIPER_WOOD);
        generator.registerLog(WKBlocks.STRIPPED_ROWAN_LOG).log(WKBlocks.STRIPPED_ROWAN_LOG).wood(WKBlocks.STRIPPED_ROWAN_WOOD);
        generator.registerLog(WKBlocks.STRIPPED_SUMAC_LOG).log(WKBlocks.STRIPPED_SUMAC_LOG).wood(WKBlocks.STRIPPED_SUMAC_WOOD);

        generator.registerSingleton(WKBlocks.BLACKTHORN_LEAVES, TexturedModel.LEAVES);
        generator.registerSingleton(WKBlocks.ELDER_LEAVES, TexturedModel.LEAVES);
        generator.registerSingleton(WKBlocks.HAWTHORN_LEAVES, TexturedModel.LEAVES);
        generator.registerSingleton(WKBlocks.JUNIPER_LEAVES, TexturedModel.LEAVES);
        generator.registerSingleton(WKBlocks.ROWAN_LEAVES, TexturedModel.LEAVES);
        generator.registerSingleton(WKBlocks.SUMAC_LEAVES, TexturedModel.LEAVES);

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

        generator.registerDoor(WKBlocks.BLACKTHORN_DOOR);
        generator.registerDoor(WKBlocks.ELDER_DOOR);
        generator.registerDoor(WKBlocks.HAWTHORN_DOOR);
        generator.registerDoor(WKBlocks.JUNIPER_DOOR);
        generator.registerDoor(WKBlocks.ROWAN_DOOR);
        generator.registerDoor(WKBlocks.SUMAC_DOOR);

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

    public void registerPressurePlate(BlockStateModelGenerator blockStateModelGenerator, Block pressureplateBlock, Block parentBlock) {
        Texture textureMap = Texture.all(parentBlock);
        Identifier pressureplate_up = Models.PRESSURE_PLATE_UP.upload(pressureplateBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier pressureplate_down = Models.PRESSURE_PLATE_DOWN.upload(pressureplateBlock, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(createPressurePlateBlockState(pressureplateBlock, pressureplate_up, pressureplate_down));
    }

    public void registerFence(BlockStateModelGenerator blockStateModelGenerator, Block fenceBlock, Block parentBlock) {
        Texture textureMap = Texture.all(parentBlock);
        Identifier post = Models.FENCE_POST.upload(fenceBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier side = Models.FENCE_SIDE.upload(fenceBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier inventory = Models.FENCE_INVENTORY.upload(fenceBlock, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(createFenceBlockState(fenceBlock, post, side));
        blockStateModelGenerator.registerParentedItemModel(fenceBlock, inventory);
    }

    public void registerFenceGate(BlockStateModelGenerator blockStateModelGenerator, Block fencegateBlock, Block parentBlock) {
        Texture textureMap = Texture.all(parentBlock);
        Identifier fence_gate = Models.TEMPLATE_FENCE_GATE.upload(fencegateBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier fence_gate_open = Models.TEMPLATE_FENCE_GATE_OPEN.upload(fencegateBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier fence_gate_wall = Models.TEMPLATE_FENCE_GATE_WALL.upload(fencegateBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier fence_gate_wall_open = Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(fencegateBlock, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(createFenceGateBlockState(fencegateBlock, fence_gate_open, fence_gate, fence_gate_wall_open, fence_gate_wall, false));
    }

    public void registerButton(BlockStateModelGenerator blockStateModelGenerator, Block buttonBlock, Block parentBlock) {
        Texture textureMap = Texture.all(parentBlock);
        Identifier button = Models.BUTTON.upload(buttonBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier pressed = Models.BUTTON_PRESSED.upload(buttonBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier inventory = Models.BUTTON_INVENTORY.upload(buttonBlock, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(createButtonBlockState(buttonBlock, button, pressed));
        blockStateModelGenerator.registerParentedItemModel(buttonBlock, inventory);
    }

    public void registerSign(BlockStateModelGenerator blockStateModelGenerator, Block signBlock, Block signWallBlock, Block parentBlock) {
        Texture textureMap = Texture.all(parentBlock);
        Identifier sign = Models.PARTICLE.upload(signBlock, textureMap, blockStateModelGenerator.modelCollector);
        Identifier wallSign = Models.PARTICLE.upload(signWallBlock, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(createSingletonBlockState(signBlock, sign));
        blockStateModelGenerator.blockStateCollector.accept(createSingletonBlockState(signWallBlock, wallSign));
        blockStateModelGenerator.excludeFromSimpleItemModelGeneration(signBlock);
        blockStateModelGenerator.excludeFromSimpleItemModelGeneration(signWallBlock);
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

        generator.register(WKItems.HEART_OF_INNOCENCE, Models.GENERATED);
        generator.register(WKItems.BELLADONNA_BLOSSOM, Models.GENERATED);
        generator.register(WKItems.WORMWOOD_SPRIG, Models.GENERATED);
        generator.register(WKItems.ELDER_BLOSSOM, Models.GENERATED);
        generator.register(WKItems.CONEFLOWER_BLOSSOM, Models.GENERATED);
        generator.register(WKItems.SANGUINARY_BLOSSOM, Models.GENERATED);
        generator.register(WKItems.ST_JOHNS_WORT_BLOSSOM, Models.GENERATED);
        generator.register(WKItems.IRIS_BLOSSOM, Models.GENERATED);
        generator.register(WKItems.CHAMOMILE_BLOSSOM, Models.GENERATED);
        generator.register(WKItems.GINGER_ROOTS, Models.GENERATED);
        generator.register(WKItems.HELLEBORE_BLOSSOM, Models.GENERATED);
        generator.register(WKItems.FOXGLOVE_BLOSSOM, Models.GENERATED);

        generator.register(WKItems.AMARANTH_SEEDS, Models.GENERATED);
        generator.register(WKItems.BELLADONNA_SEEDS, Models.GENERATED);
        generator.register(WKItems.BRIAR_SEEDS, Models.GENERATED);
        generator.register(WKItems.CHAMOMILE_SEEDS, Models.GENERATED);
        generator.register(WKItems.CAMELLIA_SEEDS, Models.GENERATED);
        generator.register(WKItems.CONEFLOWER_SEEDS, Models.GENERATED);
        generator.register(WKItems.FOXGLOVE_SEEDS, Models.GENERATED);
        generator.register(WKItems.HELLEBORE_SEEDS, Models.GENERATED);
        generator.register(WKItems.IRIS_SEEDS, Models.GENERATED);
        generator.register(WKItems.SANGUINARY_SEEDS, Models.GENERATED);
        generator.register(WKItems.ST_JOHNS_WORT_SEEDS, Models.GENERATED);
        generator.register(WKItems.WORMWOOD_SEEDS, Models.GENERATED);

        generator.register(WKItems.AMARANTH_SPRIG, Models.GENERATED);
        generator.register(WKItems.MINT_SPRIG, Models.GENERATED);
        generator.register(WKItems.ROWAN_BERRIES, Models.GENERATED);
        generator.register(WKItems.SLOE_BERRIES, Models.GENERATED);
        generator.register(WKItems.JUNIPER_BERRIES, Models.GENERATED);
        generator.register(WKItems.BLACKBERRY, Models.GENERATED);
        generator.register(WKItems.HAWTHORN_BERRIES, Models.GENERATED);
        generator.register(WKItems.SUMAC_BERRIES, Models.GENERATED);
        generator.register(WKItems.BRIAR_HIPS, Models.GENERATED);
        generator.register(WKItems.TEA_LEAF, Models.GENERATED);
    }

    public final void registerTallCrops(BlockStateModelGenerator generator, Block crop, Property<Integer> ageProperty, int... ageTextureIndices) {
        if (ageProperty.getValues().size() != ageTextureIndices.length) {
            throw new IllegalArgumentException();
        } else {
            BlockStateVariantMap blockStateVariantMap = BlockStateVariantMap.create(ageProperty, Properties.DOUBLE_BLOCK_HALF)
                    .register((integer, half) -> {
                        int i = ageTextureIndices[integer];
                        Identifier identifier = generator.createSubModel(crop, "_" + half + "_stage" + i, Models.CROP, Texture::crop);
                        return BlockStateVariant.create().put(VariantSettings.MODEL, identifier);
                    });
            generator.registerItemModel(crop.asItem());
            generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(crop, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(crop)))
                    .coordinate(blockStateVariantMap)
            );
        }
    }
}
