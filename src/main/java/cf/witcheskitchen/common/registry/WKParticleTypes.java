package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WKParticleTypes {

    List<ObjectDefinition<ParticleType<?>>> PARTICLE_TYPES = new ArrayList<>();

    ParticleType<SimpleParticleType> BUBBLE = create("bubble", FabricParticleTypes.simple());
    ParticleType<SimpleParticleType> SPLASH = create("splash", FabricParticleTypes.simple());
    ParticleType<SimpleParticleType> MAGIC_SPARKLE = create("magic_sparkle", FabricParticleTypes.simple());

    static <T extends ParticleOptions> ParticleType<T> create(final String name, final ParticleType<T> type) {
        final ResourceLocation id = WitchesKitchen.id(name);
        final ObjectDefinition<ParticleType<?>> definition = new ObjectDefinition<>(id, type);
        PARTICLE_TYPES.add(definition);
        return type;
    }

    static List<ObjectDefinition<ParticleType<?>>> getParticleTypes() {
        return Collections.unmodifiableList(PARTICLE_TYPES);
    }

    static void init() {
        PARTICLE_TYPES.forEach(entry -> Registry.register(BuiltInRegistries.PARTICLE_TYPE, entry.id(), entry.object()));
    }
}
