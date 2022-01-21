package cf.witcheskitchen.api.fluid;


import javax.annotation.Nonnull;

/**
 * This interface represents a Fluid Storage. The user of this
 * Fluid <i>abstraction</i> has control over when the storage
 * can fill or drain the fluid.
 *
 * <p>The Fluid storage interface provides three getter methods to
 * access the current fluid, fluid amount and capacity.</p>
 *
 * Note: This implementation is not <b>required</b> but is provided for convenience.
 * You are free to handle fluids in your own way.
 */

public interface IFluidStorage {
    /**
     * @param stack FluidStack attempting to fill the tank.
     * @return Amount of fluid that was accepted by the tank.
     */
    int fill(FluidStack stack);

    /**
     * @param amount amount of fluid to be removed from the tank.
     * @return Amount of fluid that was removed from the tank.
     */
    @Nonnull
    FluidStack drain(int amount);

    /**
     * @param stack fluid to be removed from the container.
     * @return FluidStack representing fluid that was removed from the tank.
     */
    @Nonnull
    FluidStack drain(FluidStack stack);


    int getAmount();

    int getCapacity();

    boolean isEmpty();

}
