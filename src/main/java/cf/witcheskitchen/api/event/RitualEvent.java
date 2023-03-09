package cf.witcheskitchen.api.event;

import cf.witcheskitchen.api.ritual.Ritual;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class RitualEvent {
    public static final Event<Tick> TICK = EventFactory.createArrayBacked(Tick.class, listener -> (rite, world, pos) -> {
        for (Tick event : listener) {
            event.tick(rite, world, pos);
        }
    });

    public static final Event<Start> START = EventFactory.createArrayBacked(Start.class, start -> (rite, world, pos, player) -> {
        for (Start event : start) {
            event.start(rite, world, pos, player);
        }
    });

    public static final Event<End> END = EventFactory.createArrayBacked(End.class, listener -> (rite, world, pos) -> {
        for (End event : listener) {
            event.end(rite, world, pos);
        }
    });

    @FunctionalInterface
    public interface Tick {
        void tick(Ritual rite, World world, BlockPos pos);
    }

    @FunctionalInterface
    public interface Start {
        void start(Ritual rite, World world, BlockPos pos, PlayerEntity player);
    }

    @FunctionalInterface
    public interface End {
        void end(Ritual rite, World world, BlockPos pos);
    }
}
