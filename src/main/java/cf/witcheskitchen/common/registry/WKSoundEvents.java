package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

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

    static List<ObjectDefinition<SoundEvent>> getSoundEvents() {
        return Collections.unmodifiableList(SOUND_EVENTS);
    }

    private static SoundEvent register(String name) {
        final Identifier id = WitchesKitchen.id(name);
        final SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(id);
        final ObjectDefinition<SoundEvent> def = new ObjectDefinition<>(id, soundEvent);
        SOUND_EVENTS.add(def);
        return soundEvent;
    }

    static void init() {
        SOUND_EVENTS.forEach(entry -> Registry.register(Registries.SOUND_EVENT, entry.id(), entry.object()));
    }
}
