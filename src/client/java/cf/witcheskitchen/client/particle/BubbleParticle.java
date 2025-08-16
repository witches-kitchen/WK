package cf.witcheskitchen.client.particle;

import cf.witcheskitchen.api.block.WKBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class BubbleParticle extends SpriteBillboardParticle {

    public BubbleParticle(ClientWorld clientWorld, double posX, double posY, double posZ, double r, double g, double b) {
        super(clientWorld, posX, posY, posZ, r, g, b);
        this.setBoundingBoxSpacing(0.02F, 0.02F);
        float offset = (float) ((Math.random() * 0.4F) + 0.3F);
        this.scale *= offset;
        this.maxAge = (int) (8D / (Math.random() * 0.8D + 0.2D));
        this.velocityX *= 0.1;
        this.velocityY *= 0.1;
        this.velocityZ *= 0.1;
        this.red = (float) (((Math.random() * 0.3F) + 1.0F) * r * offset);
        this.green = (float) (((Math.random() * 0.3F) + 1.0F) * g * offset);
        this.blue = (float) (((Math.random() * 0.3F) + 1.0F) * b * offset);
    }

    @Override
    public void tick() {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (maxAge-- <= 0) {
            this.markDead();
        } else {
            this.move(velocityX, velocityY, velocityZ);
            this.velocityX *= 0.85;
            this.velocityY *= 0.85;
            this.velocityZ *= 0.85;
            final BlockPos pos = BlockPos.ofFloored(this.x, this.y, this.z);
            if (this.kill(pos)) {
                this.markDead();
            }
        }
    }

    protected boolean kill(BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(Blocks.WATER) || (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED))) {
            return false;
        } else {
            return !(state.getBlock() instanceof WKBlock);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    // Immutable Factory
    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            final BubbleParticle particle = new BubbleParticle(clientWorld, d, e, f, g, h, i);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
