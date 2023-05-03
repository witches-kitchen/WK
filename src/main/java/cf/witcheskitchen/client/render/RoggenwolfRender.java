package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.RoggenwolfModel;
import cf.witcheskitchen.common.entity.hostile.RoggenwolfEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RoggenwolfRender extends GeoEntityRenderer<RoggenwolfEntity> {
    public RoggenwolfRender(EntityRendererFactory.Context ctx) {
        super(ctx, new RoggenwolfModel());
        this.shadowRadius = 0.33f;
    }
}
