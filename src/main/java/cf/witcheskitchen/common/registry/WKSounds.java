package cf.witcheskitchen.common.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKSounds {

    // Cu-Sith 
    public static SoundEvent CUSITH_IDLE_EVENT = register("witcheskitchen:cusith_ambient");
    public static SoundEvent CUSITH_DEATH_EVENT = register("witcheskitchen:cusith_death");


    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(new Identifier(id)));
    }


    public static void register() {
    }

}
