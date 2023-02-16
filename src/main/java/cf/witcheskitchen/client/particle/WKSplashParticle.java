package cf.witcheskitchen.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.RainSplashParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class WKSplashParticle extends RainSplashParticle {

    public WKSplashParticle(ClientWorld clientWorld, double d, double e, double f, double r, double g, double b) {
        super(clientWorld, d, e, f);
        this.gravityStrength = 0.04F;
        if (g == 0.0 && (r != 0.0 || b != 0.0)) {
            this.velocityX = r;
            this.velocityY = 0.1;
            this.velocityZ = b;
        }
        this.colorRed = (float) r;
        this.colorGreen = (float) g;
        this.colorBlue = (float) b;
    }


    @ClientOnly
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            final WKSplashParticle particle = new WKSplashParticle(clientWorld, d, e, f, g, h, i);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
