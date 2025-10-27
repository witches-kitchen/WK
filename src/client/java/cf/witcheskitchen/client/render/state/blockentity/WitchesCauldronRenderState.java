package cf.witcheskitchen.client.render.state.blockentity;

import cf.witcheskitchen.api.fluid.FluidStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ARGB;

public class WitchesCauldronRenderState extends BlockEntityRenderState {
    public FluidStack fluidStack;
    public int heatTicks;
    public int color;
    public float depth;
    public QuadParticleRenderState particleRenderState = new QuadParticleRenderState();
    public FluidSpriteRenderer fluidSpriteRenderer = new FluidSpriteRenderer();

    public class FluidSpriteRenderer implements SubmitNodeCollector.CustomGeometryRenderer {
        @Override
        public void render(PoseStack.Pose matrix, VertexConsumer buffer) {
            float size = 0.12f;
            int light = lightCoords;
            int overlay = OverlayTexture.NO_OVERLAY;

            var renderer = FluidRenderHandlerRegistry.INSTANCE.get(fluidStack.getFluid());
            var sprites = renderer.getFluidSprites(null, null, fluidStack.getFluid().defaultFluidState());

            if (sprites.length > 0) {
                var sprite = sprites[0];

                float maxV = (sprite.getV1() - sprite.getV0()) * size;
                float minV = (sprite.getV1() - sprite.getV0()) * (1 - size);
                final int r = ARGB.red(color);
                final int g = ARGB.green(color);
                final int b = ARGB.blue(color);
                buffer.addVertex(matrix, size, 0, 1 - size).setColor(r, g, b, 255).setUv(sprite.getU0(), sprite.getV0() + maxV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
                buffer.addVertex(matrix, 1 - size, 0, 1 - size).setColor(r, g, b, 255).setUv(sprite.getU1(), sprite.getV0() + maxV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
                buffer.addVertex(matrix, 1 - size, 0, size).setColor(r, g, b, 255).setUv(sprite.getU1(), sprite.getV0() + minV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
                buffer.addVertex(matrix, size, 0, size).setColor(r, g, b, 255).setUv(sprite.getU0(), sprite.getV0() + minV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
            }
        }
    }
}
