package cf.witcheskitchen.client.event;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.component.WKComponents;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WKClientEventsHandler {


    public static class MagicHudRender extends DrawableHelper implements HudRenderCallback {
        @Override
        public void onHudRender(MatrixStack matrixStack, float tickDelta) {
            final MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;
            if (player == null) return;
            WKComponents.PLAYER_COMPONENT.maybeGet(player).ifPresent(component -> {
                if (!component.isWitch()) return;

                int height = client.getWindow().getScaledHeight();
                int width = client.getWindow().getScaledWidth();
                int magic = component.getMagic();
                int magicCap = component.getMagicCap();

                matrixStack.push();
                matrixStack.translate(width / 2f - 112, height - 48, 0);
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

                RenderSystem.setShaderTexture(0, getEmptyTexture());
                drawTexture(matrixStack, 0, 0, 0, 0, 20, 42, 20, 42);

                RenderSystem.setShaderTexture(0, getBarTexture());
                int p = (magic * 42 / magicCap);
                drawTexture(matrixStack, 0, 42 - p, 0, -p, 20, p, 20, 42);
                //this.drawTexture(matrices, x, y - n, u, v - n, w, n);
                RenderSystem.depthMask(true);
                RenderSystem.disableBlend();
                matrixStack.pop();
            });
        }

        private Identifier getBarTexture() {
            return WitchesKitchen.id("textures/gui/magic/magic_bar_fill.png");
        }

        private Identifier getEmptyTexture() {
            return WitchesKitchen.id("textures/gui/magic/magic_bar_border.png");
        }
    }
}
