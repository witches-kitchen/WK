package cf.witcheskitchen.client.particle;

import cf.witcheskitchen.common.blocks.technical.WKDeviceBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class WKBubbleParticle extends SpriteBillboardParticle {

    public WKBubbleParticle(ClientWorld clientWorld, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, posX, posY, posZ, velocityX, velocityY, velocityZ);
        // Minecraft bubble particle properties
        this.setBoundingBoxSpacing(0.02F, 0.02F);
        this.scale *= random.nextFloat() * 0.3 + 0.3;
        this.velocityX *= 0.1;
        this.velocityY *= 0.1;
        this.velocityZ *= 0.1;
        this.red = (float) velocityX;
        this.green = (float) velocityY;
        this.blue = (float) velocityZ;
        this.maxAge = (int) (4 / (Math.random() * 0.8 + 0.2));
    }


    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.maxAge-- <= 0) {
            super.markDead();
        } else {
            this.velocityY += 0.002D;
            super.move(velocityX, velocityY, velocityZ);
            this.velocityX *= 0.8500000238418579D;
            this.velocityY *= 0.8500000238418579D;
            this.velocityZ *= 0.8500000238418579D;
            if (!(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof WKDeviceBlock)) {
                super.markDead();
            }
        }
        System.out.println("Ticking");
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
