package cf.witcheskitchen.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.AtlasIds;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;


@Environment(EnvType.CLIENT)
public class RenderHelper {

    public static final Material MINECRAFT_WATER_STILL_SPRITE = new Material(AtlasIds.BLOCKS, ResourceLocation.withDefaultNamespace("block/water_still"));
    public static final Material MINECRAFT_LAVA_STILL_SPRITE = new Material(AtlasIds.BLOCKS, ResourceLocation.withDefaultNamespace("block/lava_still"));

    public static void renderWaterSprite(final PoseStack stack, final VertexConsumer buffer, final int color, final float size, final int light, final int overlay) {
        RenderHelper.renderFluidSprite(stack, buffer, MINECRAFT_WATER_STILL_SPRITE.sprite(), color, size, light, overlay);
    }

    public static void renderLavaSprite(final PoseStack stack, final VertexConsumer buffer, final float size, final int light, final int overlay) {
        RenderHelper.renderFluidSprite(stack, buffer, MINECRAFT_LAVA_STILL_SPRITE.sprite(), -1, size, light, overlay);
    }

    public static void renderFluidSprite(final PoseStack stack, final VertexConsumer buffer, final TextureAtlasSprite sprite, final int argb, final float size, final int light, final int overlay) {
        var matrix = stack.last().pose();
        float maxV = (sprite.getV1() - sprite.getV0()) * size;
        float minV = (sprite.getV1() - sprite.getV0()) * (1 - size);
        final int r = ARGB.red(argb);
        final int g = ARGB.green(argb);
        final int b = ARGB.blue(argb);
        buffer.addVertex(matrix, size, 0, 1 - size).setColor(r, g, b, 255).setUv(sprite.getU0(), sprite.getV0() + maxV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
        buffer.addVertex(matrix, 1 - size, 0, 1 - size).setColor(r, g, b, 255).setUv(sprite.getU1(), sprite.getV0() + maxV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
        buffer.addVertex(matrix, 1 - size, 0, size).setColor(r, g, b, 255).setUv(sprite.getU1(), sprite.getV0() + minV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
        buffer.addVertex(matrix, size, 0, size).setColor(r, g, b, 255).setUv(sprite.getU0(), sprite.getV0() + minV).setLight(light).setOverlay(overlay).setNormal(1, 1, 1);
    }
}
