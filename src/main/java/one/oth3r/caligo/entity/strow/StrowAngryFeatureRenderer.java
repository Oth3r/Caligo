package one.oth3r.caligo.entity.strow;

import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class StrowAngryFeatureRenderer<T extends StrowEntity, M extends StrowModel<T>> extends FeatureRenderer<T, M> {
    private final RenderLayer LAYER = RenderLayer.getEyes(new Identifier(Caligo.MOD_ID, "textures/entity/strow/strow_emissions.png"));

    public StrowAngryFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, StrowEntity strowEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.LAYER);
        if (strowEntity.isAngry()) {
            ((Model) this.getContextModel()).render(matrices, vertexConsumer, 0xF00000, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
