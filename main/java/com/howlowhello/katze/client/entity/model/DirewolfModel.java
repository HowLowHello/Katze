package com.howlowhello.katze.client.entity.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.howlowhello.katze.entities.DirewolfEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class DirewolfModel <T extends DirewolfEntity> extends EntityModel<T> {
	private final ModelRenderer direwolfHead;
	private final ModelRenderer field_78191_aChild;
	private final ModelRenderer field_78191_aChildChild;
	private final ModelRenderer field_78191_aChild_1;
	private final ModelRenderer field_78191_aChildChild_1;
	private final ModelRenderer field_78191_aChildChildChild;
	private final ModelRenderer field_78191_aChildChildChildChild;
	private final ModelRenderer rightDirewoldArm;
	private final ModelRenderer leftDirewolfArm;
	private final ModelRenderer direwolfHands;
	private final ModelRenderer leftDirewolfLeg;
	private final ModelRenderer field_78189_b0;
	private final ModelRenderer direwolfBody;
	private final ModelRenderer rightDirewolfLeg;

	public DirewolfModel() {
		textureWidth = 64;
		textureHeight = 128;

		direwolfHead = new ModelRenderer(this);
		direwolfHead.setRotationPoint(0.0F, 0.0F, 0.0F);
		direwolfHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);

		field_78191_aChild = new ModelRenderer(this);
		field_78191_aChild.setRotationPoint(0.0F, -2.0F, 0.0F);
		direwolfHead.addChild(field_78191_aChild);
		setRotationAngle(field_78191_aChild, 0.0F, 0.0F, 0.0436F);
		field_78191_aChild.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		field_78191_aChildChild = new ModelRenderer(this);
		field_78191_aChildChild.setRotationPoint(0.0F, -2.0F, 0.0F);
		field_78191_aChild.addChild(field_78191_aChildChild);
		field_78191_aChildChild.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, 0.0F, true);

		field_78191_aChild_1 = new ModelRenderer(this);
		field_78191_aChild_1.setRotationPoint(-5.0F, -10.0313F, -5.0F);
		direwolfHead.addChild(field_78191_aChild_1);
		field_78191_aChild_1.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);

		field_78191_aChildChild_1 = new ModelRenderer(this);
		field_78191_aChildChild_1.setRotationPoint(1.75F, -4.0F, 2.0F);
		field_78191_aChild_1.addChild(field_78191_aChildChild_1);
		setRotationAngle(field_78191_aChildChild_1, -0.0524F, 0.0F, 0.0262F);
		field_78191_aChildChild_1.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F, 0.0F, false);

		field_78191_aChildChildChild = new ModelRenderer(this);
		field_78191_aChildChildChild.setRotationPoint(1.75F, -4.0F, 2.0F);
		field_78191_aChildChild_1.addChild(field_78191_aChildChildChild);
		setRotationAngle(field_78191_aChildChildChild, -0.1047F, 0.0F, 0.0524F);
		field_78191_aChildChildChild.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		field_78191_aChildChildChildChild = new ModelRenderer(this);
		field_78191_aChildChildChildChild.setRotationPoint(5.0F, 12.0313F, 5.0F);
		field_78191_aChildChildChild.addChild(field_78191_aChildChildChildChild);
		setRotationAngle(field_78191_aChildChildChildChild, -0.2094F, 0.0F, 0.1047F);
		field_78191_aChildChildChildChild.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		rightDirewoldArm = new ModelRenderer(this);
		rightDirewoldArm.setRotationPoint(0.0F, 3.0F, -1.0F);
		setRotationAngle(rightDirewoldArm, -0.75F, 0.0F, 0.0F);
		rightDirewoldArm.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);

		leftDirewolfArm = new ModelRenderer(this);
		leftDirewolfArm.setRotationPoint(0.0F, 3.0F, -1.0F);
		setRotationAngle(leftDirewolfArm, -0.75F, 0.0F, 0.0F);
		leftDirewolfArm.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);

		direwolfHands = new ModelRenderer(this);
		direwolfHands.setRotationPoint(0.0F, 3.0F, -1.0F);
		setRotationAngle(direwolfHands, -0.75F, 0.0F, 0.0F);
		direwolfHands.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, false);

		leftDirewolfLeg = new ModelRenderer(this);
		leftDirewolfLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
		leftDirewolfLeg.setTextureOffset(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

		field_78189_b0 = new ModelRenderer(this);
		field_78189_b0.setRotationPoint(0.0F, 0.0F, 0.0F);
		field_78189_b0.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, 0.0F, false);

		direwolfBody = new ModelRenderer(this);
		direwolfBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		direwolfBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, 0.5F, false);

		rightDirewolfLeg = new ModelRenderer(this);
		rightDirewolfLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
		rightDirewolfLeg.setTextureOffset(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.direwolfHead.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.direwolfHead.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.rightDirewolfLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.leftDirewolfLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.rightDirewolfLeg.rotateAngleY = 0.0F;
		this.leftDirewolfLeg.rotateAngleY = 0.0F;
	}



	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		direwolfHead.render(matrixStack, buffer, packedLight, packedOverlay);
		rightDirewoldArm.render(matrixStack, buffer, packedLight, packedOverlay);
		leftDirewolfArm.render(matrixStack, buffer, packedLight, packedOverlay);
		direwolfHands.render(matrixStack, buffer, packedLight, packedOverlay);
		leftDirewolfLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		field_78189_b0.render(matrixStack, buffer, packedLight, packedOverlay);
		direwolfBody.render(matrixStack, buffer, packedLight, packedOverlay);
		rightDirewolfLeg.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

}