package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.FerretEntityModel;
import cf.witcheskitchen.common.entities.tameable.FerretEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FerretRender extends GeoEntityRenderer<FerretEntity> {
    public FerretRender(EntityRendererFactory.Context ctx, AnimatedGeoModel<FerretEntity> modelProvider) {
        super(ctx, new FerretEntityModel());
        this.shadowRadius = 0.33f;
    }
}
