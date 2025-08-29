package cf.witcheskitchen.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class WKSplashParticle extends WaterDropParticle {

    public WKSplashParticle(ClientLevel clientWorld, double d, double e, double f, double r, double g, double b) {
        super(clientWorld, d, e, f);
        this.gravity = 0.04F;
        if (g == 0.0 && (r != 0.0 || b != 0.0)) {
            this.xd = r;
            this.yd = 0.1;
            this.zd = b;
        }
        this.rCol = (float) r;
        this.gCol = (float) g;
        this.bCol = (float) b;
    }


    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            final WKSplashParticle particle = new WKSplashParticle(clientWorld, d, e, f, g, h, i);
            particle.pickSprite(this.spriteProvider);
            return particle;
        }
    }
}
