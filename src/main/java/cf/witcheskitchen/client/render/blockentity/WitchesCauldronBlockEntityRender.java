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

    public static final float[] WATER_LEVELS = new float[]{0.35F, 0.60F};
    private static final int[] DEFAULT_COLOR = {0, 63, 255};

    @Override
    public void render(WitchesCauldronBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.isFilled()) {
            final double i = entity.getPercentFilled();

            int color = entity.getColor();
            if (entity.getColor() == -1) {
                color = 4159204;
            }
            matrices.push();
            matrices.translate(0, WATER_LEVELS[1], 0);
            RenderHelper.renderWaterSprite(matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), color, 0.12F, light, overlay);
            matrices.pop();
        }
    }
}
