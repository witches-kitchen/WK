package cf.witcheskitchen.common.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GrowthStatusEffect extends MobEffect {
    public GrowthStatusEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        if (entity.hasEffect(WKStatusEffects.COOLDOWN)) {
            return false;
        }
        int radius = amplifier + 1;
        BlockPos initialPosition = entity.blockPosition();
        for (BlockPos position : BlockPos.betweenClosed(initialPosition.offset(-radius, -radius, -radius), initialPosition.offset(radius, radius, radius))) {
            BlockState blockState = entity.level().getBlockState(position);
            {
                if (blockState.getBlock() instanceof BonemealableBlock fertilizable) {
                    if (fertilizable.isValidBonemealTarget(world, position, entity.level().getBlockState(position))) {
                        if (fertilizable.isBonemealSuccess(world, world.random, position, entity.level().getBlockState(position))) {
                            BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), entity.level(), position);
                            BoneMealItem.growWaterPlant(new ItemStack(Items.BONE_MEAL), entity.level(), position, null);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void onMobRemoved(ServerLevel world, LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
        entity.addEffect(new MobEffectInstance(WKStatusEffects.COOLDOWN, 6000, 0));
    }
}
