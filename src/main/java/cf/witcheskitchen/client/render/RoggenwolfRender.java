package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.FerretEntityModel;
import cf.witcheskitchen.common.entity.hostile.RoggenwolfEntity;
import cf.witcheskitchen.common.entity.neutral.ChurchGrimEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RoggenwolfRender extends GeoEntityRenderer<RoggenwolfEntity> {
    public RoggenwolfRender(EntityRendererFactory.Context ctx) {
        super(ctx, new RoggenwolfEntityModel());
        this.shadowRadius = 0.33f;
    }
}
