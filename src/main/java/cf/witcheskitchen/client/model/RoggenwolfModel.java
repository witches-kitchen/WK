package cf.witcheskitchen.client.model;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.hostile.RoggenwolfEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class RoggenwolfModel extends DefaultedEntityGeoModel<RoggenwolfEntity> {
    public RoggenwolfModel() {
        super(WitchesKitchen.id("roggenwolf"), true);
    }

    @Override
    public Identifier getTextureResource(RoggenwolfEntity object) {
        return new Identifier(WitchesKitchen.MODID, "textures/entity/roggenwolf_" + object.getVariant() + ".png");
    }
}
