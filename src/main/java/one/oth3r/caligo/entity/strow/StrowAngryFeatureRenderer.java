package one.oth3r.caligo.entity.strow;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.SpiderEyesFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class StrowAngryFeatureRenderer<T extends StrowEntityRenderState, M extends StrowModel> extends FeatureRenderer<T, M> {
    private final RenderLayer LAYER = RenderLayer.getEyes(Identifier.of(Caligo.MOD_ID, "textures/entity/strow/strow_emissions.png"));

    public StrowAngryFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T state, float limbAngle, float limbDistance) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.LAYER);
        if (state.isAngry) {
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }
}
