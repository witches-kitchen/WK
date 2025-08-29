package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WKSoundEvents {
    List<ObjectDefinition<SoundEvent>> SOUND_EVENTS = new ArrayList<>();
    // Cu-Sith 
    SoundEvent CUSITH_IDLE_EVENT = register("cusith_ambient");
    SoundEvent CUSITH_DEATH_EVENT = register("cusith_death");
    SoundEvent CUSITH_HOWL_EVENT = register("cusith_howl");
    //Ferret
    SoundEvent FERRET_IDLE_EVENT = register("ferret_idle");
    SoundEvent FERRET_ATTACK_EVENT = register("ferret_attack");
    SoundEvent FERRET_CHIRP_EVENT = register("ferret_chirp");
    //Particles
    SoundEvent BUBBLE = register("bubble");
    //Broom
    SoundEvent BROOM_RIDING_EVENT = register("broom_loop");
    SoundEvent BROOM_USE_EVENT = register("broom_mount1");

    SoundEvent HALLUCINATION_BREATH = register("hallucination_breath");

    static List<ObjectDefinition<SoundEvent>> getSoundEvents() {
        return Collections.unmodifiableList(SOUND_EVENTS);
    }

    private static SoundEvent register(String name) {
        final ResourceLocation id = WitchesKitchen.id(name);
        final SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(id);
        final ObjectDefinition<SoundEvent> def = new ObjectDefinition<>(id, soundEvent);
        SOUND_EVENTS.add(def);
        return soundEvent;
    }

    static void init() {
        SOUND_EVENTS.forEach(entry -> Registry.register(BuiltInRegistries.SOUND_EVENT, entry.id(), entry.object()));
    }
}
