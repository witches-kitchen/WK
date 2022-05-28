package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.WKIdentifier;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.entities.hostile.CuSithEntity;
import cf.witcheskitchen.common.entities.neutral.ChurchGrimEntity;
import cf.witcheskitchen.common.entities.tameable.FerretEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WKEntityTypes {
    private static final List<ObjectDefinition<EntityType<?>>> ENTITY_TYPES = new ArrayList<>();

    //monsters
    public static final EntityType<CuSithEntity> CUSITH = create("cusith", CuSithEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CuSithEntity::new).dimensions(EntityDimensions.changing(1.0f, 1.0f)).build());

    //animals
    public static final EntityType<FerretEntity> FERRET = create("ferret", FerretEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FerretEntity::new).dimensions(EntityDimensions.changing(1.0f, 1.0f)).build());
    public static final EntityType<ChurchGrimEntity> CHURCH_GRIM = create("church_grim", ChurchGrimEntity.createAttributes(), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChurchGrimEntity::new).dimensions(EntityDimensions.changing(1.0f, 1.0f)).build());

    public static List<ObjectDefinition<EntityType<?>>> getEntityTypes() {
        return Collections.unmodifiableList(ENTITY_TYPES);
    }

    private static <T extends LivingEntity> EntityType<T> create(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        FabricDefaultAttributeRegistry.register(type, attributes);
        ENTITY_TYPES.add(new ObjectDefinition<>(new WKIdentifier(name), type));
        return type;
    }

    public static void register() {
        ENTITY_TYPES.forEach(entity -> Registry.register(Registry.ENTITY_TYPE, entity.id(), entity.object()));
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Entities: Successfully Loaded");
        }
    }

}
