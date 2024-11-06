package one.oth3r.caligo.entity;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.entity.model.ModelTransformer;

import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

public record AnimatedBabyModelTransformer(
        boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, float bodyYOffset, Set<String> headParts
) implements ModelTransformer {
    @Override
    public ModelData apply(ModelData modelData) {
        float headScale = this.scaleHead ? this.babyHeadScale : 1.0F;
        float bodyScale = this.babyBodyScale;
        UnaryOperator<ModelTransform> headScaling = modelTransform -> modelTransform.addPivot(0.0F, this.babyYHeadOffset, this.babyZHeadOffset).scaled(headScale);
        UnaryOperator<ModelTransform> bodyScaling = modelTransform -> modelTransform.addPivot(0.0F, this.bodyYOffset, 0.0F).scaled(bodyScale);

        for (Map.Entry<String, ModelPartData> entry : modelData.getRoot().getChild("root").getChildren()) {
            String entryName = entry.getKey();
            ModelPartData modelPartData = entry.getValue();
            modelData.getRoot().getChild("root").addChild(entryName, modelPartData.applyTransformer(this.headParts.contains(entryName) ? headScaling : bodyScaling));
        }

        return modelData;
    }
}
