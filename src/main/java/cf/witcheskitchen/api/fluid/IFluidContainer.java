package cf.witcheskitchen.api.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface IFluidContainer {

    int getCapacity();

    @Nonnull
    ItemStack getEmpty();

    @Nonnull
    ItemStack getFilled(Fluid fluid);

    @Nonnull
    Fluid getFluid(ItemStack stack);

}
