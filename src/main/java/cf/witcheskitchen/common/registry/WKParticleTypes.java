package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class WKParticleTypes {

    private static final Map<Identifier, ParticleType<?>> PARTICLE_TYPES = new LinkedHashMap<>();

    public static final ParticleType<DefaultParticleType> BUBBLE = create("bubble", FabricParticleTypes.simple());

    private static <T extends ParticleEffect> ParticleType<T> create(final String id, final ParticleType<T> type) {
        PARTICLE_TYPES.put(new Identifier(WK.MODID, id), type);
        return type;
    }

    public static void init() {
        PARTICLE_TYPES.keySet().forEach(id -> Registry.register(Registry.PARTICLE_TYPE, id, PARTICLE_TYPES.get(id)));
    }
}
