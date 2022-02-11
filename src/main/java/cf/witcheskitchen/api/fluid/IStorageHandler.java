package cf.witcheskitchen.api.fluid;

import net.minecraft.util.math.Direction;

import javax.annotation.Nonnull;

/**
 * <p>
 * Implement this interface for a container that should handle fluids, generally storing them in
 * </p>
 *
 * <p>
 * This is not <b>necessary</b> but it is <b>recommended</b> as an alternative
 * to implementing {@link IFluidStorage} for each of your containers.
 * </p>
 * <p>
 * Each of these methods must call the matching method from the existing {@link FluidTank} that the block entity
 * is using.
 * </p>
 * <p>
 * Note: <b>DO NOT ASSUME</b> these methods are being call somewhere, and you must
 * provide a manual implementation of each of these.
 *
 * <p>You might also want to see {@link FluidTank} and {@link FluidStack}. </p>
 */

public interface IStorageHandler {

    /**
     * Fills the Storage (which ends up modifying the internal {@link FluidStack}) of this tank,
     * from an existing {@link FluidStack}.
     * <br>
     * Must be called when you want to <b>fill</b> or <b>increment</b> the current amount of fluid of the tank.
     *
     * @param stack {@link FluidStack} attempting to fill the tank.
     * @param side  {@link Direction} from where the fluid is coming from.
     * @return the amount of fluid that was accepted (because the tank could have low free space) by the tank.
     */
    int fill(FluidStack stack, Direction side);

    /**
     * Determines whether you can fill or not your tank
     *
     * @param stack FluidStack to insert.
     * @param side  Direction where the fluid is coming from
     * @return whether you can fill it or not
     */
    boolean canFill(FluidStack stack, Direction side);

    /**
     * Returns the FluidStack from the Tank.
     *
     * <p>
     * <strong>IMPORTANT:</strong> This FluidStack <em>MUST NOT</em> be modified. This method is not for
     * altering internal contents. Any implementers who are able to detect modification via this method
     * should throw an exception. It is ENTIRELY reasonable and likely that the stack returned here will be a copy.
     * </p>
     *
     * @return The FluidStack from the Tank.
     */
    @Nonnull
    FluidStack getFluidStack();

    /**
     * Drains the Storage (which ends up modifying the internal {@link FluidStack}) of this tank,
     * from an existing {@link FluidStack}.
     * Must be called when you want to <b>drain</b> or <b>decrease</b> the current amount of fluid of the tank.
     *
     * @param stack FluidStack that has the data you want to drain of this tank
     * @param side  {@link Direction} from where the fluid is draining to.
     * @return {@link FluidStack} The FluidStack that represents the new storage of the tank.
     */
    @Nonnull
    FluidStack drain(FluidStack stack, Direction side);

    /**
     * Drains the Storage (which ends up modifying the internal {@link FluidStack}) of this tank,
     * from an existing {@link FluidStack}.
     * Must be called when you want to <b>drain</b> or <b>decrease</b> the current amount of fluid of the tank.
     *
     * @param amount The <b>maximum</b> amount of fluid that will be removed from the storage.
     * @param side   {@link Direction} from where the fluid is draining to.
     * @return {@link FluidStack} The FluidStack that represents the new storage of the tank.
     */
    @Nonnull
    FluidStack drain(int amount, Direction side);

}
