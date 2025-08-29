package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class FerretEntityModel extends DefaultedEntityGeoModel<FerretEntity> {
    public static final DataTicket<Integer> VARIANT = DataTicket.create("variant", int.class);

    public FerretEntityModel() {
        super(WitchesKitchen.id("ferret"), true);
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return WitchesKitchen.id("textures/entity/ferret_" + renderState.getOrDefaultGeckolibData(VARIANT, 0) + ".png");
    }

    @Override
    public void addAdditionalStateData(FerretEntity animatable, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, renderState);
        renderState.addGeckolibData(VARIANT, animatable.getVariant());
    }
}
