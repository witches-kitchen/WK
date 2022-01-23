package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.client.RenderHelper;
import cf.witcheskitchen.common.blocks.entity.WitchesCauldronBlockEntity;
import cf.witcheskitchen.common.registry.WKParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleEffect;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class WitchesCauldronBlockEntityRender implements BlockEntityRenderer<WitchesCauldronBlockEntity> {

    @Override
    public void render(WitchesCauldronBlockEntity cauldron, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (cauldron.getWorld() != null && !cauldron.getStackForTank(0).isEmpty()) {
            final Random random = cauldron.getWorld().getRandom();
            int color = cauldron.getColor();
            float depth = (float) (((cauldron.getPercentFilled() - 1) * (0.4D)) + (0.6D));
            matrices.push();
            matrices.translate(0, depth, 0);
            RenderHelper.renderWaterSprite(matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), color, 0.12F, light, overlay);
            if (cauldron.isBoiling()) {
                final double width = 0.3D;
                final double r = ((color >> 16) & 0xff) / 255F;
                final double g = ((color >> 8) & 0xff) / 255F;
                final double b = (color & 0xff) / 255F;
                double particleX = 0.2 + (random.nextDouble() * 0.6);
                double particleZ = 0.2 + (random.nextDouble() * 0.6);
                for (int i = 0; i < 3; i++) {
                    cauldron.getWorld().addParticle((ParticleEffect) WKParticleTypes.BUBBLE, cauldron.getPos().getX() + particleX, cauldron.getPos().getY() + depth, cauldron.getPos().getZ() + particleZ, r, g, b);
                }
            }
            matrices.pop();
        }
    }
}
