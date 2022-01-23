package cf.witcheskitchen.client.particle;

import cf.witcheskitchen.api.event.ParticleEvents;
import cf.witcheskitchen.common.blocks.technical.WKDeviceBlock;
import dev.architectury.event.events.client.ClientPlayerEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Material;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class WKBubbleParticle extends SpriteBillboardParticle {

    protected boolean move;

    public WKBubbleParticle(ClientWorld clientWorld, double posX, double posY, double posZ, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, posX, posY, posZ, velocityX, velocityY, velocityZ);
        // Minecraft bubble particle properties
        this.setBoundingBoxSpacing(0.02F, 0.02F);
        this.maxAge = (int) (8d / (Math.random() * 0.8 + 0.2));
   //     this.scale *= random.nextFloat() * 0.3 + 0.3;
        this.velocityX *= 0.1;
        this.velocityY *= 0.1;
        this.velocityZ *= 0.1;
        this.red = (float) velocityX;
        this.green = (float) velocityY;
        this.blue = (float) velocityZ;
        this.maxAge = (int) (4 / (Math.random() * 0.8 + 0.2));
        float f = (float) (Math.random() * 0.4f + 0.6f);
        this.scale *= f / 2;

    }

    @Override
    public void tick() {
        if (!world.isClient) {
            this.markDead();
        }
        //ParticleEvents.START_BUBBLE_PARTICLE_TICK_EVENT.invoker().onStartTick(this, this.random);
        prevPosX = this.x;
        prevPosY = this.y;
        prevPosZ = this.z;
        this.velocityY += 0.002;
        move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= 0.85;
        this.velocityY *= 0.85;
        this.velocityZ *= 0.85;
        if (maxAge-- <= 0 || world.getBlockState(new BlockPos(this.x, this.y, this.z)).getMaterial() != Material.WATER) {
            this.markDead();
        }
   //     ParticleEvents.END_BUBBLE_PARTICLE_TICK_EVENT.invoker().onEndTick(this, this.random);
    }

    protected boolean kill(BlockPos pos) {
        if (!(world.getBlockState(pos).getBlock() instanceof WKDeviceBlock)) {
            return true;
        } else if (!((world.getBlockState(pos).getBlock()) instanceof ParticleEvents.StartBubbleParticleTick)) {
            return true;
        } else {
            return !((world.getBlockState(pos).getBlock()) instanceof ParticleEvents.EndBubbleParticleTick);
        }
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public void setMoving(boolean move) {
        this.move = move;
    }

    public void setGravityStrength(float strength) {
        this.gravityStrength = strength;
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
