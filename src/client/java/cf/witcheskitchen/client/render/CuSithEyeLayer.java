package cf.witcheskitchen.client.render;

import cf.witcheskitchen.client.render.state.WKRenderState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class CuSithEyeLayer<T extends GeoAnimatable, R extends WKRenderState & GeoRenderState> extends AutoGlowingGeoLayer<T, Void, R> {
    private static Identifier[] TEXTURES;

    public CuSithEyeLayer(GeoRenderer<T, Void, R> renderer) {
        super(renderer);
    }

    @Override
    public void render(R renderState, MatrixStack poseStack, BakedGeoModel bakedModel, @Nullable RenderLayer renderType, VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, int renderColor) {
        //TODO do color instead of multiple textures
        RenderLayer layer = getRenderType(renderState);
        getRenderer().reRender(renderState, poseStack, bakedModel, bufferSource, layer,
                bufferSource.getBuffer(layer), 15728640, OverlayTexture.DEFAULT_UV,
                ColorHelper.getArgb(255, 255, 255, 255));
    }
}