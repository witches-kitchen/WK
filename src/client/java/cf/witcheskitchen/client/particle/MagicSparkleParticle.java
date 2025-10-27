package cf.witcheskitchen.client.particle;

import cf.witcheskitchen.api.event.network.MagicSparkleParticleEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class MagicSparkleParticle extends SingleQuadParticle {

    private final RandomSource random;
    private boolean canMove = false;
    private boolean circling = false;

    protected MagicSparkleParticle(ClientLevel clientWorld, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, TextureAtlasSprite sprite) {
        super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed, sprite);
        this.setScale(0.12f);
//        this.setColor((float) r, (float) g, (float) b);
        this.setColor(1f, 1f, 1f);
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
    public ParticleRenderType getGroup() {
        return ParticleRenderType.SINGLE_QUADS;
    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
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
        @Override
        public @Nullable Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new MagicSparkleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteProvider.get(random));
        }
    }

}
