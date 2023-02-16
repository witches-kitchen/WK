package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WKParticleTypes {

    private static final List<ObjectDefinition<ParticleType<?>>> PARTICLE_TYPES = new ArrayList<>();

    public static final ParticleType<DefaultParticleType> BUBBLE = create("bubble", FabricParticleTypes.simple());
    public static final ParticleType<DefaultParticleType> SPLASH = create("splash", FabricParticleTypes.simple());
    public static final ParticleType<DefaultParticleType> MAGIC_SPARKLE = create("magic_sparkle", FabricParticleTypes.simple());

    private static <T extends ParticleEffect> ParticleType<T> create(final String name, final ParticleType<T> type) {
        final Identifier id = WK.id(name);
        final ObjectDefinition<ParticleType<?>> definition = new ObjectDefinition<>(id, type);
        PARTICLE_TYPES.add(definition);
        return type;
    }

    public static List<ObjectDefinition<ParticleType<?>>> getParticleTypes() {
        return Collections.unmodifiableList(PARTICLE_TYPES);
    }

    public static void register() {
        PARTICLE_TYPES.forEach(entry -> Registry.register(Registry.PARTICLE_TYPE, entry.id(), entry.object()));
        if (WKConfig.debugMode) {
            WK.LOGGER.info("Witches Kitchen Base Custom Particles: Successfully Loaded");
        }
    }
}
