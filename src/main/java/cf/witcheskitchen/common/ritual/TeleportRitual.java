package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class TeleportRitual extends Ritual {
    @Override
    public void onEnd(Level world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        super.onEnd(world, blockPos, ritualRecipe);

        BlockPos location = BlockPos.ZERO; //TODO get waystone location
        double strength = ritualRecipe.circleSet.size() * 2;
        List<Entity> list = world.getEntitiesOfClass(Entity.class, new AABB(blockPos).inflate(strength), livingEntity ->
                livingEntity.distanceToSqr(blockPos.getX(), blockPos.getY(), blockPos.getZ()) < strength);

        for (Entity entity : list) {
            entity.teleportTo(location.getX(), location.getY(), location.getZ());
        }
    }
}
