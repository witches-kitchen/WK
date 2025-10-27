package cf.witcheskitchen.client.particle;

import cf.witcheskitchen.api.block.WKBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class BubbleParticle extends SingleQuadParticle {

    public BubbleParticle(ClientLevel clientWorld, double posX, double posY, double posZ, double xSpeed, double ySpeed, double zSpeed, TextureAtlasSprite sprite) {
        super(clientWorld, posX, posY, posZ, xSpeed, ySpeed, zSpeed, sprite);
        this.setSize(0.02F, 0.02F);
        float offset = (float) ((Math.random() * 0.4F) + 0.3F);
        this.quadSize *= offset;
        this.lifetime = (int) (8D / (Math.random() * 0.8D + 0.2D));
        this.xd *= 0.1;
        this.yd *= 0.1;
        this.zd *= 0.1;
//        this.rCol = (float) (((Math.random() * 0.3F) + 1.0F) * r * offset);
//        this.gCol = (float) (((Math.random() * 0.3F) + 1.0F) * g * offset);
//        this.bCol = (float) (((Math.random() * 0.3F) + 1.0F) * b * offset);
        this.rCol = (float) (((Math.random() * 0.3F) + 1.0F) * offset);
        this.gCol = (float) (((Math.random() * 0.3F) + 1.0F) * offset);
        this.bCol = (float) (((Math.random() * 0.3F) + 1.0F) * offset);
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
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    public void setScale(float scale) {
        this.quadSize = scale;
    }

    // Immutable Factory
    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @Nullable Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new BubbleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider.get(random));
        }
    }
}
