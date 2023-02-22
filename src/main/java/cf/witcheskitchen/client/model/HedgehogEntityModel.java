package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.tameable.HedgehogEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HedgehogEntityModel extends AnimatedGeoModel<HedgehogEntity> {

    @Override
    public Identifier getModelResource(HedgehogEntity object) {
        return new Identifier(WitchesKitchen.MODID, "geo/hedgehog.geo.json");
    }

    @Override
    public Identifier getTextureResource(HedgehogEntity object) {
        return new Identifier(WitchesKitchen.MODID, "textures/entity/hedgehog_" + object.getVariant() + ".png");
    }

    @Override
    public Identifier getAnimationResource(HedgehogEntity animatable) {
        return new Identifier(WitchesKitchen.MODID, "animations/hedgehog.json");
    }
}
