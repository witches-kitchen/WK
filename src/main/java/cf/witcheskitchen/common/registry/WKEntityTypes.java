package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.entity.hostile.CuSithEntity;
import cf.witcheskitchen.common.entity.hostile.RoggenwolfEntity;
import cf.witcheskitchen.common.entity.neutral.ChurchGrimEntity;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import cf.witcheskitchen.common.entity.tameable.HedgehogEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public interface WKEntityTypes {
    List<ObjectDefinition<EntityType<?>>> ENTITY_TYPES = new ArrayList<>();

    //monsters
    EntityType<CuSithEntity> CUSITH = create("cusith", CuSithEntity.createAttributes(), key -> EntityType.Builder.of(CuSithEntity::new, MobCategory.MONSTER)
            .sized(1.0f, 1.0f)
            .build(key));

    //animals
    EntityType<FerretEntity> FERRET = create("ferret", FerretEntity.createAttributes(), key -> EntityType.Builder.of(FerretEntity::new, MobCategory.CREATURE)
            .sized(1.0f, 1.0f)
            .build(key));
    EntityType<HedgehogEntity> HEDGEHOG = create("hedgehog", HedgehogEntity.createAttributes(), key -> EntityType.Builder.of(HedgehogEntity::new, MobCategory.CREATURE)
            .sized(0.25f, 0.25f)
            .build(key));

    EntityType<ChurchGrimEntity> CHURCH_GRIM = create("church_grim", ChurchGrimEntity.createAttributes(), key -> EntityType.Builder.of(ChurchGrimEntity::new, MobCategory.CREATURE)
            .sized(1.0f, 1.0f)
            .build(key));

    EntityType<RoggenwolfEntity> ROGGENWOLF = create("roggenwolf", RoggenwolfEntity.createAttributes(), key -> EntityType.Builder.of(RoggenwolfEntity::new, MobCategory.CREATURE)
            .sized(1.0f, 1.0f)
            .build(key));


    static List<ObjectDefinition<EntityType<?>>> getEntityTypes() {
        return Collections.unmodifiableList(ENTITY_TYPES);
    }

    static <T extends LivingEntity> EntityType<T> create(String name, AttributeSupplier.Builder attributes, Function<ResourceKey<EntityType<?>>, EntityType<T>> typeGetter) {
        var key = ResourceKey.create(Registries.ENTITY_TYPE, WitchesKitchen.id(name));
        var type = typeGetter.apply(key);
        FabricDefaultAttributeRegistry.register(type, attributes);
        ENTITY_TYPES.add(new ObjectDefinition<>(key.location(), type));
        return type;
    }

    static void init() {
        ENTITY_TYPES.forEach(entity -> Registry.register(BuiltInRegistries.ENTITY_TYPE, entity.id(), entity.object()));
    }
}
