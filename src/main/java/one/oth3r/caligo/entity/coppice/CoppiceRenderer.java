package one.oth3r.caligo.entity.coppice;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.ModModelLayers;

import java.util.Locale;
import java.util.Map;

public class CoppiceRenderer extends AgeableMobEntityRenderer<CoppiceEntity, CoppiceEntityRenderState, CoppiceModel> {
    private static final Map<CoppiceEntity.Variant, Identifier> TEXTURES = Util.make(Maps.newHashMap(), variants -> {
        for (CoppiceEntity.Variant variant : CoppiceEntity.Variant.values()) {
            variants.put(variant, Identifier.of(Caligo.MOD_ID,String.format(Locale.ROOT, "textures/entity/coppice/coppice_%s.png", variant.getName())));
        }
    });

    public CoppiceRenderer(EntityRendererFactory.Context context) {
        super(context, new CoppiceModel(context.getPart(ModModelLayers.COPPICE)), new CoppiceModel(context.getPart(ModModelLayers.COPPICE_BABY)), .3f);
        this.addFeature(new HeldItemFeatureRenderer<>(this,context.getItemRenderer()));
    }

    @Override
    public CoppiceEntityRenderState createRenderState() {
        return new CoppiceEntityRenderState();
    }

    @Override
    public Identifier getTexture(CoppiceEntityRenderState state) {
        return TEXTURES.get(state.variant);
    }

    @Override
    public void updateRenderState(CoppiceEntity coppiceEntity, CoppiceEntityRenderState coppiceEntityRenderState, float f) {
        super.updateRenderState(coppiceEntity, coppiceEntityRenderState, f);

        coppiceEntityRenderState.variant = coppiceEntity.getVariant();
        coppiceEntityRenderState.hasItem = !coppiceEntity.getMainHandStack().isEmpty();
        coppiceEntityRenderState.isPanicking = coppiceEntity.isPanicking();
        coppiceEntityRenderState.eatingAnimationState.copyFrom(coppiceEntity.eatingAnimationState);
        coppiceEntityRenderState.idleAnimationState.copyFrom(coppiceEntity.idleAnimationState);
        coppiceEntityRenderState.holdAnimationState.copyFrom(coppiceEntity.holdAnimationState);
    }
}
