// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package one.oth3r.caligo.entity.coppice;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
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
		this.head = this.body.getChild("head");
		this.leftArm = this.body.getChild("left_arm");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(18, 22).cuboid(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

		ModelPartData left_arm = body.addChild("left_arm", ModelPartBuilder.create().uv(30, 22).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -4.0F, 0.0F));

		ModelPartData right_arm = body.addChild("right_arm", ModelPartBuilder.create().uv(0, 22).cuboid(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, -4.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 22).cuboid(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F))
				.uv(4, 2).cuboid(-5.0F, -9.0F, -5.0F, 10.0F, 10.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

		ModelPartData leaf = head.addChild("leaf", ModelPartBuilder.create().uv(9, 35).cuboid(-3.5F, -13.0F, 0.0F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F))
				.uv(45, 13).cuboid(-0.5F, -9.0F, 0.0F, 1.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r1 = leaf.addChild("cube_r1", ModelPartBuilder.create().uv(7, 39).cuboid(0.0F, 0.0F, 1.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -9.0F, -1.0F, 0.021F, -0.0454F, -0.9139F));

		ModelPartData leaf2 = head.addChild("leaf2", ModelPartBuilder.create().uv(9, 35).cuboid(-3.5F, -13.0F, 0.0F, 4.0F, 4.0F, 0.0F, new Dilation(0.0F))
				.uv(45, 13).cuboid(-0.5F, -9.0F, 0.0F, 1.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		ModelPartData cube_r2 = leaf2.addChild("cube_r2", ModelPartBuilder.create().uv(7, 39).cuboid(0.0F, 0.0F, 1.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -9.0F, -1.0F, 0.021F, -0.0454F, -0.9139F));

		ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(24, 28).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -4.0F, 0.0F));

		ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -4.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(CoppiceEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw,headPitch);

		if (CoppiceBrain.isPanicking(entity)) {
			this.animateMovement(CoppiceAnimations.RUN_SCARED, limbSwing, limbSwingAmount,3f,3f);
		} else if (!entity.getMainHandStack().isEmpty()) {
			this.animateMovement(CoppiceAnimations.RUN_ORE, limbSwing, limbSwingAmount,3f,3f);
		} else {
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
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		root.render(matrices, vertices, light, overlay, color);
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