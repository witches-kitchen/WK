package cf.witcheskitchen.mixin.fluid;

import cf.witcheskitchen.api.fluid.IFluidContainer;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GlassBottleItem.class)
public class BottleItemMixin implements IFluidContainer {

    @Override
    public int getCapacity() {
        return WKFluidAPI.BUCKET_VOLUME;
    }

    @Override
    public @NotNull
    ItemStack getEmptyStack() {
        return new ItemStack(((GlassBottleItem) (Object) this));
    }

    @Override
    public @NotNull
    ItemStack getFullStack(Fluid fluid) {
        if (fluid == Fluids.WATER) {
            return Items.POTION.getDefaultStack();
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public @NotNull
    Fluid getFluidType(ItemStack stack) {
        return Fluids.EMPTY;
    }
}
