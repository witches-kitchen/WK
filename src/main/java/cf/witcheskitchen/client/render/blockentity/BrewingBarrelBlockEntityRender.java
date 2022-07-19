package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.common.blocks.entity.BrewingBarrelBlockEntity;
import cf.witcheskitchen.common.blocks.device.WitchesOvenBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class BrewingBarrelBlockEntityRender implements BlockEntityRenderer<BrewingBarrelBlockEntity> {

    @Override
    public void render(BrewingBarrelBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final Direction facing = entity.getCachedState().get(WitchesOvenBlock.FACING);
        final ItemStack stack = entity.getRenderStack();
        if (stack.isEmpty()) {
            return;
        }
        matrices.push();
        double offsetZ = 0;
        double offsetX = 0;
        switch (facing) {
            case SOUTH -> offsetZ += 1.05;
            case EAST -> {
                offsetX -= 1.0;
                offsetZ += 1.05;
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
            }
            case WEST -> {
                offsetX -= 1.0;
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
            }
            default -> {
            }
        }

        matrices.translate(0.51 + offsetX, 0.2, -0.02 + offsetZ);
        matrices.scale(0.375F, 0.375F, 0.375F);
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, 0);
        matrices.pop();
    }

}
