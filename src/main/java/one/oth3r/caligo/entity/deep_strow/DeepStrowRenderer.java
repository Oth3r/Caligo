package one.oth3r.caligo.entity.deep_strow;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.ModModelLayers;
import one.oth3r.caligo.entity.strow.StrowAngryFeatureRenderer;
import one.oth3r.caligo.entity.strow.StrowModel;

public class DeepStrowRenderer extends MobEntityRenderer<DeepStrowEntity, StrowModel<DeepStrowEntity>> {

    private static final Identifier TEXTURE = Identifier.of(Caligo.MOD_ID, "textures/entity/deep_strow/deep_strow.png");

    public DeepStrowRenderer(EntityRendererFactory.Context context) {
        super(context, new StrowModel<>(context.getPart(ModModelLayers.STROW)), 0.2f);
        this.addFeature(new StrowAngryFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(DeepStrowEntity entity) {
        return TEXTURE;
    }
}
