package one.oth3r.caligo.entity.stulter;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;

public class StulterModel extends EntityModel<EntityRenderState> {
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone3;

	public StulterModel(ModelPart root) {
        super(root);
        this.bone = root.getChild("bone");
        this.bone2 = this.bone.getChild("bone2");
        this.bone3 = this.bone2.getChild("bone3");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create().uv(13, 13).cuboid(-24.0F, -48.0F, -24.0F, 48.0F, 48.0F, 35.0F, new Dilation(0.0F))
                .uv(142, 96).cuboid(24.0F, -41.0F, -28.0F, 9.0F, 41.0F, 15.0F, new Dilation(0.0F))
                .uv(142, 96).mirrored().cuboid(-33.0F, -41.0F, -28.0F, 9.0F, 41.0F, 15.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData bone2 = bone.addChild("bone2", ModelPartBuilder.create().uv(15, 111).cuboid(-40.0F, -74.0F, 5.0F, 35.0F, 26.0F, 21.0F, new Dilation(0.0F)), ModelTransform.pivot(23.0F, 0.0F, -23.0F));

        ModelPartData bone3 = bone2.addChild("bone3", ModelPartBuilder.create().uv(0, 0).cuboid(-20.0F, -33.0F, 6.0F, 5.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.0F, -48.0F, 6.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(EntityRenderState state) {
    }

}
