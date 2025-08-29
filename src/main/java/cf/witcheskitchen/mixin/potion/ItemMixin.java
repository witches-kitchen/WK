package cf.witcheskitchen.mixin.potion;

import cf.witcheskitchen.api.interfaces.AlcoholEffect;
import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "finishUsingItem", at = @At("HEAD"))
    public void onWineDrink(ItemStack stack, Level world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (!world.isClientSide() && stack.has(DataComponents.POTION_CONTENTS)) {
            final var potionEntry = stack.get(DataComponents.POTION_CONTENTS).potion();
            if (!potionEntry.isEmpty()) {
                Potion potion = potionEntry.orElseThrow().value();

                if (!(potion instanceof AlcoholEffect alcohol))
                    return;

                alcohol.onDrink(world, stack, user);
                final RandomSource random = world.getRandom();
                final int percentage = alcohol.getDrunkChance();
                final int x = Mth.nextInt(random, 0, 100);
                if (x <= percentage) {
                    user.addEffect(new MobEffectInstance(WKStatusEffects.DRUNK, alcohol.getDuration()));
                }
            }
        }
    }

    @Inject(method = "finishUsingItem", at = @At("RETURN"))
    public void onFinished(ItemStack stack, Level world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (!stack.has(DataComponents.POTION_CONTENTS))
            return;

        final var potionEntry = stack.get(DataComponents.POTION_CONTENTS).potion();

        if (potionEntry.isEmpty())
            return;

        final Potion potion = potionEntry.orElseThrow().value();
        if (potion instanceof AlcoholEffect alcohol) {
            alcohol.onFinished(world, stack, user);
        }
    }
}
