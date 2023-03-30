package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import cf.witcheskitchen.common.registry.WKStatusEffects;
import cf.witcheskitchen.common.statuseffect.ParalysisStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

import static cf.witcheskitchen.common.registry.WKStatusEffects.PARALYSIS;

public class FreezeRitual extends Ritual {
    @Override
    public void tick(World world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        super.tick(world, blockPos, ritualRecipe);
        double strength = ritualRecipe.circleSet.size() * 2;
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, new Box(blockPos).expand(strength), livingEntity ->
                livingEntity.squaredDistanceTo(blockPos.getX(), blockPos.getY(), blockPos.getZ()) < strength && !(livingEntity instanceof PlayerEntity));
        for (LivingEntity entity : list) {
            entity.setVelocity(0, 0, 0);
        }

    }
}
