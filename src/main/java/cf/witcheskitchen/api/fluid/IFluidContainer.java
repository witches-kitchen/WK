package cf.witcheskitchen.api.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * <p>
 * Represents an object that can store a certain amount of Fluid.
 * </p>
 * For reference look at {@link cf.witcheskitchen.mixin.fluid.BucketItemMixin}
 */
public interface IFluidContainer {

    /**
     * @return The capacity of this container
     */
    int getCapacity();

    /**
     * @return The empty stack that is returned when this fluid container is empty
     */
    @Nonnull
    ItemStack getEmptyStack();

    /**
     * @param fluid Fluid
     * @return The filled stack that is returned based on the FluidType when this fluid container is not empty
     */
    @Nonnull
    ItemStack getFullStack(Fluid fluid);

    /**
     * @param stack ItemStack container
     * @return the FluidType for the container
     */
    @Nonnull
    Fluid getFluidType(ItemStack stack);

}
