// Made with Blockbench 4.0.4
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class wolf<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "wolf"), "main");
	private final ModelPart cu_sith;

	public wolf(ModelPart root) {
		this.cu_sith = root.getChild("cu_sith");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition cu_sith = partdefinition.addOrReplaceChild("cu_sith", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition upperBody = cu_sith.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 2.0F, -4.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(34, 55).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(24, 13).addBox(-4.0F, 2.0F, 3.0F, 8.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -9.0F, 2.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition head = upperBody.addOrReplaceChild("head", CubeListBuilder.create().texOffs(19, 23).addBox(-3.0F, -2.0F, -5.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 58).addBox(-3.0F, 4.0F, -5.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(50, 47).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 34).addBox(-1.5F, 1.0F, -8.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition ear1 = head.addOrReplaceChild("ear1", CubeListBuilder.create().texOffs(40, 31).addBox(0.0F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(56, 11).addBox(0.0F, 5.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.0F, -2.0F));

		PartDefinition ear0 = head.addOrReplaceChild("ear0", CubeListBuilder.create().texOffs(41, 5).addBox(-1.0F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(56, 0).addBox(-1.0F, 5.0F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.0F, -2.0F));

		PartDefinition body = cu_sith.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-4.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-4.0F, -2.0F, -5.0F, 6.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 39).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 34).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, 2.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(26, 41).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 5.0F, -1.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(41, 21).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 5.0F, -1.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(38, 39).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -6.0F, -1.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(8, 39).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -6.0F, -1.0F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		cu_sith.render(poseStack, buffer, packedLight, packedOverlay);
	}
}