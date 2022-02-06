package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class WKSoundEvents {

    private static final Map<Identifier, SoundEvent> SOUND_EVENTS = new HashMap<>();
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
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Sounds: Successfully Loaded");
        }
    }

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(WK.MODID, name);
        SoundEvent soundEvent = new SoundEvent(id);
        SOUND_EVENTS.put(id, soundEvent);
        return soundEvent;
    }

    public static void register() {
        SOUND_EVENTS.keySet().forEach(id -> Registry.register(Registry.SOUND_EVENT, id, SOUND_EVENTS.get(id)));
    }
}
