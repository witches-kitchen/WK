package cf.witcheskitchen.mixin.fluid;

import cf.witcheskitchen.api.fluid.IFluidContainer;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BottleItem.class)
public class BottleItemMixin implements IFluidContainer {

    @Override
    public int getCapacity() {
        return WKFluidAPI.BUCKET_VOLUME;
    }

    @Override
    public @NotNull
    ItemStack getEmptyStack() {
        return new ItemStack(((BottleItem) (Object) this));
    }

    @Override
    public @NotNull
    ItemStack getFullStack(Fluid fluid) {
        if (fluid == Fluids.WATER) {
            return Items.POTION.getDefaultInstance();
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
