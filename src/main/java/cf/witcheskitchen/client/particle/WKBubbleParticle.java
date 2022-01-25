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
        this.maxAge = (int) (8d / (Math.random() * 0.8 + 0.2));

        this.velocityX *= 0.1;
        this.velocityY *= 0.1;
        this.velocityZ *= 0.1;
        float f = (float) (Math.random() * 0.4f + 0.6f);
        this.scale *= f / 2;
        this.red = (float) (((Math.random() * 0.2) + 0.8f) * r * f);
        this.green = (float) (((Math.random() * 0.2) + 0.8f) * g * f);
        this.blue = (float) (((Math.random() * 0.2) + 0.8f) * b * f);


    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            this.markDead();
        }
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.velocityY += 0.002;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= 0.85;
        this.velocityY *= 0.85;
        this.velocityZ *= 0.85;
        final BlockPos particlePos = new BlockPos(this.x, this.y, this.z);
        if (this.maxAge-- <= 0 || this.kill(particlePos)) {
            this.markDead();
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
