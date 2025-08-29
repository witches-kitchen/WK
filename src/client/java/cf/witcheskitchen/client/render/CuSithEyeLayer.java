package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.render.state.WKRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class CuSithEyeLayer<T extends GeoAnimatable, R extends WKRenderState & GeoRenderState> extends AutoGlowingGeoLayer<T, Void, R> {
    private static ResourceLocation[] TEXTURES;

    public CuSithEyeLayer(GeoRenderer<T, Void, R> renderer) {
        super(renderer);
    }

    @Override
    public void render(R renderState, PoseStack poseStack, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, int renderColor) {
        //TODO do color instead of multiple textures
        RenderType layer = getRenderType(renderState);
        getRenderer().reRender(renderState, poseStack, bakedModel, bufferSource, layer,
                bufferSource.getBuffer(layer), 15728640, OverlayTexture.NO_OVERLAY,
                ARGB.color(255, 255, 255, 255));
    }
}