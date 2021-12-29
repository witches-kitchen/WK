package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.entities.tameable.FerretEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FerretEntityModel extends AnimatedGeoModel<FerretEntity> {
    @Override
    public Identifier getModelLocation(FerretEntity object) {
        return new Identifier(WK.MODID, "geo/ferret.geo.json");
    }

    @Override
    public Identifier getTextureLocation(FerretEntity object) {
        return new Identifier(WK.MODID, "textures/entity/ferret_" + object.getVariant() + ".png");
    }

    @Override
    public Identifier getAnimationFileLocation(FerretEntity animatable) {
        return new Identifier(WK.MODID, "animations/ferret.json");
    }
}
