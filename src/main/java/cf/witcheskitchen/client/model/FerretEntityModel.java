package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class FerretEntityModel extends DefaultedEntityGeoModel<FerretEntity> {
    public FerretEntityModel() {
        super(WitchesKitchen.id("ferret"), true);
    }

    @Override
    public Identifier getTextureResource(FerretEntity object) {
        return new Identifier(WitchesKitchen.MODID, "textures/entity/ferret_" + object.getVariant() + ".png");
    }
}
