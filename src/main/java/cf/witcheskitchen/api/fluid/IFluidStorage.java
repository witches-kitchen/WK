package cf.witcheskitchen.api.fluid;


import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

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
    @NotNull
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
    @NotNull
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
    @NotNull
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
     * Writes the internal content of this tank {@link FluidStack} to a {@link CompoundTag}.
     * <br>
     * Must be written from {@link net.minecraft.world.level.block.entity.BlockEntity#saveAdditional(ValueOutput)}.
     */
    void writeStorage(@NotNull ValueOutput data);

    /**
     * Reads the internal content of this tank.
     * <br>
     * Must be read from {@link net.minecraft.world.level.block.entity.BlockEntity#loadAdditional(net.minecraft.world.level.storage.ValueInput)}
     *
     * @param data {@link CompoundTag}
     */
    void readStorage(@NotNull ValueInput data);

}
