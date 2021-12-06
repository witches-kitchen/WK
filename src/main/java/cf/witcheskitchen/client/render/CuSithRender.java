package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.CuSithEntityModel;
import cf.witcheskitchen.common.entities.hostile.CuSithEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CuSithRender extends GeoEntityRenderer<CuSithEntity> {
    public CuSithRender(EntityRendererFactory.Context ctx) {
        super(ctx, new CuSithEntityModel());
        this.shadowRadius = 0.33f;
        this.addLayer(new CuSithEyeLayer(this));
    }
}
