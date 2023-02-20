package cf.witcheskitchen.api.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ColorUtil;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;


@ClientOnly
public class RenderHelper {

    public static final SpriteIdentifier MINECRAFT_WATER_STILL_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier("block/water_still"));
    public static final SpriteIdentifier MINECRAFT_LAVA_STILL_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier("block/lava_still"));

    public static void renderWaterSprite(final MatrixStack stack, final VertexConsumer buffer, final int color, final float size, final int light, final int overlay) {
        RenderHelper.renderFluidSprite(stack, buffer, MINECRAFT_WATER_STILL_SPRITE.getSprite(), color, size, light, overlay);
    }

    public static void renderLavaSprite(final MatrixStack stack, final VertexConsumer buffer, final float size, final int light, final int overlay) {
        RenderHelper.renderFluidSprite(stack, buffer, MINECRAFT_LAVA_STILL_SPRITE.getSprite(), -1, size, light, overlay);
    }

    public static void renderFluidSprite(final MatrixStack stack, final VertexConsumer buffer, final Sprite sprite, final int argb, final float size, final int light, final int overlay) {
        var matrix = stack.peek().getModel();
        float maxV = (sprite.getMaxV() - sprite.getMinV()) * size;
        float minV = (sprite.getMaxV() - sprite.getMinV()) * (1 - size);
        final int r = ColorUtil.ARGB32.getRed(argb);
        final int g = ColorUtil.ARGB32.getGreen(argb);
        final int b = ColorUtil.ARGB32.getBlue(argb);
        buffer.vertex(matrix, size, 0, 1 - size).color(r, g, b, 255).uv(sprite.getMinU(), sprite.getMinV() + maxV).light(light).overlay(overlay).normal(1, 1, 1).next();
        buffer.vertex(matrix, 1 - size, 0, 1 - size).color(r, g, b, 255).uv(sprite.getMaxU(), sprite.getMinV() + maxV).light(light).overlay(overlay).normal(1, 1, 1).next();
        buffer.vertex(matrix, 1 - size, 0, size).color(r, g, b, 255).uv(sprite.getMaxU(), sprite.getMinV() + minV).light(light).overlay(overlay).normal(1, 1, 1).next();
        buffer.vertex(matrix, size, 0, size).color(r, g, b, 255).uv(sprite.getMinU(), sprite.getMinV() + minV).light(light).overlay(overlay).normal(1, 1, 1).next();
    }
}
