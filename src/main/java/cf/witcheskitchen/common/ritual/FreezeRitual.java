package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class FreezeRitual extends Ritual {
    @Override
    public void tick(Level world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        super.tick(world, blockPos, ritualRecipe);
        double strength = ritualRecipe.circleSet.size() * 2;
        List<LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, new AABB(blockPos).inflate(strength), livingEntity ->
                livingEntity.distanceToSqr(blockPos.getX(), blockPos.getY(), blockPos.getZ()) < strength && !(livingEntity instanceof Player));
        for (LivingEntity entity : list) {
            entity.setDeltaMovement(0, 0, 0);
            entity.setSpeed(0);
            entity.setSprinting(false);
        }

    }
}
