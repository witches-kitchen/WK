package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LightningRitual extends Ritual {
    @Override
    public void tick(World world, BlockPos blockPos, RitualRecipe ritualRecipe) {
        super.tick(world, blockPos, ritualRecipe);
        LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world, SpawnReason.MOB_SUMMONED);
        if (lightningEntity != null) {
            lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
            world.spawnEntity(lightningEntity);
        }
    }
}
