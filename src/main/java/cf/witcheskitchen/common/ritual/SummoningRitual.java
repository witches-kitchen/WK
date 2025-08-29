package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SummoningRitual extends Ritual {

    @Override
    public void onEnd(Level world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        super.onEnd(world, blockPos, ritualRecipe);
        if (ritualRecipe.summons != null && !ritualRecipe.summons.isEmpty()) {
            for (EntityType<?> entityType : ritualRecipe.summons) {
                Entity entity = entityType.create(world, EntitySpawnReason.MOB_SUMMONED);
                if (entity != null) {
                    BlockPos spawnPos = blockPos.offset(world.getRandom().nextInt(2) - 1, 0, world.getRandom().nextInt(2) - 1);
                    entity.snapTo(spawnPos, 0, 0);
                    world.addFreshEntity(entity);
                }
            }
        }
    }
}
