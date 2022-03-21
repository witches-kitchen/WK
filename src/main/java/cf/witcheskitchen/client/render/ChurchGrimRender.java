package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.ChurchGrimModel;
import cf.witcheskitchen.common.entities.neutral.ChurchGrimEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ChurchGrimRender extends GeoEntityRenderer<ChurchGrimEntity> {
    public ChurchGrimRender(EntityRendererFactory.Context ctx) {
        super(ctx, new ChurchGrimModel());
        this.shadowRadius = 0.33f;
    }
}
