package cf.witcheskitchen.common.block;

import cf.witcheskitchen.common.registry.WKDamageSources;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlackthornLeavesBlock extends LeavesBlock {

    private static final VoxelShape COLLISION_BOX = Block.createCuboidShape(1.0, 1.0, 1.0, 15.0, 15.0, 15.0);

    public BlackthornLeavesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_BOX;
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
