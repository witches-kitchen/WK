package cf.witcheskitchen.client.render.blockentity;

import cf.witcheskitchen.common.block.WitchesOvenBlock;
import cf.witcheskitchen.common.blockentity.BrewingBarrelBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Axis;
import net.minecraft.util.math.Direction;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
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
                matrices.multiply(Axis.Y_POSITIVE.rotationDegrees(90));
            }
            case WEST -> {
                offsetX -= 1.0;
                matrices.multiply(Axis.Y_POSITIVE.rotationDegrees(90));
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
