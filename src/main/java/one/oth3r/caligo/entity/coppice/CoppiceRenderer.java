package one.oth3r.caligo.entity.coppice;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.ModModelLayers;

public class CoppiceRenderer extends MobEntityRenderer<CoppiceEntity, CoppiceEntityRenderState, CoppiceModel<CoppiceEntity>> {
    private static final Identifier TEXTURE = Identifier.of(Caligo.MOD_ID, "textures/entity/coppice/coppice.png");

    public CoppiceRenderer(EntityRendererFactory.Context context) {
        super(context, new CoppiceModel<>(context.getPart(ModModelLayers.COPPICE)), 0.3f);
        this.addFeature(new HeldItemFeatureRenderer<>(this,context.getItemRenderer()));
    }

    @Override
    public CoppiceEntityRenderState createRenderState() {
        return new CoppiceEntityRenderState();
    }

    @Override
    public Identifier getTexture(CoppiceEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public void updateRenderState(CoppiceEntity coppiceEntity, CoppiceEntityRenderState coppiceEntityRenderState, float f) {
        super.updateRenderState(coppiceEntity, coppiceEntityRenderState, f);
        coppiceEntityRenderState.hasItem = !coppiceEntity.getMainHandStack().isEmpty();
        coppiceEntityRenderState.isPanicking = coppiceEntity.isPanicking();
        coppiceEntityRenderState.eatingAnimationState.copyFrom(coppiceEntity.eatingAnimationState);
        coppiceEntityRenderState.idleAnimationState.copyFrom(coppiceEntity.idleAnimationState);
        coppiceEntityRenderState.holdAnimationState.copyFrom(coppiceEntity.holdAnimationState);
    }
}
