package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.FerretEntityModel;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FerretRender extends GeoEntityRenderer<FerretEntity> {
    public FerretRender(EntityRendererFactory.Context ctx) {
        super(ctx, new FerretEntityModel());
        this.shadowRadius = 0.33f;
    }
}