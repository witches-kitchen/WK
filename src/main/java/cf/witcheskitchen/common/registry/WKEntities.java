package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.entities.hostile.CuSithEntity;
import cf.witcheskitchen.common.entities.tameable.FerretEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class WKEntities {

    private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

    //monsters
    public static final EntityType<CuSithEntity> CUSITH = create("cusith", CuSithEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CuSithEntity::new).dimensions(EntityDimensions.changing(1.0f, 1.0f)).build());

    //animals
    public static final EntityType<FerretEntity> FERRET = create("ferret", FerretEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FerretEntity::new).dimensions(EntityDimensions.changing(1.0f, 1.0f)).build());

    private static <T extends LivingEntity> EntityType<T> create(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        FabricDefaultAttributeRegistry.register(type, attributes);
        ENTITY_TYPES.put(type, new Identifier(WK.MODID, name));
        return type;
    }

    private static <T extends Entity> EntityType<T> create(String name, EntityType<T> type) {
        ENTITY_TYPES.put(type, new Identifier(WK.MODID, name));
        return type;
    }

    public static void register() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
    }

}
