package cf.witcheskitchen.mixin.fluid;

import cf.witcheskitchen.api.fluid.IFluidContainer;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PotionItem.class)
public class PotionItemMixin implements IFluidContainer {
    @Override
    public int getCapacity() {
        return WKFluidAPI.GLASS_BOTTLE_VOLUME;
    }

    @Override
    public @NotNull
    ItemStack getEmptyStack() {
        return new ItemStack(Items.GLASS_BOTTLE);
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
        return Fluids.WATER;
    }
}
