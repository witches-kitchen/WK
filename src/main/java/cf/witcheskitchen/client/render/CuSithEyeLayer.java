package cf.witcheskitchen.client.render;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.texture.AutoGlowingTexture;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class CuSithEyeLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    private static Identifier[] TEXTURES;

    public CuSithEyeLayer(GeoRenderer<T> renderer) {
        super(renderer);
    }

    protected RenderLayer getRenderLayer(T animatable) {
        return AutoGlowingTexture.getRenderType(getTextureResource(animatable));
    }

    @Override
    public void render(MatrixStack poseStack, T animatable, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        //TODO do color instead of multiple textures
        RenderLayer layer = getRenderLayer(animatable);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, layer,
                bufferSource.getBuffer(layer), partialTick, 15728640, OverlayTexture.DEFAULT_UV,
                1, 1, 1, 1);
    }
}