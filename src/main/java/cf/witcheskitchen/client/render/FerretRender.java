package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.model.FerretEntityModel;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FerretRender extends GeoEntityRenderer<FerretEntity> {
    public FerretRender(EntityRendererFactory.Context ctx) {
        super(ctx, new FerretEntityModel());
        this.shadowRadius = 0.33f;
    }

    @Override
    public void render(FerretEntity ferret, float entityYaw, float partialTick, MatrixStack matrices, VertexConsumerProvider bufferSource, int packedLight) {
        matrices.push();

        if(ferret.getVehicle() != null) {
            Entity vehicle = ferret.getVehicle();
            double height = vehicle.getHeight();
            double width = vehicle.getWidth();
            matrices.translate(width/2,- height / 2,width/2);
        }

        matrices.pop();
        super.render(ferret, entityYaw, partialTick, matrices, bufferSource, packedLight);
    }
}