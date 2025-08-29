package cf.witcheskitchen.api.interfaces;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface AlcoholEffect {

    /**
     * @return Determines the chance (in percentage) of getting drunk when you drink this Wine.
     */
    int getDrunkChance();

    default int getDuration() {
        return 200;
    }

    void onDrink(Level world, ItemStack wine, LivingEntity entity);

    void onFinished(Level world, ItemStack wine, LivingEntity entity);
}
