package cf.witcheskitchen.api;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public interface IDeviceExperienceHandler {

    /***
     * Fired when the player takes the crafted recipe  from the output slot
     * @param world ServerWorld
     * @param playerPos Vec3d ((position of the player))
     */
    void dropExperience(final ServerWorld world, final Vec3d playerPos);
}
