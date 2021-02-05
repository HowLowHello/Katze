package com.howlowhello.katze.client.entity.model;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.howlowhello.katze.entities.IsraDynameEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class IsraDynameModel<T extends IsraDynameEntity> extends EntityModel<T> {
	private final ModelRenderer field_78176_b0;
	private final ModelRenderer field_78176_b1;
	private final ModelRenderer ironGolemLeftArm;
	private final ModelRenderer ironGolemRightArm;
	private final ModelRenderer ironGolemLeftLeg;
	private final ModelRenderer field_78178_a0;
	private final ModelRenderer field_78178_a1;
	private final ModelRenderer ironGolemRightLeg;

	public IsraDynameModel() {
		textureWidth = 128;
		textureHeight = 128;

		field_78176_b0 = new ModelRenderer(this);
		field_78176_b0.setRotationPoint(0.0F, -7.0F, 0.0F);
		field_78176_b0.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, 0.0F, false);

		field_78176_b1 = new ModelRenderer(this);
		field_78176_b1.setRotationPoint(0.0F, -7.0F, 0.0F);
		field_78176_b1.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, 0.5F, false);

		ironGolemLeftArm = new ModelRenderer(this);
		ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
		ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F, false);

		ironGolemRightArm = new ModelRenderer(this);
		ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
		ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F, false);

		ironGolemLeftLeg = new ModelRenderer(this);
		ironGolemLeftLeg.setRotationPoint(-4.0F, 11.0F, 0.0F);
		ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F, false);

		field_78178_a0 = new ModelRenderer(this);
		field_78178_a0.setRotationPoint(0.0F, -7.0F, -2.0F);
		field_78178_a0.setTextureOffset(8, 43).addBox(-4.0F, -12.0F, -5.5F, 8.0F, 10.0F, 8.0F, 0.0F, false);

		field_78178_a1 = new ModelRenderer(this);
		field_78178_a1.setRotationPoint(0.0F, -7.0F, -2.0F);
		field_78178_a1.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		ironGolemRightLeg = new ModelRenderer(this);
		ironGolemRightLeg.setRotationPoint(5.0F, 11.0F, 0.0F);
		ironGolemRightLeg.setTextureOffset(60, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F, true);
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.field_78178_a0.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.field_78178_a0.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.ironGolemLeftLeg.rotateAngleX = -1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F) * limbSwingAmount;
		this.ironGolemRightLeg.rotateAngleX = 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F) * limbSwingAmount;
		this.ironGolemLeftLeg.rotateAngleY = 0.0F;
		this.ironGolemRightLeg.rotateAngleY = 0.0F;
	}

	@Override
	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		int i = entityIn.getAttackTimer();
		if (i > 0) {
			this.ironGolemRightArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
			this.ironGolemLeftArm.rotateAngleX = -2.0F + 1.5F * MathHelper.func_233021_e_((float)i - partialTick, 10.0F);
		} else {
				this.ironGolemRightArm.rotateAngleX = (-0.2F + 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;
				this.ironGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * MathHelper.func_233021_e_(limbSwing, 13.0F)) * limbSwingAmount;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		field_78176_b0.render(matrixStack, buffer, packedLight, packedOverlay);
		field_78176_b1.render(matrixStack, buffer, packedLight, packedOverlay);
		ironGolemLeftArm.render(matrixStack, buffer, packedLight, packedOverlay);
		ironGolemRightArm.render(matrixStack, buffer, packedLight, packedOverlay);
		ironGolemLeftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		field_78178_a0.render(matrixStack, buffer, packedLight, packedOverlay);
		field_78178_a1.render(matrixStack, buffer, packedLight, packedOverlay);
		ironGolemRightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}