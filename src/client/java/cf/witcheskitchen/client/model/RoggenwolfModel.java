package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.hostile.RoggenwolfEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class RoggenwolfModel extends DefaultedEntityGeoModel<RoggenwolfEntity> {
    public static final DataTicket<Integer> VARIANT = DataTicket.create("variant", int.class);

    public RoggenwolfModel() {
        super(WitchesKitchen.id("roggenwolf"), true);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return WitchesKitchen.id("textures/entity/roggenwolf_" + renderState.getOrDefaultGeckolibData(VARIANT, 0) + ".png");
    }

    @Override
    public void addAdditionalStateData(RoggenwolfEntity animatable, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, renderState);
        renderState.addGeckolibData(VARIANT, animatable.getVariant());
    }
}
