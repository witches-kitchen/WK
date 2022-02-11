package cf.witcheskitchen.client.particle;

import cf.witcheskitchen.api.event.network.MagicSparkleParticleEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class MagicSparkleParticle extends SpriteBillboardParticle {

    private boolean canMove = false;
    private boolean circling = false;

    private final Random random;

    protected MagicSparkleParticle(ClientWorld clientWorld, double d, double e, double f, double r, double g, double b) {
        super(clientWorld, d, e, f);
        this.setScale(0.12f);
        this.setColor((float) r, (float) g, (float) b);
        this.random = clientWorld.getRandom();
        this.maxAge = 25 + (random.nextInt(10));
        MagicSparkleParticleEvent.PARTICLE_CONSTRUCTOR_EVENT.invoker().onConstructor(this);
    }

    public float getRed() {
        return this.red;
    }
    public float getGreen() {
        return this.green;
    }
    public float getBlue() {
        return this.blue;
    }
    public float getAlpha() {
        return this.alpha;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;

        if (this.age++ >= this.maxAge) {
            this.markDead();
        }
        if (!this.dead) {
            super.velocityY -= 0.04D * (double)super.gravityStrength;
        }
        if (this.onGround) {
            this.velocityX *= 0.7F;
            this.velocityZ *= 0.7F;
        }

        this.move(super.velocityX, super.velocityY, super.velocityZ);

    }

    public MagicSparkleParticle setGravity(float gravity) {
        super.gravityStrength = gravity;
        return this;
    }


    public MagicSparkleParticle setCanMove(boolean canMove) {
        this.canMove = canMove;
        return this;
    }

    public MagicSparkleParticle setScale(float scale) {
        super.scale = scale;
        return this;
    }

    public MagicSparkleParticle setCircling(boolean circling) {
        this.circling = circling;
        return this;
    }


    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public Random getRandom() {
        return random;
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
