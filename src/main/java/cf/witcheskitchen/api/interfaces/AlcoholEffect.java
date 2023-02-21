package cf.witcheskitchen.api.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface AlcoholEffect {

    /**
     * @return Determines the chance (in percentage) of getting drunk when you drink this Wine.
     */
    int getDrunkChance();

    default int getDuration() {
        return 200;
    }

    void onDrink(World world, ItemStack wine, LivingEntity entity);

    void onFinished(World world, ItemStack wine, LivingEntity entity);
}
