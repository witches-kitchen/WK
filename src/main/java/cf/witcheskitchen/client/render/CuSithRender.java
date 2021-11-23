package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.CuSithEntityModel;
import cf.witcheskitchen.common.entities.hostile.CuSithEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CuSithRender extends GeoEntityRenderer<CuSithEntity> {
    public CuSithRender(EntityRendererFactory.Context ctx) {
        super(ctx, new CuSithEntityModel());
        this.shadowRadius = 0.33f;
    }

    @Override
    public RenderLayer getRenderType(CuSithEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
