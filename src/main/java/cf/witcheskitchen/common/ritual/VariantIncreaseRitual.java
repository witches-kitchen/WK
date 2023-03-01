package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VariantIncreaseRitual extends Ritual {
    @Override
    public void onStart(World world, BlockPos blockPos, PlayerEntity player, RitualRecipe ritualRecipe) {
        super.onStart(world, blockPos, player, ritualRecipe);
    }
}
