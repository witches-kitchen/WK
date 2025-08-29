package cf.witcheskitchen.client.particle;

import cf.witcheskitchen.api.event.network.MagicSparkleParticleEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

@Environment(EnvType.CLIENT)
public class MagicSparkleParticle extends TextureSheetParticle {

    private final RandomSource random;
    private boolean canMove = false;
    private boolean circling = false;

    protected MagicSparkleParticle(ClientLevel clientWorld, double x, double y, double z, double r, double g, double b) {
        super(clientWorld, x, y, z);
        this.setScale(0.12f);
        this.setColor((float) r, (float) g, (float) b);
        this.random = clientWorld.getRandom();
        this.lifetime = 25 + (random.nextInt(10));
        MagicSparkleParticleEvent.PARTICLE_CONSTRUCTOR_EVENT.invoker().onConstructor(this);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        }
        if (!this.removed) {
            super.yd -= 0.04D * (double) super.gravity;
        }
        this.move(super.xd, super.yd, super.zd);
        if (this.onGround) {
            this.xd *= 0.7F;
            this.zd *= 0.7F;
        }
    }

    public MagicSparkleParticle setGravity(float gravity) {
        super.gravity = gravity;
        return this;
    }


    public MagicSparkleParticle setCanMove(boolean canMove) {
        this.canMove = canMove;
        return this;
    }

    public MagicSparkleParticle setScale(float scale) {
        super.quadSize = scale;
        return this;
    }

    public MagicSparkleParticle setCircling(boolean circling) {
        this.circling = circling;
        return this;
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public RandomSource getRandom() {
        return random;
    }

    public float getRed() {
        return this.rCol;
    }

    public float getGreen() {
        return this.gCol;
    }

    public float getBlue() {
        return this.bCol;
    }

    public float getAlpha() {
        return this.alpha;
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            final MagicSparkleParticle particle = new MagicSparkleParticle(clientWorld, d, e, f, g, h, i);
            particle.pickSprite(this.spriteProvider);
            return particle;
        }
    }

}
