package one.oth3r.caligo.entity.stulter;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;

public class StulterModel extends EntityModel<EntityRenderState> {

	public StulterModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(1, 32).cuboid(-5.0F, -33.0F, -5.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData bone2 = modelPartData.addChild("bone2", ModelPartBuilder.create().uv(-1, -1).cuboid(-9.0F, -21.0F, -7.0F, 18.0F, 21.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData bone4 = modelPartData.addChild("bone4", ModelPartBuilder.create().uv(52, 34).cuboid(9.0F, -15.0F, -4.0F, 8.0F, 15.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData bone3 = modelPartData.addChild("bone3", ModelPartBuilder.create().uv(0, 54).cuboid(-17.0F, -15.0F, -4.0F, 8.0F, 15.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(EntityRenderState state) {
    }

}
