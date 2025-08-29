package cf.witcheskitchen.client.particle;

import cf.witcheskitchen.api.block.WKBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

@Environment(EnvType.CLIENT)
public class BubbleParticle extends TextureSheetParticle {

    public BubbleParticle(ClientLevel clientWorld, double posX, double posY, double posZ, double r, double g, double b) {
        super(clientWorld, posX, posY, posZ, r, g, b);
        this.setSize(0.02F, 0.02F);
        float offset = (float) ((Math.random() * 0.4F) + 0.3F);
        this.quadSize *= offset;
        this.lifetime = (int) (8D / (Math.random() * 0.8D + 0.2D));
        this.xd *= 0.1;
        this.yd *= 0.1;
        this.zd *= 0.1;
        this.rCol = (float) (((Math.random() * 0.3F) + 1.0F) * r * offset);
        this.gCol = (float) (((Math.random() * 0.3F) + 1.0F) * g * offset);
        this.bCol = (float) (((Math.random() * 0.3F) + 1.0F) * b * offset);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (lifetime-- <= 0) {
            this.remove();
        } else {
            this.move(xd, yd, zd);
            this.xd *= 0.85;
            this.yd *= 0.85;
            this.zd *= 0.85;
            final BlockPos pos = BlockPos.containing(this.x, this.y, this.z);
            if (this.kill(pos)) {
                this.remove();
            }
        }
    }

    protected boolean kill(BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.is(Blocks.WATER) || (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED))) {
            return false;
        } else {
            return !(state.getBlock() instanceof WKBlock);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void setScale(float scale) {
        this.quadSize = scale;
    }

    // Immutable Factory
    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            final BubbleParticle particle = new BubbleParticle(clientWorld, d, e, f, g, h, i);
            particle.pickSprite(this.spriteProvider);
            return particle;
        }
    }
}
