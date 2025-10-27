package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.api.event.network.MagicSparkleParticleEvent;
import cf.witcheskitchen.api.fluid.FluidStack;
import cf.witcheskitchen.api.util.TimeHelper;
import cf.witcheskitchen.client.particle.BubbleParticle;
import cf.witcheskitchen.client.particle.MagicSparkleParticle;
import cf.witcheskitchen.client.render.state.blockentity.WitchesCauldronRenderState;
import cf.witcheskitchen.common.blockentity.WitchesCauldronBlockEntity;
import cf.witcheskitchen.common.registry.WKParticleTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class WitchesCauldronBlockEntityRender implements BlockEntityRenderer<WitchesCauldronBlockEntity, WitchesCauldronRenderState> {

    @Override
    public WitchesCauldronRenderState createRenderState() {
        return new WitchesCauldronRenderState();
    }

    @Override
    public void extractRenderState(WitchesCauldronBlockEntity cauldron, WitchesCauldronRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(cauldron, renderState, partialTick, cameraPosition, breakProgress);

        renderState.fluidStack = cauldron.getFluidStack();
        renderState.color = cauldron.getColor();
        renderState.depth = (float) (((cauldron.getPercentFilled() - 1) * (0.4D)) + (0.6D));
        renderState.heatTicks = cauldron.getTicksHeated();
        renderState.particleRenderState.clear();

        Level world = cauldron.getLevel();

        if (cauldron.getFluidStack().getFluid().is(FluidTags.WATER)) {
            float range = 0.3F;
            double offsetX = 0.5D + Mth.nextDouble(world.getRandom(), -range, range);
            double offsetZ = 0.5D + Mth.nextDouble(world.getRandom(), -range, range);
            final int heatTicks = TimeHelper.toSeconds(cauldron.getTicksHeated());
            if (heatTicks > 0) {
                final float r = ((renderState.color >> 16) & 0xff) / 255F;
                final float g = ((renderState.color >> 8) & 0xff) / 255F;
                final float b = (renderState.color & 0xff) / 255F;
                final double xPos = cauldron.getBlockPos().getX();
                final double yPos = cauldron.getBlockPos().getY();
                final double zPos = cauldron.getBlockPos().getZ();
                switch (heatTicks) {
                    case 1, 2, 3, 4 -> {
                        if (world.getGameTime() % 5 == 0) { // 5 ticks delay
                            var particle = (BubbleParticle) Minecraft.getInstance().particleEngine.createParticle((ParticleOptions) WKParticleTypes.BUBBLE, xPos + offsetX, yPos + renderState.depth, zPos + offsetZ, 0.2, 0.2, 0.2);
                            particle.setColor(r, g, b);
                            particle.extract(renderState.particleRenderState, Minecraft.getInstance().getEntityRenderDispatcher().camera, partialTick);
                        }
                    }
                    case 5 -> {
                        var particle = (BubbleParticle) Minecraft.getInstance().particleEngine.createParticle((ParticleOptions) WKParticleTypes.BUBBLE, xPos + offsetX, yPos + renderState.depth, zPos + offsetZ, 0.2, 0.2, 0.2);
                        particle.setColor(r, g, b);
                        particle.extract(renderState.particleRenderState, Minecraft.getInstance().getEntityRenderDispatcher().camera, partialTick);
                    }
                }
            }
        }
    }

    @Override
    public void submit(WitchesCauldronRenderState renderState, PoseStack matrices, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        final FluidStack stack = renderState.fluidStack;

        if (!stack.isEmpty()) {
            matrices.pushPose();
            final float depth = renderState.depth;
            matrices.translate(0, depth, 0);

            if (stack.hasFluid(Fluids.WATER)) {
                renderState.particleRenderState.submit(nodeCollector, cameraRenderState);
            }

            nodeCollector.submitCustomGeometry(matrices, RenderType.translucentMovingBlock(), renderState.fluidSpriteRenderer);
            matrices.popPose();
        }
    }

    @Environment(EnvType.CLIENT)
    public static class MagicalParticleEventHandler implements MagicSparkleParticleEvent.ParticleConstructorCallback {

        @Override
        public void onConstructor(MagicSparkleParticle particle) {
            final var rand = particle.getRandom();
            final float shift = 0.1F;
            final float doubleColorShift = shift;
            final float redShift = rand.nextFloat() * doubleColorShift - shift;
            final float greenShift = rand.nextFloat() * doubleColorShift - shift;
            final float blueShift = rand.nextFloat() * doubleColorShift - shift;
            particle.setColor(particle.getRed() + redShift, particle.getGreen() + greenShift, particle.getBlue() + blueShift);
            particle.setGravity(0.25F);
            particle.setParticleSpeed(rand.nextDouble() * 0.08D - 0.04D, rand.nextDouble() * 0.05D + 0.08D, rand.nextDouble() * 0.08D - 0.04D);
        }
    }
}
