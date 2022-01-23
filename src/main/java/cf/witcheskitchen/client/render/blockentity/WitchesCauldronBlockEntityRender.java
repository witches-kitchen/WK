package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.client.RenderHelper;
import cf.witcheskitchen.common.blocks.entity.WitchesCauldronBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class WitchesCauldronBlockEntityRender implements BlockEntityRenderer<WitchesCauldronBlockEntity> {

    @Override
    public void render(WitchesCauldronBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (!entity.getStackForTank(0).isEmpty()) {
            int color = entity.getColor();
            float depth = (float) (((entity.getPercentFilled() - 1) * (0.4D)) + (0.6D));
            matrices.push();
            matrices.translate(0, depth, 0);
            RenderHelper.renderWaterSprite(matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), color, 0.12F, light, overlay);
            matrices.pop();
        }
    }
}
