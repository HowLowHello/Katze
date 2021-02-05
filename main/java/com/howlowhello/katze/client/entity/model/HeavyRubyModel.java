package com.howlowhello.katze.client.entity.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.howlowhello.katze.entities.HeavyRubyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class HeavyRubyModel <T extends HeavyRubyEntity> extends EntityModel<T> {
	private final ModelRenderer HeavyRubyRightArm;
	private final ModelRenderer HeavyRubyRightArmChild;
	private final ModelRenderer HeavyRubyRightArmChild_1;
	private final ModelRenderer HeavyRubyLeftArm;
	private final ModelRenderer HeavyRubyLeftArmChild;
	private final ModelRenderer HeavyRubyLeftArmChild_1;
	private final ModelRenderer HeavyRubyBody0;
	private final ModelRenderer HeavyRubyBodyChild_1;
	private final ModelRenderer HeavyRubyBodyChild;
	private final ModelRenderer HeavyRubyBodyChild_2;
	private final ModelRenderer HeavyRubyBodyChild_3;
	private final ModelRenderer HeavyRubyBodyChild_4;
	private final ModelRenderer HeavyRubyBody1;
	private final ModelRenderer HeavyRubyHead;
	private final ModelRenderer HeavyRubyHeadChild_1;
	private final ModelRenderer HeavyRubyHeadChild_4;
	private final ModelRenderer HeavyRubyHeadChild_2;
	private final ModelRenderer HeavyRubyHeadChild_3;
	private final ModelRenderer HeavyRubyHeadChild_5;
	private final ModelRenderer HeavyRubyHeadChild;
	private final ModelRenderer HeavyRubyHeadChild_6;
	private final ModelRenderer HeavyRubyRightLeg;
	private final ModelRenderer HeavyRubyLeftLeg;

	public HeavyRubyModel() {
		textureWidth = 128;
		textureHeight = 128;

		HeavyRubyRightArm = new ModelRenderer(this);
		HeavyRubyRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
		HeavyRubyRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F, false);

		HeavyRubyRightArmChild = new ModelRenderer(this);
		HeavyRubyRightArmChild.setRotationPoint(-11.0F, -1.0F, 0.0F);
		HeavyRubyRightArm.addChild(HeavyRubyRightArmChild);
		setRotationAngle(HeavyRubyRightArmChild, 1.8386F, 0.5467F, 1.283F);
		HeavyRubyRightArmChild.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyRightArmChild_1 = new ModelRenderer(this);
		HeavyRubyRightArmChild_1.setRotationPoint(-12.0F, 3.0F, 0.0F);
		HeavyRubyRightArm.addChild(HeavyRubyRightArmChild_1);
		setRotationAngle(HeavyRubyRightArmChild_1, 0.7854F, 0.0F, 0.9599F);
		HeavyRubyRightArmChild_1.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyLeftArm = new ModelRenderer(this);
		HeavyRubyLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
		HeavyRubyLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F, false);

		HeavyRubyLeftArmChild = new ModelRenderer(this);
		HeavyRubyLeftArmChild.setRotationPoint(11.0F, -1.0F, 0.0F);
		HeavyRubyLeftArm.addChild(HeavyRubyLeftArmChild);
		setRotationAngle(HeavyRubyLeftArmChild, 1.8387F, -0.5466F, -1.2831F);
		HeavyRubyLeftArmChild.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyLeftArmChild_1 = new ModelRenderer(this);
		HeavyRubyLeftArmChild_1.setRotationPoint(12.0F, 3.0F, 0.0F);
		HeavyRubyLeftArm.addChild(HeavyRubyLeftArmChild_1);
		setRotationAngle(HeavyRubyLeftArmChild_1, 0.7854F, 0.0F, -0.96F);
		HeavyRubyLeftArmChild_1.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyBody0 = new ModelRenderer(this);
		HeavyRubyBody0.setRotationPoint(0.0F, -7.0F, 0.0F);
		HeavyRubyBody0.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, 0.0F, false);

		HeavyRubyBodyChild_1 = new ModelRenderer(this);
		HeavyRubyBodyChild_1.setRotationPoint(4.0F, 3.0F, 3.0F);
		HeavyRubyBody0.addChild(HeavyRubyBodyChild_1);
		setRotationAngle(HeavyRubyBodyChild_1, 0.9416F, -0.5081F, -1.0117F);
		HeavyRubyBodyChild_1.setTextureOffset(32, 69).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

		HeavyRubyBodyChild = new ModelRenderer(this);
		HeavyRubyBodyChild.setRotationPoint(-4.0F, 3.0F, 3.0F);
		HeavyRubyBody0.addChild(HeavyRubyBodyChild);
		setRotationAngle(HeavyRubyBodyChild, 0.9415F, 0.5081F, 1.0116F);
		HeavyRubyBodyChild.setTextureOffset(32, 69).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

		HeavyRubyBodyChild_2 = new ModelRenderer(this);
		HeavyRubyBodyChild_2.setRotationPoint(6.0F, 5.0F, -5.0F);
		HeavyRubyBody0.addChild(HeavyRubyBodyChild_2);
		setRotationAngle(HeavyRubyBodyChild_2, 2.4083F, -0.6542F, -1.396F);
		HeavyRubyBodyChild_2.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyBodyChild_3 = new ModelRenderer(this);
		HeavyRubyBodyChild_3.setRotationPoint(0.0F, 3.0F, -4.0F);
		HeavyRubyBody0.addChild(HeavyRubyBodyChild_3);
		setRotationAngle(HeavyRubyBodyChild_3, -0.7854F, -0.6109F, 1.5708F);
		HeavyRubyBodyChild_3.setTextureOffset(32, 69).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

		HeavyRubyBodyChild_4 = new ModelRenderer(this);
		HeavyRubyBodyChild_4.setRotationPoint(-6.0F, 5.0F, -5.0F);
		HeavyRubyBody0.addChild(HeavyRubyBodyChild_4);
		setRotationAngle(HeavyRubyBodyChild_4, 2.4082F, 0.6543F, 1.3959F);
		HeavyRubyBodyChild_4.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyBody1 = new ModelRenderer(this);
		HeavyRubyBody1.setRotationPoint(0.0F, -7.0F, 0.0F);
		HeavyRubyBody1.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, 0.5F, false);

		HeavyRubyHead = new ModelRenderer(this);
		HeavyRubyHead.setRotationPoint(0.0F, -7.0F, -1.0F);
		HeavyRubyHead.setTextureOffset(0, 10).addBox(-6.0F, -14.0F, -6.5F, 12.0F, 12.0F, 12.0F, 0.0F, false);

		HeavyRubyHeadChild_1 = new ModelRenderer(this);
		HeavyRubyHeadChild_1.setRotationPoint(4.0F, -9.0F, 1.0F);
		HeavyRubyHead.addChild(HeavyRubyHeadChild_1);
		setRotationAngle(HeavyRubyHeadChild_1, -0.0273F, 0.7637F, -0.7646F);
		HeavyRubyHeadChild_1.setTextureOffset(0, 0).addBox(0.0F, -4.0F, 0.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyHeadChild_4 = new ModelRenderer(this);
		HeavyRubyHeadChild_4.setRotationPoint(4.0F, -3.0F, -4.0F);
		HeavyRubyHead.addChild(HeavyRubyHeadChild_4);
		setRotationAngle(HeavyRubyHeadChild_4, 0.3754F, -0.9521F, 0.1812F);
		HeavyRubyHeadChild_4.setTextureOffset(0, 0).addBox(0.0F, -4.0F, 0.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyHeadChild_2 = new ModelRenderer(this);
		HeavyRubyHeadChild_2.setRotationPoint(4.0F, -6.0F, 5.0F);
		HeavyRubyHead.addChild(HeavyRubyHeadChild_2);
		setRotationAngle(HeavyRubyHeadChild_2, -0.5411F, -0.7703F, 1.3989F);
		HeavyRubyHeadChild_2.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyHeadChild_3 = new ModelRenderer(this);
		HeavyRubyHeadChild_3.setRotationPoint(-4.0F, -9.0F, 1.0F);
		HeavyRubyHead.addChild(HeavyRubyHeadChild_3);
		setRotationAngle(HeavyRubyHeadChild_3, -0.0274F, -0.7637F, 0.7647F);
		HeavyRubyHeadChild_3.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, 0.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyHeadChild_5 = new ModelRenderer(this);
		HeavyRubyHeadChild_5.setRotationPoint(-4.0F, -6.0F, 5.0F);
		HeavyRubyHead.addChild(HeavyRubyHeadChild_5);
		setRotationAngle(HeavyRubyHeadChild_5, -1.0297F, -0.7703F, 1.7427F);
		HeavyRubyHeadChild_5.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyHeadChild = new ModelRenderer(this);
		HeavyRubyHeadChild.setRotationPoint(-4.0F, -3.0F, -4.0F);
		HeavyRubyHead.addChild(HeavyRubyHeadChild);
		setRotationAngle(HeavyRubyHeadChild, -0.5464F, -0.2909F, 1.0257F);
		HeavyRubyHeadChild.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, 0.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyHeadChild_6 = new ModelRenderer(this);
		HeavyRubyHeadChild_6.setRotationPoint(0.0F, -13.0F, 5.0F);
		HeavyRubyHead.addChild(HeavyRubyHeadChild_6);
		setRotationAngle(HeavyRubyHeadChild_6, 2.3561F, 0.7854F, 1.5707F);
		HeavyRubyHeadChild_6.setTextureOffset(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		HeavyRubyRightLeg = new ModelRenderer(this);
		HeavyRubyRightLeg.setRotationPoint(5.0F, 11.0F, 0.0F);
		HeavyRubyRightLeg.setTextureOffset(60, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F, true);

		HeavyRubyLeftLeg = new ModelRenderer(this);
		HeavyRubyLeftLeg.setRotationPoint(-4.0F, 11.0F, 0.0F);
		HeavyRubyLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.HeavyRubyHead.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.HeavyRubyHead.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.HeavyRubyLeftLeg.rotateAngleX = -1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F) * limbSwingAmount;
		this.HeavyRubyRightLeg.rotateAngleX = 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F) * limbSwingAmount;
		this.HeavyRubyLeftLeg.rotateAngleY = 0.0F;
		this.HeavyRubyRightLeg.rotateAngleY = 0.0F;
	}

	@Override
	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		int i = entityIn.getAttackTimer();
		if (i > 0) {
			this.HeavyRubyRightArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
			this.HeavyRubyLeftArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
		} else {
			this.HeavyRubyRightArm.rotateAngleX = (-0.2F + 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;
			this.HeavyRubyLeftArm.rotateAngleX = (-0.2F - 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;
		}
	}


	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		HeavyRubyRightArm.render(matrixStack, buffer, packedLight, packedOverlay);
		HeavyRubyLeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
		HeavyRubyBody0.render(matrixStack, buffer, packedLight, packedOverlay);
		HeavyRubyBody1.render(matrixStack, buffer, packedLight, packedOverlay);
		HeavyRubyHead.render(matrixStack, buffer, packedLight, packedOverlay);
		HeavyRubyRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		HeavyRubyLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

}