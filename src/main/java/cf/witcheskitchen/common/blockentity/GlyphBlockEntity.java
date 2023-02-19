package cf.witcheskitchen.common.blockentity;

import cf.witcheskitchen.api.block.entity.WKBlockEntityWithInventory;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class GlyphBlockEntity extends WKBlockEntityWithInventory {
    public GlyphBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.GLYPH, pos, state, 9);
    }
}
