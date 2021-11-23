package cf.witcheskitchen.client.model;// Made with Blockbench 4.0.4
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class CuSithEntityModel<T extends Entity> extends EntityModel<T> {
    // This was done for nothing. God fucking damnit.
    private final ModelPart root;

    public CuSithEntityModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData upperBody = root.addChild("upperBody", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 2.0F, -4.0F, 8.0F, 6.0F, 7.0F, new Dilation(0.0F))
                .uv(34, 55).cuboid(-4.0F, 0.0F, -4.0F, 8.0F, 2.0F, 7.0F, new Dilation(0.0F))
                .uv(24, 13).cuboid(-4.0F, 2.0F, 3.0F, 8.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -9.0F, 2.0F, -1.5708F, 0.0F, 0.0F));

        ModelPartData head = upperBody.addChild("head", ModelPartBuilder.create().uv(19, 23).cuboid(-3.0F, -2.0F, -5.0F, 6.0F, 6.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 58).cuboid(-3.0F, 4.0F, -5.0F, 6.0F, 1.0F, 5.0F, new Dilation(0.0F))
                .uv(50, 47).cuboid(-3.0F, -2.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(26, 34).cuboid(-1.5F, 1.0F, -8.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 8.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

        ModelPartData ear1 = head.addChild("ear1", ModelPartBuilder.create().uv(40, 31).cuboid(0.0F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(56, 11).cuboid(0.0F, 5.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.rotation(3.0F, -2.0F, -2.0F));

        ModelPartData ear0 = head.addChild("ear0", ModelPartBuilder.create().uv(41, 5).cuboid(-1.0F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(56, 0).cuboid(-1.0F, 5.0F, -1.5F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.rotation(-3.0F, -2.0F, -2.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 13).cuboid(-4.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 28).cuboid(-4.0F, -2.0F, -5.0F, 6.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(0, 39).cuboid(-1.0F, 0.0F, -2.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 34).cuboid(-1.0F, 0.0F, -5.0F, 2.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 7.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

        ModelPartData leg0 = body.addChild("leg0", ModelPartBuilder.create().uv(26, 41).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, 5.0F, -1.0F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg1 = body.addChild("leg1", ModelPartBuilder.create().uv(41, 21).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 5.0F, -1.0F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg2 = body.addChild("leg2", ModelPartBuilder.create().uv(38, 39).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -6.0F, -1.0F, -1.5708F, 0.0F, 0.0F));

        ModelPartData leg3 = body.addChild("leg3", ModelPartBuilder.create().uv(8, 39).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -6.0F, -1.0F, -1.5708F, 0.0F, 0.0F));

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}