package cf.witcheskitchen.client.event;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.component.WKEntityComponents;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import org.joml.Vector4f;

public class WKClientEventsHandler {


    public static class MagicHudRender implements HudElement {
        @Override
        public void render(DrawContext context, RenderTickCounter tickCounter) {
            final MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;
            if (player == null) return;
            WKEntityComponents.PLAYER_COMPONENT.maybeGet(player).ifPresent(component -> {
                if (!component.isWitch()) return;

                int height = client.getWindow().getScaledHeight();
                int width = client.getWindow().getScaledWidth();
                int magic = component.getMagic();
                int magicCap = component.getMagicCap();

                context.getMatrices().pushMatrix();
                context.getMatrices().translate(width / 2f - 112, height - 48);
                //RenderSystem.depthMask(false);
                //RenderSystem.enableBlend();
                //RenderSystem.defaultBlendFunc();
                RenderSystem.getDynamicUniforms().write(
                        RenderSystem.getModelViewMatrix(),
                        new Vector4f(1f, 1f, 1f, 1f),
                        RenderSystem.getModelOffset(),
                        RenderSystem.getTextureMatrix(),
                        RenderSystem.getShaderLineWidth()
                );

                context.drawTexture(RenderPipelines.GUI_TEXTURED, getEmptyTexture(), 0, 0, 0, 0, 20, 42, 20, 42);

                int p = (magic * 42 / magicCap);
                context.drawTexture(RenderPipelines.GUI_TEXTURED, getBarTexture(), 0, 42 - p, 0, -p, 20, p, 20, 42);
                //this.drawTexture(matrices, x, y - n, u, v - n, w, n);
                //RenderSystem.depthMask(true);
                //RenderSystem.disableBlend();
                context.getMatrices().popMatrix();
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
