package cf.witcheskitchen.api.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

public class WKFluidAPI {

    // reference for bucket volume
    public static final int BUCKET_VOLUME = 1000;
    public static final int GLASS_BOTTLE_VOLUME = 250;

    public static ItemStack getMatchingFilledStack(Fluid fluid, ItemStack container) {
        if (container.getItem() instanceof IFluidContainer handler) {
            return handler.getFilled(fluid);
        }
        return ItemStack.EMPTY;
    }

    public static int getFluidAmountFor(ItemStack container) {
        if (container.getItem() instanceof IFluidContainer) {
            return ((IFluidContainer) container.getItem()).getCapacity();
        }
        return 0;
    }

    public static FluidStack getFluidStackFor(ItemStack container) {
        if (container.getItem() instanceof IFluidContainer fluidContainer) {
            return new FluidStack(fluidContainer.getFluid(container), fluidContainer.getCapacity());
        }
        return FluidStack.EMPTY;
    }
}
