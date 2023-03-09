package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class TeleportRitual extends Ritual {
    @Override
    public void onEnd(World world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        super.onEnd(world, blockPos, ritualRecipe);

        BlockPos location = BlockPos.ORIGIN; //TODO get waystone location
        double strength = ritualRecipe.circleSet.size() * 2;
        List<Entity> list = world.getEntitiesByClass(Entity.class, new Box(blockPos).expand(strength), livingEntity ->
                livingEntity.squaredDistanceTo(blockPos.getX(), blockPos.getY(), blockPos.getZ()) < strength);

        for (Entity entity : list) {
            entity.teleport(location.getX(), location.getY(), location.getZ());
        }
    }
}
