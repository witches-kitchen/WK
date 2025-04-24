package cf.witcheskitchen.mixin.potion;

import cf.witcheskitchen.api.interfaces.AlcoholEffect;
import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    public void onWineDrink(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (!world.isClient() && stack.contains(DataComponentTypes.POTION_CONTENTS)) {
            final var potionEntry = stack.get(DataComponentTypes.POTION_CONTENTS).potion();
            if (!potionEntry.isEmpty()) {
                Potion potion = potionEntry.orElseThrow().value();

                if (!(potion instanceof AlcoholEffect alcohol))
                    return;

                alcohol.onDrink(world, stack, user);
                final Random random = world.getRandom();
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
        if (!stack.contains(DataComponentTypes.POTION_CONTENTS))
            return;

        final var potionEntry = stack.get(DataComponentTypes.POTION_CONTENTS).potion();

        if (potionEntry.isEmpty())
            return;

        final Potion potion = potionEntry.orElseThrow().value();
        if (potion instanceof AlcoholEffect alcohol) {
            alcohol.onFinished(world, stack, user);
        }
    }
}
