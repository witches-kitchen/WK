package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.RoggenwolfModel;
import cf.witcheskitchen.client.render.state.WKRenderState;
import cf.witcheskitchen.common.entity.hostile.RoggenwolfEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class RoggenwolfRender<R extends WKRenderState & GeoRenderState> extends GeoEntityRenderer<RoggenwolfEntity, R> {
    public RoggenwolfRender(EntityRendererProvider.Context ctx) {
        super(ctx, new RoggenwolfModel());
        this.shadowRadius = 0.33f;
    }
}
