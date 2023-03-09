package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SummoningRitual extends Ritual {

    @Override
    public void onEnd(World world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        super.onEnd(world, blockPos, ritualRecipe);
        if (ritualRecipe.summons != null && !ritualRecipe.summons.isEmpty()) {
            for (EntityType<?> entityType : ritualRecipe.summons) {
                Entity entity = entityType.create(world);
                if (entity != null) {
                    BlockPos spawnPos = blockPos.add(world.getRandom().nextInt(2) - 1, 0, world.getRandom().nextInt(2) - 1);
                    entity.refreshPositionAndAngles(spawnPos, 0, 0);
                    world.spawnEntity(entity);
                }
            }
        }
    }
}
