package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.CuSithEntityModel;
import cf.witcheskitchen.client.render.state.WKRenderState;
import cf.witcheskitchen.common.entity.hostile.CuSithEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class CuSithRender<R extends WKRenderState & GeoRenderState> extends GeoEntityRenderer<CuSithEntity, R> {
    public CuSithRender(EntityRendererFactory.Context ctx) {
        super(ctx, new CuSithEntityModel());
        this.shadowRadius = 0.33f;
        addRenderLayer(new CuSithEyeLayer<>(this));
    }
}
