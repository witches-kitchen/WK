package cf.witcheskitchen.common.blockentity;

import cf.witcheskitchen.api.block.entity.WKBlockEntity;
import cf.witcheskitchen.api.block.entity.WKBlockEntityWithInventory;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GlyphBlockEntity extends WKBlockEntityWithInventory {
    public GlyphBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.GLYPH, pos, state, 9);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void tick(World world, BlockPos blockPos, BlockState blockState, WKBlockEntity blockEntity) {
        super.tick(world, blockPos, blockState, blockEntity);
    }
}
