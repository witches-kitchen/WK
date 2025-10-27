package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.CuSithEntityModel;
import cf.witcheskitchen.client.render.state.WKRenderState;
import cf.witcheskitchen.common.entity.hostile.CuSithEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class CuSithRender<R extends WKRenderState & GeoRenderState> extends GeoEntityRenderer<CuSithEntity, R> {
    public CuSithRender(EntityRendererProvider.Context ctx) {
        super(ctx, new CuSithEntityModel());
        this.shadowRadius = 0.33f;
        this.withRenderLayer(new CuSithEyeLayer<>(this));
    }
}
