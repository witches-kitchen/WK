package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.api.event.network.MagicSparkleParticleEvent;
import cf.witcheskitchen.api.fluid.FluidStack;
import cf.witcheskitchen.api.util.RenderHelper;
import cf.witcheskitchen.api.util.TimeHelper;
import cf.witcheskitchen.client.particle.MagicSparkleParticle;
import cf.witcheskitchen.common.blockentity.WitchesCauldronBlockEntity;
import cf.witcheskitchen.common.registry.WKParticleTypes;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class WitchesCauldronBlockEntityRender implements BlockEntityRenderer<WitchesCauldronBlockEntity> {

    @Override
    public void render(WitchesCauldronBlockEntity cauldron, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final FluidStack stack = cauldron.getFluidStack();
        if (cauldron.getWorld() != null && !stack.isEmpty()) {
            final World world = cauldron.getWorld();
            matrices.push();
            final float depth = (float) (((cauldron.getPercentFilled() - 1) * (0.4D)) + (0.6D));
            matrices.translate(0, depth, 0);
            final VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());
            final int color = cauldron.getColor();
            final float i = 0.12F;
            boolean water = stack.getFluid() != Fluids.LAVA;
            if (water) {
                RenderHelper.renderWaterSprite(matrices, buffer, color, i, light, overlay);
                float range = 0.3F;
                double offsetX = 0.5D + MathHelper.nextDouble(world.getRandom(), -range, range);
                double offsetZ = 0.5D + MathHelper.nextDouble(world.getRandom(), -range, range);
                final int heatTicks = TimeHelper.toSeconds(cauldron.getTicksHeated());
                if (heatTicks > 0) {
                    final double r = ((color >> 16) & 0xff) / 255F;
                    final double g = ((color >> 8) & 0xff) / 255F;
                    final double b = (color & 0xff) / 255F;
                    final double xPos = cauldron.getPos().getX();
                    final double yPos = cauldron.getPos().getY();
                    final double zPos = cauldron.getPos().getZ();
                    switch (heatTicks) {
                        case 1, 2, 3, 4 -> {
                            if (world.getTime() % 5 == 0) { // 5 ticks delay
                                world.addParticle((ParticleEffect) WKParticleTypes.BUBBLE, xPos + offsetX, yPos + depth, zPos + offsetZ, r, g, b);
                            }
                        }
                        case 5 ->
                                world.addParticle((ParticleEffect) WKParticleTypes.BUBBLE, xPos + offsetX, yPos + depth, zPos + offsetZ, r, g, b);
                    }
                }
            } else {
                RenderHelper.renderLavaSprite(matrices, buffer, i, light, overlay);
            }
            matrices.pop();
        }
    }

    @ClientOnly
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
            particle.setVelocity(rand.nextDouble() * 0.08D - 0.04D, rand.nextDouble() * 0.05D + 0.08D, rand.nextDouble() * 0.08D - 0.04D);
        }
    }
}
