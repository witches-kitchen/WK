package cf.witcheskitchen.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RenderHelper {

    public static final SpriteIdentifier MINECRAFT_WATER_STILL_SPRITE = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier("block/water_still"));

    public static void renderWaterSprite(final MatrixStack stack, final VertexConsumer buffer, final int color, final float size, final int light, final int overlay) {
        RenderHelper.renderFluidSprite(stack, buffer, MINECRAFT_WATER_STILL_SPRITE.getSprite(), color, size, light, overlay);
    }

    public static void renderFluidSprite(final MatrixStack stack, final VertexConsumer buffer, final Sprite sprite, final int hexColor, final float size, final int light, final int overlay) {
        var matrix = stack.peek().getPositionMatrix();
        float maxV = (sprite.getMaxV() - sprite.getMinV()) * size;
        float minV = (sprite.getMaxV() - sprite.getMinV()) * (1 - size);
        final int r = (hexColor >> 16) & 0xff;
        final int g = (hexColor >> 8) & 0xff;
        final int b = hexColor & 0xff;
        buffer.vertex(matrix, size, 0, 1 - size).color(r, g, b, 255).texture(sprite.getMinU(), sprite.getMinV() + maxV).light(light).overlay(overlay).normal(1, 1, 1).next();
        buffer.vertex(matrix, 1 - size, 0, 1 - size).color(r, g, b, 255).texture(sprite.getMaxU(), sprite.getMinV() + maxV).light(light).overlay(overlay).normal(1, 1, 1).next();
        buffer.vertex(matrix, 1 - size, 0, size).color(r, g, b, 255).texture(sprite.getMaxU(), sprite.getMinV() + minV).light(light).overlay(overlay).normal(1, 1, 1).next();
        buffer.vertex(matrix, size, 0, size).color(r, g, b, 255).texture(sprite.getMinU(), sprite.getMinV() + minV).light(light).overlay(overlay).normal(1, 1, 1).next();
    }
}
