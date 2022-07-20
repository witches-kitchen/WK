package cf.witcheskitchen.api.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

public class WKFluidAPI {

    /**
     * <p>
     * These are Items that are being implemented as {@link IFluidContainer}
     * by WitchesKitchen.
     * </p>
     * Default capacity is defined here.
     */
    public static final int BUCKET_VOLUME = 1000;
    public static final int GLASS_BOTTLE_VOLUME = 250;

    /**
     * Finds the amount of fluid in Mb (MilliBuckets) of an {@link IFluidContainer}
     * or zero if it couldn't find it.
     *
     * @param container ItemStack
     * @return the amount of fluid
     */
    public static int getFluidAmountFor(ItemStack container) {
        if (container.getItem() instanceof IFluidContainer) {
            return ((IFluidContainer) container.getItem()).getCapacity();
        }
        return 0;
    }

    /**
     * <p>
     * Finds the matching <b>FILLED</b> {@link ItemStack} of the {@link IFluidContainer}
     * or {@link FluidStack#EMPTY} if it is <b>NOT</b> an {@link IFluidContainer}.
     * </p>
     * <p>
     * <b>NOTE: </b>The reason you need to specify a {@link Fluid} type, is because
     * it is possible for an {@link IFluidContainer} to have more than one
     * matching {@link ItemStack} depending on the fluid.
     * </p>
     * (For example, a bucket can be filled with water or lava).
     *
     * @param fluid          Fluid
     * @param fluidContainer ItemStack
     * @return ItemStack the matching stack
     */
    public static ItemStack getMatchingStackFor(Fluid fluid, ItemStack fluidContainer) {
        if (fluidContainer.getItem() instanceof IFluidContainer handler) {
            return handler.getFullStack(fluid);
        }
        return ItemStack.EMPTY;
    }

    /**
     * Creates a new {@link FluidStack} according to the {@link IFluidContainer}
     * implementation, or {@link FluidStack#EMPTY} if it is <b>NOT</b> an {@link IFluidContainer}.
     *
     * @param container ItemStack that can <i>possibly</i> be a fluid container.
     * @return {@link FluidStack}
     */
    public static FluidStack getStackFor(ItemStack container) {
        if (container.getItem() instanceof IFluidContainer fluidContainer) {
            return new FluidStack(fluidContainer.getFluidType(container), fluidContainer.getCapacity());
        }
        return FluidStack.EMPTY;
    }
}
