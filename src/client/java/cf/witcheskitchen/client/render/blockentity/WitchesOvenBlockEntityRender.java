package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.common.block.WitchesOvenBlock;
import cf.witcheskitchen.common.blockentity.WitchesOvenBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class WitchesOvenBlockEntityRender implements BlockEntityRenderer<WitchesOvenBlockEntity> {

    @Override
    public void render(WitchesOvenBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, Vec3 cameraPos) {
        final Direction facing = entity.getBlockState().getValue(WitchesOvenBlock.FACING);
        final NonNullList<ItemStack> extraInventory = entity.getStacksOnTop();
        final int pos = (int) entity.getBlockPos().asLong();
        for (int i = 0; i < extraInventory.size(); i++) {
            final ItemStack food = extraInventory.get(i);
            if (food.isEmpty()) {
                return;
            }
            matrices.pushPose();
            matrices.translate(0.5D, 1.02, 0.5D);
            final Direction dir = Direction.from2DDataValue((i + facing.get2DDataValue()) % 4);
            final float rotation = -dir.toYRot();
            matrices.mulPose(Axis.YP.rotationDegrees(rotation));
            matrices.mulPose(Axis.XP.rotationDegrees(90.0F));
            matrices.translate(-0.20D, -0.20D, 0.0D);
            matrices.scale(0.375F, 0.375F, 0.375F);
            Minecraft.getInstance().getItemRenderer().renderStatic(food, ItemDisplayContext.FIXED, light, overlay, matrices, vertexConsumers, entity.getLevel(), pos + i);
            matrices.popPose();
        }

    }
}
