package cf.witcheskitchen.mixin.fluid;

import cf.witcheskitchen.api.fluid.IFluidContainer;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
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
    public @NotNull
    ItemStack getEmptyStack() {
        return new ItemStack(Items.BUCKET);
    }

    @Override
    public @NotNull
    ItemStack getFullStack(Fluid fluid) {
        return new ItemStack(fluid.getBucketItem());
    }

    @Override
    public @NotNull
    Fluid getFluidType(ItemStack stack) {
        return this.fluid;
    }
}
