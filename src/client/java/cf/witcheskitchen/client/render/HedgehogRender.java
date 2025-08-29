package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.HedgehogEntityModel;
import cf.witcheskitchen.client.render.state.WKRenderState;
import cf.witcheskitchen.common.entity.tameable.HedgehogEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class HedgehogRender<R extends WKRenderState & GeoRenderState> extends GeoEntityRenderer<HedgehogEntity, R> {
    public HedgehogRender(EntityRendererProvider.Context ctx) {
        super(ctx, new HedgehogEntityModel());
        this.shadowRadius = 0.1f;
    }
}