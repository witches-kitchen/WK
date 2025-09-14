package cf.witcheskitchen.common.block;

import cf.witcheskitchen.api.block.WKBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CenserBlock extends WKBlock implements SimpleWaterloggedBlock {
    public CenserBlock(Properties settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    public @Nullable <T extends BlockEntity> GameEventListener getListener(ServerLevel level, T blockEntity) {
        return super.getListener(level, blockEntity);
    }

    @Override
    public BlockState getAppearance(BlockState state, BlockAndTintGetter renderView, BlockPos pos, Direction side, @Nullable BlockState sourceState, @Nullable BlockPos sourcePos) {
        return super.getAppearance(state, renderView, pos, side, sourceState, sourcePos);
    }

    @Override
    public boolean isEnabled(FeatureFlagSet enabledFeatures) {
        return super.isEnabled(enabledFeatures);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable LivingEntity owner, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return SimpleWaterloggedBlock.super.canPlaceLiquid(owner, level, pos, state, fluid);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidState);
    }

    @Override
    public ItemStack pickupBlock(@Nullable LivingEntity owner, LevelAccessor level, BlockPos pos, BlockState state) {
        return SimpleWaterloggedBlock.super.pickupBlock(owner, level, pos, state);
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return SimpleWaterloggedBlock.super.getPickupSound();
    }
}
