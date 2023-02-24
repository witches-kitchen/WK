package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.hostile.CuSithEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class CuSithEntityModel extends DefaultedEntityGeoModel<CuSithEntity> {
    public CuSithEntityModel() {
        super(WitchesKitchen.id("cusith"), true);
    }

    @Override
    public Identifier getTextureResource(CuSithEntity object) {
        return new Identifier(WitchesKitchen.MODID, "textures/entity/cusith_" + object.getVariant() + ".png");
    }


}
