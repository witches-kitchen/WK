package cf.witcheskitchen.common.items;

import cf.witcheskitchen.api.WKApi;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SaltItemBlock extends RedstoneWireBlock {
    public SaltItemBlock(BlockState defaultState, Settings settings) {
        super(settings);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityContext && entityContext.getEntity().isPresent()) {
            Entity entity = entityContext.getEntity().get();
            if (entity instanceof LivingEntity livingEntity && WKApi.isSpiritualEntity(livingEntity)) {
                return VoxelShapes.fullCube();
            }
        }
        return super.getCollisionShape(state, world, pos, context);
    }
}
