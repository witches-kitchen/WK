package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.common.entities.hostile.CuSithEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class WKMobAttributes {
    public static void register() {
        FabricDefaultAttributeRegistry.register(WKEntities.CUSITH, CuSithEntity.createMobAttributes());
    }
}
