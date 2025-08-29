package cf.witcheskitchen.common.block;

import cf.witcheskitchen.common.registry.WKDamageSources;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class BlackthornLeavesBlock extends LeavesBlock {

    private static final VoxelShape COLLISION_BOX = Block.box(1.0, 1.0, 1.0, 15.0, 15.0, 15.0);

    public BlackthornLeavesBlock(Properties settings) {
        super(0.4f, settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return COLLISION_BOX;
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

    @Override
    public MapCodec<? extends LeavesBlock> codec() {
        return null;
    }

    @Override
    protected void spawnFallingLeavesParticle(Level world, BlockPos pos, RandomSource random) {
        // TODO: add leaf particles
    }
}
