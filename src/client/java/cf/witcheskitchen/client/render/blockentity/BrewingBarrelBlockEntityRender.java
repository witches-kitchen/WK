package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.client.render.state.blockentity.BrewingBarrelRenderState;
import cf.witcheskitchen.common.block.WitchesOvenBlock;
import cf.witcheskitchen.common.blockentity.BrewingBarrelBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class BrewingBarrelBlockEntityRender implements BlockEntityRenderer<BrewingBarrelBlockEntity, BrewingBarrelRenderState> {
    private final ItemModelResolver itemModelResolver;

    public BrewingBarrelBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public BrewingBarrelRenderState createRenderState() {
        return new BrewingBarrelRenderState();
    }

    @Override
    public void extractRenderState(BrewingBarrelBlockEntity blockEntity, BrewingBarrelRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.facing = blockEntity.getBlockState().getValue(WitchesOvenBlock.FACING);

        var itemStackState = new ItemStackRenderState();
        this.itemModelResolver.appendItemLayers(itemStackState, blockEntity.getRenderStack(), ItemDisplayContext.FIXED, blockEntity.getLevel(), blockEntity, HashCommon.long2int(blockEntity.getBlockPos().asLong()));
        renderState.itemStackState = itemStackState;
    }

    @Override
    public void submit(BrewingBarrelRenderState renderState, PoseStack matrices, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        Direction facing = renderState.facing;
        if (renderState.itemStackState.isEmpty()) {
            return;
        }

        matrices.pushPose();
        double offsetZ = 0;
        double offsetX = 0;
        switch (facing) {
            case SOUTH -> offsetZ += 1.05;
            case EAST -> {
                offsetX -= 1.0;
                offsetZ += 1.05;
                matrices.mulPose(Axis.YP.rotationDegrees(90));
            }
            case WEST -> {
                offsetX -= 1.0;
                matrices.mulPose(Axis.YP.rotationDegrees(90));
            }
            default -> {
            }
        }

        matrices.translate(0.51 + offsetX, 0.2, -0.02 + offsetZ);
        matrices.scale(0.375F, 0.375F, 0.375F);
        renderState.itemStackState.submit(matrices, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();
    }
}
