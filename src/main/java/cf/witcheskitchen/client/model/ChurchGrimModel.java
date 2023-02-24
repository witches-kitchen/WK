package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.neutral.ChurchGrimEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class ChurchGrimModel extends DefaultedEntityGeoModel<ChurchGrimEntity> {
    public ChurchGrimModel() {
        super(WitchesKitchen.id("churchgrim"), true);
    }

    @Override
    public Identifier getTextureResource(ChurchGrimEntity object) {
        return new Identifier(WitchesKitchen.MODID, "textures/entity/grim_" + object.getVariant() + ".png");
    }
}
