package cf.witcheskitchen.common.block;

import cf.witcheskitchen.common.registry.WKDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class BlackthornPillarBlock extends RotatedPillarBlock {

    private static final VoxelShape X_AXIS_COLLISION_SHAPE = Block.box(1, 0, 1, 15, 16, 15);
    private static final VoxelShape Y_AXIS_COLLISION_SHAPE = Block.box(1, 0, 1, 15, 16, 15);
    private static final VoxelShape Z_AXIS_COLLISION_SHAPE = Block.box(1, 1, 0, 15, 15, 16);

    public BlackthornPillarBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case X -> X_AXIS_COLLISION_SHAPE;
            case Y -> Y_AXIS_COLLISION_SHAPE;
            case Z -> Z_AXIS_COLLISION_SHAPE;
        };
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler) {
        super.entityInside(state, world, pos, entity, handler);
        if (entity instanceof LivingEntity && world instanceof ServerLevel serverWorld) {
            entity.hurtServer(serverWorld, entity.damageSources().source(WKDamageSources.HUGGING_BLACKTHORN), 2.0F);
        }
    }

    @Override
    public void attack(BlockState state, Level world, BlockPos pos, Player player) {
        super.attack(state, world, pos, player);
        if (player.getMainHandItem().isEmpty() && world instanceof ServerLevel serverWorld) {
            player.hurtServer(serverWorld, player.damageSources().source(WKDamageSources.PUNCHING_BLACKTHORN), 2.0F);
        }
    }
}
