package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.hostile.CuSithEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class CuSithEntityModel extends DefaultedEntityGeoModel<CuSithEntity> {
    public static final DataTicket<Integer> VARIANT = DataTicket.create("variant", int.class);

    public CuSithEntityModel() {
        super(WitchesKitchen.id("cusith"), "head");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return WitchesKitchen.id("textures/entity/cusith_" + renderState.getOrDefaultGeckolibData(VARIANT, 0) + ".png");
    }


    @Override
    public void addAdditionalStateData(CuSithEntity animatable, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, renderState);
        renderState.addGeckolibData(VARIANT, animatable.getVariant());
    }
}
