package cf.witcheskitchen.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class WKSplashParticle extends WaterDropParticle {

    public WKSplashParticle(ClientLevel clientWorld, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, TextureAtlasSprite sprite) {
        super(clientWorld, x, y, z, sprite);
        this.gravity = 0.04F;
        if (ySpeed == 0.0 && (xSpeed != 0.0 || zSpeed != 0.0)) {
            this.xd = xSpeed;
            this.yd = 0.1;
            this.zd = zSpeed;
        }
//        this.rCol = (float) r;
//        this.gCol = (float) g;
//        this.bCol = (float) b;
    }


    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @Nullable Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new WKSplashParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteProvider.get(random));
        }
    }
}
