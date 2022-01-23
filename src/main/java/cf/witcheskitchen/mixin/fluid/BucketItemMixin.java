package cf.witcheskitchen.mixin.fluid;

import cf.witcheskitchen.api.fluid.IFluidContainer;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BucketItem.class)
public class BucketItemMixin implements IFluidContainer {

    @Shadow
    @Final
    private Fluid fluid;

    @Override
    public int getCapacity() {
        return WKFluidAPI.BUCKET_VOLUME;
    }

    @Override
    public ItemStack getEmpty() {
        return new ItemStack((((BucketItem) (Object) this).getRecipeRemainder()));
    }

    @Override
    public ItemStack getFilled(Fluid fluid) {
        if (fluid == Fluids.EMPTY) {
            return new ItemStack(Items.BUCKET);
        } else if (fluid == Fluids.WATER) {
            return new ItemStack(Items.WATER_BUCKET);
        } else if (fluid == Fluids.LAVA) {
            return new ItemStack(Items.LAVA_BUCKET);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public Fluid getFluid(ItemStack stack) {
        return this.fluid;
    }
}
