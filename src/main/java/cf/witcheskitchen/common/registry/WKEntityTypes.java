package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.entity.hostile.CuSithEntity;
import cf.witcheskitchen.common.entity.hostile.RoggenwolfEntity;
import cf.witcheskitchen.common.entity.neutral.ChurchGrimEntity;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import cf.witcheskitchen.common.entity.tameable.HedgehogEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WKEntityTypes {
    List<ObjectDefinition<EntityType<?>>> ENTITY_TYPES = new ArrayList<>();

    //monsters
    EntityType<CuSithEntity> CUSITH = create("cusith", CuSithEntity.createAttributes(), QuiltEntityTypeBuilder.create(SpawnGroup.MONSTER, CuSithEntity::new)
            .setDimensions(EntityDimensions.changing(1.0f, 1.0f))
            .build());

    //animals
    EntityType<FerretEntity> FERRET = create("ferret", FerretEntity.createAttributes(), QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, FerretEntity::new)
            .setDimensions(EntityDimensions.changing(1.0f, 1.0f))
            .build());
    EntityType<HedgehogEntity> HEDGEHOG = create("hedgehog", HedgehogEntity.createAttributes(), QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, HedgehogEntity::new)
            .setDimensions(EntityDimensions.changing(0.25f, 0.25f))
            .build());

    EntityType<ChurchGrimEntity> CHURCH_GRIM = create("church_grim", ChurchGrimEntity.createAttributes(), QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, ChurchGrimEntity::new)
            .setDimensions(EntityDimensions.changing(1.0f, 1.0f))
            .build());

    EntityType<RoggenwolfEntity> ROGGENWOLF = create("roggenwolf", RoggenwolfEntity.createAttributes(), QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, RoggenwolfEntity::new)
            .setDimensions(EntityDimensions.changing(1.0f, 1.0f))
            .build());


    static List<ObjectDefinition<EntityType<?>>> getEntityTypes() {
        return Collections.unmodifiableList(ENTITY_TYPES);
    }

    static <T extends LivingEntity> EntityType<T> create(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        //FabricDefaultAttributeRegistry.register(type, attributes);
        DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(type, attributes.build());
        ENTITY_TYPES.add(new ObjectDefinition<>(WitchesKitchen.id(name), type));
        return type;
    }

    static void init() {
        ENTITY_TYPES.forEach(entity -> Registry.register(Registries.ENTITY_TYPE, entity.id(), entity.object()));
    }
}
