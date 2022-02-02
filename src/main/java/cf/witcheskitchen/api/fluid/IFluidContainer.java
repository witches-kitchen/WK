package cf.witcheskitchen.api.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * <p>
 * Represents an object that can store a certain amount of Fluid.
 * </p>
 * For reference look at {@link cf.witcheskitchen.mixin.fluid.BucketItemMixin}
 */
public interface IFluidContainer {

    int getCapacity();

    @Nonnull
    ItemStack getEmpty();

    @Nonnull
    ItemStack getFilled(Fluid fluid);

    @Nonnull
    Fluid getFluid(ItemStack stack);

}
