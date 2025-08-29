package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.common.block.WitchesOvenBlock;
import cf.witcheskitchen.common.blockentity.BrewingBarrelBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class BrewingBarrelBlockEntityRender implements BlockEntityRenderer<BrewingBarrelBlockEntity> {

    @Override
    public void render(BrewingBarrelBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, Vec3 cameraPos) {
        final Direction facing = entity.getBlockState().getValue(WitchesOvenBlock.FACING);
        final ItemStack stack = entity.getRenderStack();
        if (stack.isEmpty()) {
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
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, matrices, vertexConsumers, entity.getLevel(), 0);
        matrices.popPose();
    }

}
