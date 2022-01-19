package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.client.RenderHelper;
import cf.witcheskitchen.common.blocks.entity.WitchesCauldronBlockEntity;
import cf.witcheskitchen.common.blocks.technical.WitchesCauldronBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class WitchesCauldronBlockEntityRender implements BlockEntityRenderer<WitchesCauldronBlockEntity> {

    public static final float[] WATER_LEVELS = new float[] { 0.35F, 0.60F };

    @Override
    public void render(WitchesCauldronBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final int i = entity.getCachedState().get(WitchesCauldronBlock.WATER_LEVELS);
        if (i > 0) {
            matrices.push();
            matrices.translate(0, WATER_LEVELS[i - 1], 0);
            RenderHelper.renderWaterSprite(matrices, vertexConsumers.getBuffer(RenderLayer.getTranslucent()), entity.getWaterColor(), 0.12F, light, overlay);
            matrices.pop();
        }
    }
}
