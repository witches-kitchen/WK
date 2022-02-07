package cf.witcheskitchen.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3i;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class MagicSparkleParticle extends SpriteBillboardParticle {

    protected MagicSparkleParticle(ClientWorld clientWorld, double d, double e, double f, double r, double g, double b) {
        super(clientWorld, d, e, f);
        this.gravityStrength = 0.25F;
        final Random random = clientWorld.getRandom();
        this.setMaxAge(25 + clientWorld.random.nextInt(10));
        this.setVelocity(clientWorld.random.nextDouble() * 0.08D - 0.04D, clientWorld.random.nextDouble() * 0.05D + 0.08D, clientWorld.random.nextDouble() * 0.08D - 0.04D);
        float maxColorShift = 0.2F;
        float doubleColorShift = maxColorShift * 2.0F;
        float shiftR = random.nextFloat() * doubleColorShift - maxColorShift;
        float shitG = random.nextFloat() * doubleColorShift - maxColorShift;
        float shitB = random.nextFloat() * doubleColorShift - maxColorShift;
        this.setColor(shiftR, shitG, shitB);
        this.setAlpha(0.2f);

    }
    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if(this.age ++ < Math.min(super.maxAge, 600) && super.age >= 0) {
            if((double)super.age > (double)super.maxAge * 0.9D) {

            }
        } else {
            this.markDead();
        }
        if (!this.dead) {
            Vector3d f = new Vector3d(this.x, this.y, this.z);
            f.multiply(0.5f);
            super.velocityX *= 1.08D;
            super.velocityY *= 0.85D;
            super.velocityZ *= 1.08D;
          //  super.velocityY -= 0.04D * (double)super.gravityStrength;
        }
        this.move(super.velocityX, super.velocityY, super.velocityZ);

    }
    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            final MagicSparkleParticle particle = new MagicSparkleParticle(clientWorld, d, e, f, g, h, i);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
