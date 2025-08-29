package cf.witcheskitchen.client.event;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.component.WKEntityComponents;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector4f;

public class WKClientEventsHandler {


    public static class MagicHudRender implements HudElement {
        @Override
        public void render(GuiGraphics context, DeltaTracker tickCounter) {
            final Minecraft client = Minecraft.getInstance();
            LocalPlayer player = client.player;
            if (player == null) return;
            WKEntityComponents.PLAYER_COMPONENT.maybeGet(player).ifPresent(component -> {
                if (!component.isWitch()) return;

                int height = client.getWindow().getGuiScaledHeight();
                int width = client.getWindow().getGuiScaledWidth();
                int magic = component.getMagic();
                int magicCap = component.getMagicCap();

                context.getMatrixStack().pushMatrix();
                context.getMatrixStack().translate(width / 2f - 112, height - 48);
                //RenderSystem.depthMask(false);
                //RenderSystem.enableBlend();
                //RenderSystem.defaultBlendFunc();
                RenderSystem.getDynamicUniforms().writeTransform(
                    RenderSystem.getModelViewMatrix(),
                    new Vector4f(1f, 1f, 1f, 1f),
                    RenderSystem.getModelOffset(),
                    RenderSystem.getTextureMatrix(),
                    RenderSystem.getShaderLineWidth()
                );

                context.blit(RenderPipelines.GUI_TEXTURED, getEmptyTexture(), 0, 0, 0, 0, 20, 42, 20, 42);

                int p = (magic * 42 / magicCap);
                context.blit(RenderPipelines.GUI_TEXTURED, getBarTexture(), 0, 42 - p, 0, -p, 20, p, 20, 42);
                //this.drawTexture(matrices, x, y - n, u, v - n, w, n);
                //RenderSystem.depthMask(true);
                //RenderSystem.disableBlend();
                context.getMatrixStack().popMatrix();
            });
        }

        private ResourceLocation getBarTexture() {
            return WitchesKitchen.id("textures/gui/magic/magic_bar_fill.png");
        }

        private ResourceLocation getEmptyTexture() {
            return WitchesKitchen.id("textures/gui/magic/magic_bar_border.png");
        }
    }
}
