package one.oth3r.caligo.entity.strow;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.ModModelLayers;

public class StrowRenderer extends MobEntityRenderer<StrowEntity, StrowModel<StrowEntity>> {
    private static final Identifier TEXTURE = Identifier.of(Caligo.MOD_ID, "textures/entity/strow/strow.png");
//    private static final Identifier TEXTURE_ANGRY = Identifier.of(SpookieTest.MOD_ID, "textures/entity/strow/strow_angry.png");
    public StrowRenderer(EntityRendererFactory.Context context) {
        super(context, new StrowModel<>(context.getPart(ModModelLayers.STROW)), 0.2f);
        this.addFeature(new StrowAngryFeatureRenderer<>(this));
    }

    @Override
    public void render(StrowEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(StrowEntity entity) {
//        if (entity.isAngry()) return TEXTURE_ANGRY;
        return TEXTURE;
    }
}
