package cf.witcheskitchen.common.block;

import cf.witcheskitchen.api.block.WKBlockWithEntity;
import cf.witcheskitchen.common.blockentity.GlyphBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GlyphBlock extends WKBlockWithEntity {
    private final IntProperty GLYPH = IntProperty.of("type", 0, 9);
    private final BooleanProperty ACTIVE = BooleanProperty.of("active");
    private final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 0.01, 1.0);

    public GlyphBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return state.get(ACTIVE) ? new GlyphBlockEntity(pos, state) : null;
    }
}
