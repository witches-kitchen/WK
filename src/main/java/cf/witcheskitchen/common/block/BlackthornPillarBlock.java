package cf.witcheskitchen.common.block;

import cf.witcheskitchen.common.registry.WKDamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlackthornPillarBlock extends PillarBlock {

    private static final VoxelShape X_AXIS_COLLISION_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);
    private static final VoxelShape Y_AXIS_COLLISION_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);
    private static final VoxelShape Z_AXIS_COLLISION_SHAPE = Block.createCuboidShape(1, 1, 0, 15, 15, 16);

    public BlackthornPillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AXIS)) {
            case X -> X_AXIS_COLLISION_SHAPE;
            case Y -> Y_AXIS_COLLISION_SHAPE;
            case Z -> Z_AXIS_COLLISION_SHAPE;
        };
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (entity instanceof LivingEntity) {
            entity.damage(WKDamageSources.HUGGING_BLACKTHORN, 2.0F);
        }
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        super.onBlockBreakStart(state, world, pos, player);
        if (player.getMainHandStack().isEmpty()) {
            player.damage(WKDamageSources.PUNCHING_BLACKTHORN, 2.0F);
        }
    }
}
