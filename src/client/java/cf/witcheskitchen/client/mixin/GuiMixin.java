package cf.witcheskitchen.client.mixin;

import cf.witcheskitchen.api.util.SeedTypeHelper;
import cf.witcheskitchen.common.item.VariantSeedItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow
    private ItemStack lastToolHighlight;
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    public abstract Font getFont();

    @Inject(method = "renderSelectedItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getFont()Lnet/minecraft/client/gui/Font;", ordinal = 1))
    private void wk$renderTypeColor(GuiGraphics context, CallbackInfo ci, @Local(ordinal = 1) int x, @Local(ordinal = 2) int y, @Local(ordinal = 3) int alpha) {
        if (this.lastToolHighlight.getItem() instanceof VariantSeedItem) {
            MutableComponent text = SeedTypeHelper.getSeedTypeText(this.lastToolHighlight);
            if (text != null) {
                int io = this.getFont().width(text);
                int xo = (this.minecraft.getWindow().getGuiScaledWidth() - io) / 2;
                context.drawString(this.getFont(), text, xo, y + 9, 16777215 + (alpha << 24));
            }
        }
    }
}
