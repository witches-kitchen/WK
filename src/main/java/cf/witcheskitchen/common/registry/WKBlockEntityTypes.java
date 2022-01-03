package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.blocks.entity.WitchesOvenBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKBlockEntityTypes {

    private static void registerBlockEntity(String id, BlockEntityType<? extends BlockEntity> blockEntity) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(WK.MODID, id), blockEntity);
    }    public static final BlockEntityType<WitchesOvenBlockEntity> WITCHES_OVEN = FabricBlockEntityTypeBuilder.create(WitchesOvenBlockEntity::new, WKBlocks.IRON_WITCHES_OVEN).build();

    public static void register() {
        registerBlockEntity("witches_oven", WITCHES_OVEN);
    }


}
