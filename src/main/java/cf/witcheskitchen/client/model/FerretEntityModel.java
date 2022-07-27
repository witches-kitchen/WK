package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FerretEntityModel extends AnimatedGeoModel<FerretEntity> {

    @Override
    public Identifier getModelResource(FerretEntity object) {
        return new Identifier(WK.MODID, "geo/ferret.geo.json");
    }

    @Override
    public Identifier getTextureResource(FerretEntity object) {
        return new Identifier(WK.MODID, "textures/entity/ferret_" + object.getVariant() + ".png");
    }

    @Override
    public Identifier getAnimationResource(FerretEntity animatable) {
        return new Identifier(WK.MODID, "animations/ferret.json");
    }
}
