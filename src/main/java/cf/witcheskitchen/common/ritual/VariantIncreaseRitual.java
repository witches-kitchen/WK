package cf.witcheskitchen.common.ritual;

import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class VariantIncreaseRitual extends Ritual {
    @Override
    public void onStart(Level world, BlockPos blockPos, Player player, RitualRecipe ritualRecipe) {
        super.onStart(world, blockPos, player, ritualRecipe);
    }
}
