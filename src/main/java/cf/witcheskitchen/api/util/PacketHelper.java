package cf.witcheskitchen.api.util;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import java.util.function.Consumer;

public final class PacketHelper {

    private PacketHelper() {
        // Don't let anyone instantiate this
    }

    /**
     * Sends the packet to all players tracking the given entity in a server world.
     *
     * <p><b>Warning</b>: If the provided entity is a player, it is not
     * guaranteed by the contract that said player is included in the
     * resulting stream.
     *
     * @param trackedEntity the entity being tracked
     * @throws IllegalArgumentException if the entity is not in a server world
     */

    public static void sendToAllTracking(final Entity trackedEntity, Consumer<? super ServerPlayer> packet) {
        PlayerLookup.tracking(trackedEntity).forEach(packet);
    }

    public static void sendToAllTracking(ServerLevel world, BlockPos pos, Consumer<? super ServerPlayer> packet) {
        PlayerLookup.tracking(world, world.getChunkAt(pos).getPos()).forEach(packet);
    }
}
