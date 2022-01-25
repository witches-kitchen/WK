package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.client.RenderHelper;
import cf.witcheskitchen.common.blocks.entity.WitchesCauldronBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;

@Environment(EnvType.CLIENT)
public class WitchesCauldronBlockEntityRender implements BlockEntityRenderer<WitchesCauldronBlockEntity> {

    @Override
    public void render(WitchesCauldronBlockEntity cauldron, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (cauldron.getWorld() != null && !cauldron.getStackForTank(0).isEmpty()) {
            matrices.push();
            final float depth = (float) (((cauldron.getPercentFilled() - 1) * (0.4D)) + (0.6D));
            matrices.translate(0, depth, 0);
            final VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getTranslucent());
            final int color = cauldron.getColor();
            final float i = 0.12F;
            boolean water = cauldron.getStackForTank(0).getFluid() != Fluids.LAVA;
            if (water) {
                RenderHelper.renderWaterSprite(matrices, buffer, color, i, light, overlay);
            } else {
                RenderHelper.renderLavaSprite(matrices, buffer, i, light, overlay);
            }
            matrices.pop();
        }
    }
}
