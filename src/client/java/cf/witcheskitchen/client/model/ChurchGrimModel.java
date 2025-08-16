package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.neutral.ChurchGrimEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ChurchGrimModel extends DefaultedEntityGeoModel<ChurchGrimEntity> {
    public static final DataTicket<Integer> VARIANT = DataTicket.create("variant", int.class);

    public ChurchGrimModel() {
        super(WitchesKitchen.id("churchgrim"), true);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return WitchesKitchen.id("textures/entity/grim_" + renderState.getOrDefaultGeckolibData(VARIANT, 0) + ".png");
    }

    @Override
    public void addAdditionalStateData(ChurchGrimEntity animatable, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, renderState);
        renderState.addGeckolibData(VARIANT, animatable.getVariant());
    }
}
