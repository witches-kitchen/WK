package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class LightningRitual extends Ritual {
    @Override
    public void tick(Level world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        super.tick(world, blockPos, ritualRecipe);
        LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(world, EntitySpawnReason.MOB_SUMMONED);
        if (lightningEntity != null) {
            lightningEntity.snapTo(Vec3.atBottomCenterOf(blockPos));
            world.addFreshEntity(lightningEntity);
        }
    }
}
