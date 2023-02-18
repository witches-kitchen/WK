package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.WitchesKitchenConfig;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.entity.hostile.CuSithEntity;
import cf.witcheskitchen.common.entity.neutral.ChurchGrimEntity;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WKEntityTypes {
    private static final List<ObjectDefinition<EntityType<?>>> ENTITY_TYPES = new ArrayList<>();

    //monsters
    public static final EntityType<CuSithEntity> CUSITH = create("cusith", CuSithEntity.createAttributes(), QuiltEntityTypeBuilder.create(SpawnGroup.MONSTER, CuSithEntity::new)
            .setDimensions(EntityDimensions.changing(1.0f, 1.0f))
            .build());

    //animals
    public static final EntityType<FerretEntity> FERRET = create("ferret", FerretEntity.createAttributes(), QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, FerretEntity::new)
            .setDimensions(EntityDimensions.changing(1.0f, 1.0f))
            .build());
    public static final EntityType<ChurchGrimEntity> CHURCH_GRIM = create("church_grim", ChurchGrimEntity.createAttributes(), QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, ChurchGrimEntity::new)
            .setDimensions(EntityDimensions.changing(1.0f, 1.0f))
            .build());

    public static List<ObjectDefinition<EntityType<?>>> getEntityTypes() {
        return Collections.unmodifiableList(ENTITY_TYPES);
    }

    private static <T extends LivingEntity> EntityType<T> create(String name, DefaultAttributeContainer.Builder attributes, EntityType<T> type) {
        //FabricDefaultAttributeRegistry.register(type, attributes);
        DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(type, attributes.build());
        ENTITY_TYPES.add(new ObjectDefinition<>(WitchesKitchen.id(name), type));
        return type;
    }

    public static void register() {
        ENTITY_TYPES.forEach(entity -> Registry.register(Registry.ENTITY_TYPE, entity.id(), entity.object()));
        if (WitchesKitchenConfig.debugMode) {
            WitchesKitchen.LOGGER.info("Witches Kitchen Base Entities: Successfully Loaded");
        }
    }

}
