package cf.witcheskitchen.common.block;

import cf.witcheskitchen.common.registry.WKDamageSources;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlackthornLeavesBlock extends LeavesBlock {

    private static final VoxelShape COLLISION_BOX = Block.createCuboidShape(1.0, 1.0, 1.0, 15.0, 15.0, 15.0);

    public BlackthornLeavesBlock(Settings settings) {
        super(0.4f, settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_BOX;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        super.onEntityCollision(state, world, pos, entity, handler);
        if (entity instanceof LivingEntity && world instanceof ServerWorld serverWorld) {
            entity.damage(serverWorld, entity.getDamageSources().create(WKDamageSources.HUGGING_BLACKTHORN), 2.0F);
        }
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        super.onBlockBreakStart(state, world, pos, player);
        if (player.getMainHandStack().isEmpty() && world instanceof ServerWorld serverWorld) {
            player.damage(serverWorld, player.getDamageSources().create(WKDamageSources.PUNCHING_BLACKTHORN), 2.0F);
        }
    }

    @Override
    public MapCodec<? extends LeavesBlock> getCodec() {
        return null;
    }

    @Override
    protected void spawnLeafParticle(World world, BlockPos pos, Random random) {
        // TODO: add leaf particles
    }
}
