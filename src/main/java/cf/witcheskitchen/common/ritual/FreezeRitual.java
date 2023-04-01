package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class FreezeRitual extends Ritual {
    @Override
    public void tick(World world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        super.tick(world, blockPos, ritualRecipe);
        double strength = ritualRecipe.circleSet.size() * 2;
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, new Box(blockPos).expand(strength), livingEntity ->
                livingEntity.squaredDistanceTo(blockPos.getX(), blockPos.getY(), blockPos.getZ()) < strength && !(livingEntity instanceof PlayerEntity));
        for (LivingEntity entity : list) {
            entity.setVelocity(0, 0, 0);
            entity.setMovementSpeed(0);
            entity.setSprinting(false);
        }

    }
}
