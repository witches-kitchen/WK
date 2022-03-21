package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.entities.neutral.ChurchGrimEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChurchGrimModel extends AnimatedGeoModel<ChurchGrimEntity> {
    @Override
    public Identifier getModelLocation(ChurchGrimEntity object) {
        return new Identifier(WK.MODID, "geo/churchgrim.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ChurchGrimEntity object) {
        //Todo: See how well name variants are supported, then apply one of the short-haired white ones if one is named Max
        return new Identifier(WK.MODID, "textures/entity/grim_" + object.getVariant() + ".png");
    }

    @Override
    public Identifier getAnimationFileLocation(ChurchGrimEntity animatable) {
        return new Identifier(WK.MODID, "animations/churchgrim.json");
    }
}
