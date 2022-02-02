package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKSounds {

    // Cu-Sith 
    public static final SoundEvent CUSITH_IDLE_EVENT = register("cusith_ambient");
    public static final SoundEvent CUSITH_DEATH_EVENT = register("cusith_death");
    public static final SoundEvent CUSITH_HOWL_EVENT = register("cusith_howl");
 
    //Ferret
    public static final SoundEvent FERRET_IDLE_EVENT = register("ferret_ambient");
    public static final SoundEvent FERRET_ATTACK_EVENT = register("ferret_attack");
    public static final SoundEvent FERRET_CHIRP_EVENT = register("ferret_chirp");

    //Cauldron
    public static final SoundEvent CAULDRON_BOIL_EVENT = register("cauldron_bubble");

    //Broom
    public static final SoundEvent BROOM_RIDING_EVENT = register("broom_loop");
    public static final SoundEvent BROOM_USE_EVENT = register("broom_mount1");

    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(new Identifier(WK.MODID, id)));
    }
    
    public static void register() {
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Sounds: Successfully Loaded");
        }
    }

}
