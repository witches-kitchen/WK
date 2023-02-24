package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.tameable.HedgehogEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class HedgehogEntityModel extends DefaultedEntityGeoModel<HedgehogEntity> {
    public HedgehogEntityModel() {
        super(WitchesKitchen.id("hedgehog"), true);
    }

    @Override
    public Identifier getTextureResource(HedgehogEntity object) {
        return new Identifier(WitchesKitchen.MODID, "textures/entity/hedgehog_" + object.getVariant() + ".png");
    }


}
