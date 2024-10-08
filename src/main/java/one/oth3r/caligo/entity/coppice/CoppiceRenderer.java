package one.oth3r.caligo.entity.coppice;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.ModModelLayers;

public class CoppiceRenderer extends MobEntityRenderer<CoppiceEntity, CoppiceModel<CoppiceEntity>> {
    private static final Identifier TEXTURE = Identifier.of(Caligo.MOD_ID, "textures/entity/coppice/coppice.png");

    public CoppiceRenderer(EntityRendererFactory.Context context) {
        super(context, new CoppiceModel<>(context.getPart(ModModelLayers.COPPICE)), 0.3f);
        this.addFeature(new HeldItemFeatureRenderer<>(this,context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(CoppiceEntity entity) {
        return TEXTURE;
    }


}
