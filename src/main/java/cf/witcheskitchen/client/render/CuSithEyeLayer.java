package cf.witcheskitchen.client.render;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.entities.hostile.CuSithEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class CuSithEyeLayer extends GeoLayerRenderer<CuSithEntity> {
    public CuSithEyeLayer(IGeoRenderer<CuSithEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, CuSithEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        new Identifier(WK.MODID, "textures/entity/cusitheyes" + CuSithEntity.VARIANT + ".png");
    }
}
