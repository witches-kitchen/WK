package cf.witcheskitchen.mixin.fluid;

import cf.witcheskitchen.api.fluid.IFluidContainer;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BucketItem.class)
public class BucketItemMixin implements IFluidContainer {

    @Shadow
    @Final
    private Fluid content;

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
        return new ItemStack(fluid.getBucket());
    }

    @Override
    public @NotNull
    Fluid getFluidType(ItemStack stack) {
        return this.content;
    }
}
