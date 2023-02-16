package cf.witcheskitchen.client.particle;

import cf.witcheskitchen.api.event.network.MagicSparkleParticleEvent;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.random.RandomGenerator;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class MagicSparkleParticle extends SpriteBillboardParticle {

    private final RandomGenerator random;
    private boolean canMove = false;
    private boolean circling = false;

    protected MagicSparkleParticle(ClientWorld clientWorld, double x, double y, double z, double r, double g, double b) {
        super(clientWorld, x, y, z);
        this.setScale(0.12f);
        this.setColor((float) r, (float) g, (float) b);
        this.random = clientWorld.getRandom();
        this.maxAge = 25 + (random.nextInt(10));
        MagicSparkleParticleEvent.PARTICLE_CONSTRUCTOR_EVENT.invoker().onConstructor(this);
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
            super.velocityY -= 0.04D * (double) super.gravityStrength;
        }
        this.move(super.velocityX, super.velocityY, super.velocityZ);
        if (this.onGround) {
            this.velocityX *= 0.7F;
            this.velocityZ *= 0.7F;
        }
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

    public RandomGenerator getRandom() {
        return random;
    }

    public float getRed() {
        return this.colorRed;
    }

    public float getGreen() {
        return this.colorGreen;
    }

    public float getBlue() {
        return this.colorBlue;
    }

    public float getAlpha() {
        return this.colorAlpha;
    }

    @ClientOnly
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            final MagicSparkleParticle particle = new MagicSparkleParticle(clientWorld, d, e, f, g, h, i);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }

}
