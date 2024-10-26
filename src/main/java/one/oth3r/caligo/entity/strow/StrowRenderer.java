package one.oth3r.caligo.entity.strow;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.ModModelLayers;

public class StrowRenderer extends MobEntityRenderer<StrowEntity, StrowEntityRenderState, StrowModel> {
    private static final Identifier TEXTURE = Identifier.of(Caligo.MOD_ID, "textures/entity/strow/strow.png");
//    private static final Identifier TEXTURE_ANGRY = Identifier.of(SpookieTest.MOD_ID, "textures/entity/strow/strow_angry.png");
    public StrowRenderer(EntityRendererFactory.Context context) {
        super(context, new StrowModel(context.getPart(ModModelLayers.STROW)), 0.2f);
        this.addFeature(new StrowAngryFeatureRenderer<>(this));
    }

    @Override
    public StrowEntityRenderState createRenderState() {
        return new StrowEntityRenderState();
    }

    @Override
    public Identifier getTexture(StrowEntityRenderState state) {
        //        if (entity.isAngry()) return TEXTURE_ANGRY;
        return TEXTURE;
    }

    @Override
    public void updateRenderState(StrowEntity strowEntity, StrowEntityRenderState strowEntityRenderState, float f) {
        super.updateRenderState(strowEntity, strowEntityRenderState, f);

        strowEntityRenderState.isActive = strowEntity.isActive();
        strowEntityRenderState.isAngry = strowEntity.isAngry();
        strowEntityRenderState.activeAnimationState.copyFrom(strowEntity.activeAnimationState);
        strowEntityRenderState.attackAnimationState.copyFrom(strowEntity.attackAnimationState);
    }
}
