package one.oth3r.caligo.entity.strow;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;

public class StrowModel extends EntityModel<StrowEntityRenderState> {
	private final ModelPart head;

	public StrowModel(ModelPart root) {
		super(root);
		this.head = root.getChild("root").getChild("body").getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

		ModelPartData body_r1 = body.addChild("body_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -7.0F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(12, 0).cuboid(-1.05F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(9, 0).cuboid(-3.05F, -1.75F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -5.0F, 0.0F));

		ModelPartData wing0 = body.addChild("wing0", ModelPartBuilder.create(), ModelTransform.pivot(-1.85F, -4.4F, 1.5F));

		ModelPartData wing0_r1 = wing0.addChild("wing0_r1", ModelPartBuilder.create().uv(8, 8).cuboid(-3.9F, -2.35F, 1.0F, 3.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.35F, 0.4F, -1.5F, 0.0F, 0.0F, -0.6545F));

		ModelPartData wing1 = body.addChild("wing1", ModelPartBuilder.create(), ModelTransform.pivot(-1.85F, -4.4F, -1.5F));

		ModelPartData wing1_r1 = wing1.addChild("wing1_r1", ModelPartBuilder.create().uv(0, 8).cuboid(-3.9F, -2.35F, -2.0F, 3.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.35F, 0.4F, 1.5F, 0.0F, 0.0F, -0.6545F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.75F, -0.9F, 0.0F));

		ModelPartData tail2_r1 = tail.addChild("tail2_r1", ModelPartBuilder.create().uv(8, 13).cuboid(2.25F, -1.1F, -1.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(12, 13).cuboid(2.25F, -1.1F, 0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(12, 4).cuboid(2.25F, -1.1F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.75F, 2.9F, 0.0F, 0.0F, 0.0F, -0.9163F));

		ModelPartData leg0 = root.addChild("leg0", ModelPartBuilder.create().uv(0, 13).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -3.0F, -1.0F));

		ModelPartData leg1 = root.addChild("leg1", ModelPartBuilder.create().uv(4, 13).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -3.0F, 1.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(StrowEntityRenderState state) {
		super.setAngles(state);

		this.head.pitch = state.pitch * (float) (Math.PI / 180.0);
		this.head.yaw = state.yawDegrees * (float) (Math.PI / 180.0);

		animateWalking(StrowAnimations.RUN, state.limbFrequency, state.limbAmplitudeMultiplier, 2f, 2.5f);
		animate(state.attackAnimationState, StrowAnimations.PECK, state.age);

		if (state.isAngry) {
			// todo comment
			if (!state.isActive) {
				animate(state.activeAnimationState, StrowAnimations.STARE_ACTIVATE, state.age);
			}

			animate(state.activeAnimationState, StrowAnimations.IDLE_ACTIVE, state.age);
		}
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
	}

}