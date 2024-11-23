// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package one.oth3r.caligo.entity.coppice;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class CoppiceModel<T extends CoppiceEntity> extends SinglePartEntityModel<T> implements ModelWithArms {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart leftArm;

	public CoppiceModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.head = this.root.getChild("head");
		this.leftArm = this.body.getChild("left_arm");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 22).cuboid(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F))
				.uv(4, 2).cuboid(-5.0F, -9.0F, -5.0F, 10.0F, 10.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 0.0F));

		ModelPartData stem = head.addChild("stem", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -9.0F, 0.0F));

		ModelPartData cube_r1 = stem.addChild("cube_r1", ModelPartBuilder.create().uv(45, 13).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

		ModelPartData cube_r2 = stem.addChild("cube_r2", ModelPartBuilder.create().uv(45, 13).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData stem1 = stem.addChild("stem1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r3 = stem1.addChild("cube_r3", ModelPartBuilder.create().uv(45, 13).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r4 = stem1.addChild("cube_r4", ModelPartBuilder.create().uv(45, 13).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

		ModelPartData stem2 = stem1.addChild("stem2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r5 = stem2.addChild("cube_r5", ModelPartBuilder.create().uv(45, 13).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -3.1416F, -0.7854F, 3.1416F));

		ModelPartData cube_r6 = stem2.addChild("cube_r6", ModelPartBuilder.create().uv(45, 13).cuboid(-0.5F, -3.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData leaf0 = stem2.addChild("leaf0", ModelPartBuilder.create().uv(9, 35).cuboid(-3.5F, -4.0F, 0.0F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData leaf1 = stem2.addChild("leaf1", ModelPartBuilder.create().uv(7, 39).cuboid(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.35F, -3.0F, 0.35F, 0.6155F, -0.5236F, -0.9553F));

		ModelPartData leaf2 = stem2.addChild("leaf2", ModelPartBuilder.create().uv(9, 35).cuboid(-3.5F, -4.0F, 0.0F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

		ModelPartData leaf3 = stem2.addChild("leaf3", ModelPartBuilder.create().uv(7, 39).cuboid(0.0F, 0.0F, -0.025F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.375F, -3.0F, 0.325F, 2.5586F, -0.5032F, -2.2023F));

		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(18, 22).cuboid(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

		ModelPartData left_arm = body.addChild("left_arm", ModelPartBuilder.create().uv(30, 22).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -4.0F, 0.0F));

		ModelPartData right_arm = body.addChild("right_arm", ModelPartBuilder.create().uv(0, 22).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, -4.0F, 0.0F));

		ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(24, 28).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -4.0F, 0.0F));

		ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -4.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(CoppiceEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw,headPitch);

		// if holding
		if (!entity.getMainHandStack().isEmpty()) {
			this.animateMovement(CoppiceAnimations.WALK_HOLDING, limbSwing, limbSwingAmount,3f,3f);
		}
		// else if panicking
		else if (entity.isPanicking()) {
			this.animateMovement(CoppiceAnimations.PANIC, limbSwing, limbSwingAmount,3f,3f);
		}
		// else normal
		else {
			this.animateMovement(CoppiceAnimations.WALK, limbSwing, limbSwingAmount, 3f, 3f);
		}

		this.updateAnimation(entity.eatingAnimationState, CoppiceAnimations.EATING, ageInTicks, 1f);

		this.updateAnimation(entity.idleAnimationState, CoppiceAnimations.IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.holdAnimationState, CoppiceAnimations.HOLD, ageInTicks, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * ((float)Math.PI / 180) ;
		this.head.pitch = headPitch * ((float)Math.PI / 180) * -1;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		ImmutableList<ModelPart>
				head = ImmutableList.of(this.head),
				body = ImmutableList.of(this.body,this.root.getChild("left_leg"),root.getChild("right_leg"));

		if (this.child) {
			matrices.push();
			float f = 0.7f;
			matrices.scale(-f, f, -f);
			matrices.translate(0,2.289,0);
			head.forEach(modelPart -> modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
			matrices.pop();

			matrices.push();
			f = 0.5f;
			matrices.scale(-f, f, -f);
			matrices.translate(0,3,0);
			body.forEach(modelPart -> modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
			matrices.pop();
		}
		else {
			root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		}
	}

	@Override
	public ModelPart getPart() {
		return root;
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		this.root.rotate(matrices);
		this.body.rotate(matrices);

		matrices.translate(0f,-.16f,0);
		matrices.multiply(RotationAxis.POSITIVE_X.rotation(this.leftArm.pitch));
		matrices.scale(0.5F, 0.5F, 0.5F);
		matrices.translate(.05F, -.2f, .55F);
	}
}