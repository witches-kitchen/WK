package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.FerretEntityModel;
import cf.witcheskitchen.client.render.state.WKRenderState;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class FerretRender<R extends WKRenderState & GeoRenderState> extends GeoEntityRenderer<FerretEntity, R> {
    public FerretRender(EntityRendererProvider.Context ctx) {
        super(ctx, new FerretEntityModel());
        this.shadowRadius = 0.33f;
    }
}