package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.entities.hostile.CuSithEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKEntities {

    public static final EntityType<CuSithEntity> CUSITH = Registry.register(Registry.ENTITY_TYPE,
            new Identifier(WK.MODID, "cusith"), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CuSithEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0F, 1.0F)).trackRangeBlocks(0).trackedUpdateRate(0).build());

}
