package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.common.block.WitchesOvenBlock;
import cf.witcheskitchen.common.blockentity.WitchesOvenBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class WitchesOvenBlockEntityRender implements BlockEntityRenderer<WitchesOvenBlockEntity> {

    @Override
    public void render(WitchesOvenBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        final Direction facing = entity.getCachedState().get(WitchesOvenBlock.FACING);
        final DefaultedList<ItemStack> extraInventory = entity.getStacksOnTop();
        final int pos = (int) entity.getPos().asLong();
        for (int i = 0; i < extraInventory.size(); i++) {
            final ItemStack food = extraInventory.get(i);
            if (food.isEmpty()) {
                return;
            }
            matrices.push();
            matrices.translate(0.5D, 1.02, 0.5D);
            final Direction dir = Direction.fromHorizontalQuarterTurns((i + facing.getHorizontalQuarterTurns()) % 4);
            final float rotation = -dir.getPositiveHorizontalDegrees();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
            matrices.translate(-0.20D, -0.20D, 0.0D);
            matrices.scale(0.375F, 0.375F, 0.375F);
            MinecraftClient.getInstance().getItemRenderer().renderItem(food, ItemDisplayContext.FIXED, light, overlay, matrices, vertexConsumers, entity.getWorld(), pos + i);
            matrices.pop();
        }

    }
}
