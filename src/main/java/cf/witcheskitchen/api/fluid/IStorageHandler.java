package cf.witcheskitchen.api.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.Direction;

/**
 * Implement this interface on BlockEntities that should handle fluids, generally storing them in
 * one or more internal {@link IFluidStorage} objects.
 */
public interface IStorageHandler {

    /**
     * Fills fluid into internal tanks, distribution is left entirely to the IFluidStorage.
     * @param side Direction where the Fluid is pumped in from.
     * @param stack FluidStack representing the Fluid and maximum amount of fluid to be filled.
     * @return whether the fluid stack was successfully inserted into the tank.
     */
    boolean fill(Direction side, FluidStack stack);

    /**
     * Drains fluid out of internal tanks, distribution is left entirely to the IFluidHandler.
     *
     * @param side Direction the Fluid is drained to.
     * @param stack FluidStack representing the Fluid and maximum amount of fluid to be drained.
     * @return FluidStack representing the Fluid and amount that was drained.
     */
    FluidStack drain(Direction side, FluidStack stack);

    /**
     * Drains fluid out of internal tanks, distribution is left entirely to the IFluidStorage.
     *
     * @param side Direction the fluid is drained to.
     * @param amount Amount of fluid to drain.
     * @return FluidStack representing the Fluid and amount that was drained.
     */
    FluidStack drain(Direction side, int amount, boolean doDrain);

    /**
     * Returns true if the given fluid can be inserted into the given direction.
     *
     * More formally, this should return true if fluid is able to enter from the given direction.
     */
    boolean canFill(Direction side, Fluid fluid);

    /**
     * Returns true if the given fluid can be extracted from the given direction.
     *
     * More formally, this should return true if fluid is able to leave from the given direction.
     */
    boolean canDrain(Direction side, Fluid fluid);

    /**
     * Returns the number of fluid storage units ("tanks") available
     *
     * @return The number of tanks available
     */
    int getTanks();
}
