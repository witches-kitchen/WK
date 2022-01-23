package cf.witcheskitchen.api.fluid;


import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * <p>
 * This interface represents a Fluid Storage. The user of this
 * fluid storage <i>abstraction</i> has control over the interactions that a
 * fluid tank makes when the storage has to be filled or drained,
 * which internally encapsulated by a {@link FluidStack}.
 * </p>
 *
 * <p>
 * The Fluid storage interface provides a contract that <b>MUST BE IMPLEMENTED MANUALLY</b>
 * without default or utility static functions.
 * For reference, you can look at {@link FluidTank} implementation of WitchesKitchen.
 * </p>
 *
 * <p>
 * Note: This implementation is not <<b>required</b> but is provided for convenience.
 * You are free to handle fluids in your own way. (Check out {@link IStorageHandler} for more info),
 * and <b>DO NOT ASSUME</b> that these methods are being called somewhere.
 * </p>
 */

public interface IFluidStorage {

    /**
     * A FluidStack represents the internal fluid of a Fluid Storage and some other data.
     * <br>
     * Check out {@link FluidStack}.
     *
     * @return {@link FluidTank} that represents the <i>internal storage</i> of the tank.
     */
    @Nonnull
    FluidStack getStack();

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
     * @return The internal amount of fluid of this tank.
     */
    int getFluidAmount();

    /**
     * The <b>MAXIMUM</b> capacity (in MilliBuckets) this instance of Tank can store.
     * <br>
     * It is recommended to make this value immutable or final.
     *
     * @return The capacity of this tank.
     */
    int getCapacity();

    /**
     * @return Whether this tank is considered as empty
     */
    boolean isEmpty();

    /**
     * Writes the internal content of this tank {@link FluidStack} to a {@link NbtCompound}.
     * <br>
     * Must be written from {@link net.minecraft.block.entity.BlockEntity#writeNbt(NbtCompound)}.
     *
     * @return {@link NbtCompound} that contains the data of the {@link FluidStack} of the tank
     */
    NbtCompound writeStorage();

    /**
     * Reads the internal content of this tank.
     * <br>
     * Must be read from {@link net.minecraft.block.entity.BlockEntity#readNbt(NbtCompound)}
     *
     * @param data {@link NbtCompound}
     */
    void readStorage(@NotNull NbtCompound data);

}
