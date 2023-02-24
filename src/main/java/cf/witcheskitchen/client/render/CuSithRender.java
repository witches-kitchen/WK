package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.CuSithEntityModel;
import cf.witcheskitchen.common.entity.hostile.CuSithEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CuSithRender extends GeoEntityRenderer<CuSithEntity> {
    public CuSithRender(EntityRendererFactory.Context ctx) {
        super(ctx, new CuSithEntityModel());
        this.shadowRadius = 0.33f;
        addRenderLayer(new CuSithEyeLayer(this));
    }
}
