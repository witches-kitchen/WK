package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.client.render.state.blockentity.BrewingBarrelRenderState;
import cf.witcheskitchen.client.render.state.blockentity.WitchesOvenRenderState;
import cf.witcheskitchen.common.block.WitchesOvenBlock;
import cf.witcheskitchen.common.blockentity.WitchesOvenBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class WitchesOvenBlockEntityRender implements BlockEntityRenderer<WitchesOvenBlockEntity, WitchesOvenRenderState> {
    private final ItemModelResolver itemModelResolver;

    public WitchesOvenBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public WitchesOvenRenderState createRenderState() {
        return new WitchesOvenRenderState();
    }

    @Override
    public void extractRenderState(WitchesOvenBlockEntity blockEntity, WitchesOvenRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.facing = blockEntity.getBlockState().getValue(WitchesOvenBlock.FACING);

        for (int i = 0; i < blockEntity.getStacksOnTop().size(); i++) {
            ItemStack stack = blockEntity.getStacksOnTop().get(i);
            var itemState = renderState.itemStackStates.get(i);

            this.itemModelResolver.appendItemLayers(itemState, stack, ItemDisplayContext.FIXED, blockEntity.getLevel(), blockEntity, HashCommon.long2int(blockEntity.getBlockPos().asLong()) + i);
        }
    }

    @Override
    public void submit(WitchesOvenRenderState renderState, PoseStack matrices, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        for (int i = 0; i < renderState.itemStackStates.size(); i++) {
            ItemStackRenderState itemStackState = renderState.itemStackStates.get(i);

            if (itemStackState.isEmpty()) {
                continue;
            }

            matrices.pushPose();
            matrices.translate(0.5D, 1.02, 0.5D);
            final Direction dir = Direction.from2DDataValue((i + renderState.facing.get2DDataValue()) % 4);
            final float rotation = -dir.toYRot();
            matrices.mulPose(Axis.YP.rotationDegrees(rotation));
            matrices.mulPose(Axis.XP.rotationDegrees(90.0F));
            matrices.translate(-0.20D, -0.20D, 0.0D);
            matrices.scale(0.375F, 0.375F, 0.375F);
            itemStackState.submit(matrices, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            matrices.popPose();
        }
    }
}
