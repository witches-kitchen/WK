package cf.witcheskitchen.mixin.potion;

import cf.witcheskitchen.api.interfaces.AlcoholEffect;
import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class PotionItemMixin {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    public void onWineDrink(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (!world.isClient()) {
            final Potion potion = PotionUtil.getPotion(stack);
            if (potion != Potions.EMPTY && potion instanceof AlcoholEffect alcohol) {
                alcohol.onDrink(world, stack, user);
                final RandomGenerator random = world.getRandom();
                final int percentage = alcohol.getDrunkChance();
                final int x = MathHelper.nextInt(random, 0, 100);
                if (x <= percentage) {
                    user.addStatusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, alcohol.getDuration()));
                }
            }
        }
    }

    @Inject(method = "finishUsing", at = @At("RETURN"))
    public void onFinished(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        final Potion potion = PotionUtil.getPotion(stack);
        if (potion != Potions.EMPTY && potion instanceof AlcoholEffect alcohol) {
            alcohol.onFinished(world, stack, user);
        }
    }
}
