package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.entity.hostile.CuSithEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CuSithEntityModel extends AnimatedGeoModel<CuSithEntity> {

    @Override
    public Identifier getModelResource(CuSithEntity object) {
        return new Identifier(WK.MODID, "geo/cusith.geo.json");
    }

    @Override
    public Identifier getTextureResource(CuSithEntity object) {
        return new Identifier(WK.MODID, "textures/entity/cusith_" + object.getVariant() + ".png");
    }

    @Override
    public Identifier getAnimationResource(CuSithEntity animatable) {
        return new Identifier(WK.MODID, "animations/cusith.json");
    }
}
