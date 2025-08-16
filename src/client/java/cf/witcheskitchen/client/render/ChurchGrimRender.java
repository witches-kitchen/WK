package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.ChurchGrimModel;
import cf.witcheskitchen.client.render.state.WKRenderState;
import cf.witcheskitchen.common.entity.neutral.ChurchGrimEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ChurchGrimRender<R extends WKRenderState & GeoRenderState> extends GeoEntityRenderer<ChurchGrimEntity, R> {
    public ChurchGrimRender(EntityRendererFactory.Context ctx) {
        super(ctx, new ChurchGrimModel());
        this.shadowRadius = 0.33f;
    }
}
