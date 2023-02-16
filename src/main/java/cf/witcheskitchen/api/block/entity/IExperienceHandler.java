package cf.witcheskitchen.api.block.entity;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public interface IExperienceHandler {

    /**
     * <p>Fired when the player takes the crafted recipe  from the output slot.</p>
     * <p>As well as when the block is mined</p>
     *
     * @param world     ServerWorld
     * @param playerPos Vec3d ((position of the player))
     */
    void dropExperience(final ServerWorld world, final Vec3d playerPos);
}
