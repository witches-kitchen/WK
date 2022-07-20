package cf.witcheskitchen.common.potion;

import cf.witcheskitchen.api.AlcoholEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class RumPotion extends Potion implements AlcoholEffect {

    public RumPotion(StatusEffectInstance... statusEffectInstances) {
        super(statusEffectInstances);
    }

    @Override
    public int getDrunkChance() {
        return 40;
    }

    @Override
    public void onDrink(World world, ItemStack wine, LivingEntity entity) {

    }

    @Override
    public void onFinished(World world, ItemStack wine, LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            player.getHungerManager().add(1, 3.0f);
        }
    }

}
