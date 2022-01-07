package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.common.blocks.entity.WitchesOvenBlockEntity;
import cf.witcheskitchen.common.blocks.technical.WitchesOvenBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class WitchesOvenBlockEntityRender implements BlockEntityRenderer<WitchesOvenBlockEntity> {

    @Override
    public void render(WitchesOvenBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final Direction facing = entity.getCachedState().get(WitchesOvenBlock.FACING);
        final DefaultedList<ItemStack> extraInventory = entity.getPassiveInventory();
        final int pos = (int) entity.getPos().asLong();
        for (int i = 0; i < extraInventory.size(); i++) {
            final ItemStack foodAt = extraInventory.get(i);
            if (!foodAt.isEmpty()) {
                matrices.push();
                matrices.translate(0.5D, 1.02, 0.5D);
                final Direction dir = Direction.fromHorizontal((i + facing.getHorizontal()) % 4);
                final float rotation = -dir.asRotation();
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(rotation));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
                matrices.translate(-0.20D, -0.20D, 0.0D);
                matrices.scale(0.375F, 0.375F, 0.375F);
                MinecraftClient.getInstance().getItemRenderer().renderItem(foodAt, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, pos + i);
                matrices.pop();
            }
        }

    }
}
