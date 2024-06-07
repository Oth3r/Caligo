package one.oth3r.caligo.block.statue;

import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import one.oth3r.caligo.block.deepslate_statue.DeepslateStatueBlock;
import one.oth3r.caligo.entity.statue.StatueEntity;
import one.oth3r.caligo.entity.statue.states.StatueCrouchModel;
import one.oth3r.caligo.entity.statue.states.StatueIdleModel;
import one.oth3r.caligo.entity.statue.states.StatueRunModel;
import org.joml.Quaternionf;

public class StatueBlockEntityRenderer implements BlockEntityRenderer<StatueBlockEntity> {

    public StatueBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(StatueBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState blockState = entity.getCachedState();
        matrices.push();
        matrices.translate(0.5, 1.5, 0.5);

        if (blockState.get(StatueBlock.HALF) == DoubleBlockHalf.UPPER) matrices.translate(0, -1, 0);

        float angle = blockState.get(StatueBlock.ROTATION) * -22.5f;
        float angleRadians = (float) Math.toRadians(angle);
        Quaternionf rotation = new Quaternionf(0, MathHelper.sin(angleRadians / 2.0f), 0.0f, MathHelper.cos(angleRadians / 2.0f));
        rotation.normalize();
        matrices.multiply(rotation);

        // Create a Quaternion for flipping
        Quaternionf flipQuaternion = new Quaternionf();
        flipQuaternion.rotationXYZ(0, 0, (float) Math.PI);
        flipQuaternion.normalize();
        matrices.multiply(flipQuaternion);

        // get the model for rendering
        EntityModel<StatueEntity> model;
        switch (blockState.get(StatueBlock.STATE)) {
            default -> model = new StatueIdleModel(StatueIdleModel.getTexturedModelData().createModel());
            case RUN -> model = new StatueRunModel(StatueRunModel.getTexturedModelData().createModel());
            case CROUCH -> model = new StatueCrouchModel(StatueCrouchModel.getTexturedModelData().createModel());
        }

        // change textures based on statue type
        Identifier texture = StatueEntity.TEXTURE_NORMAL;
        if (blockState.getBlock() instanceof DeepslateStatueBlock) texture = StatueEntity.TEXTURE_DEEP;

        // render the model
        RenderLayer renderLayer = RenderLayer.getEntityCutout(texture);
        // if top half, render transparent to stop z fighting
        if (blockState.get(StatueBlock.HALF) == DoubleBlockHalf.UPPER) {
            renderLayer = RenderLayer.getEntityTranslucent(texture);
        }
        model.render(matrices,vertexConsumers.getBuffer(renderLayer),light,overlay,1,1,1,0);
        matrices.pop();
    }
}
