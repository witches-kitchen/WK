package cf.witcheskitchen.client.particle;

import cf.witcheskitchen.common.blocks.technical.WKDeviceBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Material;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class WKBubbleParticle extends SpriteBillboardParticle {

    public WKBubbleParticle(ClientWorld clientWorld, double posX, double posY, double posZ, double r, double g, double b) {
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
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (maxAge-- <= 0) {
            this.markDead();
        } else {
            this.move(velocityX, velocityY, velocityZ);
            this.velocityX *= 0.85;
            this.velocityY *= 0.85;
            this.velocityZ *= 0.85;
            final BlockPos pos = new BlockPos(this.x, this.y, this.z);
            if (this.kill(pos)) {
                this.markDead();
            }
        }
    }

    protected boolean kill(BlockPos pos) {
        if (world.getBlockState(pos).getMaterial() == Material.WATER) {
            return false;
        } else {
            return !(this.world.getBlockState(pos).getBlock() instanceof WKDeviceBlock);
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
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            final WKBubbleParticle particle = new WKBubbleParticle(clientWorld, d, e, f, g, h, i);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
