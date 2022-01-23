package cf.witcheskitchen.api.fluid;

import net.minecraft.util.math.Direction;

import javax.annotation.Nonnull;

/**
 * Implement this interface for an object which should handle fluids, generally storing them in
 * one or more internal {@link FluidTank} objects.
 */
//TODO: document this in detail
public interface IStorageHandler {


    int fill(FluidStack stack, Direction side);


    boolean canFill(FluidStack stack, Direction side);

    /**
     * Returns the FluidStack in a given tank.
     *
     * <p>
     * <strong>IMPORTANT:</strong> This FluidStack <em>MUST NOT</em> be modified. This method is not for
     * altering internal contents. Any implementers who are able to detect modification via this method
     * should throw an exception. It is ENTIRELY reasonable and likely that the stack returned here will be a copy.
     * </p>
     *
     * <p>
     * <strong><em>SERIOUSLY: DO NOT MODIFY THE RETURNED FLUIDSTACK</em></strong>
     * </p>
     *
     * @param tank Tank to query.
     * @return FluidStack in a given tank. FluidStack.EMPTY if the tank is empty.
     */
    @Nonnull
    FluidStack getStackForTank(int tank);



    @Nonnull
    FluidStack drain(FluidStack stack, Direction side);


    @Nonnull
    FluidStack drain(int maxAmount, Direction side);

}
