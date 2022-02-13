package cf.witcheskitchen.api.event.network;

import cf.witcheskitchen.client.particle.MagicSparkleParticle;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class MagicSparkleParticleEvent {

    public static final Event<ParticleConstructorCallback> PARTICLE_CONSTRUCTOR_EVENT = EventFactory.createArrayBacked(ParticleConstructorCallback.class, (listeners) -> (particle) -> {
                for (var callback : listeners) {
                    callback.onConstructor(particle);
                }
            }
    );

    @FunctionalInterface
    public interface ParticleConstructorCallback {
        void onConstructor(MagicSparkleParticle particle);
    }
}
