package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.HedgehogEntityModel;
import cf.witcheskitchen.common.entity.tameable.HedgehogEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HedgehogRender extends GeoEntityRenderer<HedgehogEntity> {
    public HedgehogRender(EntityRendererFactory.Context ctx) {
        super(ctx, new HedgehogEntityModel());
        this.shadowRadius = 0.1f;
    }
}