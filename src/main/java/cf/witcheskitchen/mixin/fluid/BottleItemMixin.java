package cf.witcheskitchen.mixin.fluid;

import cf.witcheskitchen.api.fluid.IFluidContainer;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GlassBottleItem.class)
public class BottleItemMixin implements IFluidContainer {

    @Override
    public int getCapacity() {
        return WKFluidAPI.BUCKET_VOLUME / 3;
    }

    @Override
    public ItemStack getEmpty() {
        return new ItemStack((((GlassBottleItem) (Object) this).getRecipeRemainder()));
    }

    @Override
    public ItemStack getFilled(Fluid fluid) {
        if (fluid == Fluids.WATER) {
            return Items.POTION.getDefaultStack();
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public Fluid getFluid(ItemStack stack) {
        return Fluids.WATER;
    }
}
