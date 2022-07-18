package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.WKIdentifier;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WKSoundEvents {
    private static final List<ObjectDefinition<SoundEvent>> SOUND_EVENTS = new ArrayList<>();
    // Cu-Sith 
    public static final SoundEvent CUSITH_IDLE_EVENT = register("cusith_ambient");
    public static final SoundEvent CUSITH_DEATH_EVENT = register("cusith_death");
    public static final SoundEvent CUSITH_HOWL_EVENT = register("cusith_howl");
    //Ferret
    public static final SoundEvent FERRET_IDLE_EVENT = register("ferret_ambient");
    public static final SoundEvent FERRET_ATTACK_EVENT = register("ferret_attack");
    public static final SoundEvent FERRET_CHIRP_EVENT = register("ferret_chirp");
    //Particles
    public static final SoundEvent BUBBLE = register("bubble");
    //Broom
    public static final SoundEvent BROOM_RIDING_EVENT = register("broom_loop");
    public static final SoundEvent BROOM_USE_EVENT = register("broom_mount1");

    static {
        if (WKConfig.getInstance().debugMode) {
            WK.LOGGER.info("Witches Kitchen Base Sounds: Successfully Loaded");
        }
    }

    public static List<ObjectDefinition<SoundEvent>> getSoundEvents() {
        return Collections.unmodifiableList(SOUND_EVENTS);
    }

    static SoundEvent register(String name) {
        final Identifier id = new WKIdentifier(name);
        final SoundEvent soundEvent = new SoundEvent(id);
        final ObjectDefinition<SoundEvent> def = new ObjectDefinition<>(id, soundEvent);
        SOUND_EVENTS.add(def);
        return soundEvent;
    }

    public static void register() {
        SOUND_EVENTS.forEach(entry -> Registry.register(Registry.SOUND_EVENT, entry.id(), entry.object()));
    }
}
