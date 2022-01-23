package cf.witcheskitchen.api.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

public interface IFluidContainer {

    int getCapacity();

    ItemStack getEmpty();

    ItemStack getFilled(Fluid fluid);

    Fluid getFluid(ItemStack stack);

}
