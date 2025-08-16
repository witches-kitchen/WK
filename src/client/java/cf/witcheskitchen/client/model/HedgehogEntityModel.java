package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.tameable.HedgehogEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class HedgehogEntityModel extends DefaultedEntityGeoModel<HedgehogEntity> {
    public static final DataTicket<Integer> VARIANT = DataTicket.create("variant", int.class);

    public HedgehogEntityModel() {
        super(WitchesKitchen.id("hedgehog"), true);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return WitchesKitchen.id("textures/entity/hedgehog_" + renderState.getOrDefaultGeckolibData(VARIANT, 0) + ".png");
    }

    @Override
    public void addAdditionalStateData(HedgehogEntity animatable, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, renderState);
        renderState.addGeckolibData(VARIANT, animatable.getVariant());
    }
}
