package one.oth3r.caligo.block.statue;

import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
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
        // renders double but oh well
        if (blockState.get(StatueBlock.HALF) == DoubleBlockHalf.UPPER) matrices.translate(0, -1, 0);;
        float angle = blockState.get(StatueBlock.ROTATION)*-22.5f;
        float angleRadians = (float) Math.toRadians(angle);
        Quaternionf rotation = new Quaternionf(0, MathHelper.sin(angleRadians / 2.0f), 0.0f, MathHelper.cos(angleRadians / 2.0f));
        rotation.normalize();
        matrices.multiply(rotation);

        // Create a Quaternion for flipping
        Quaternionf flipQuaternion = new Quaternionf();
        flipQuaternion.rotationXYZ(0, 0, (float) Math.PI);
        flipQuaternion.normalize();
        matrices.multiply(flipQuaternion);


        EntityModel<StatueEntity> model;
        if (blockState.get(StatueBlock.STATE)==2)
            model = new StatueCrouchModel(StatueCrouchModel.getTexturedModelData().createModel());
        else if (blockState.get(StatueBlock.STATE)==1)
            model = new StatueRunModel(StatueRunModel.getTexturedModelData().createModel());
        else
            model = new StatueIdleModel(StatueIdleModel.getTexturedModelData().createModel());


        model.render(matrices,vertexConsumers.getBuffer(RenderLayer.getEntityCutout(StatueEntity.TEXTURE)),light,overlay,1,1,1,1);
//        MinecraftClient.getInstance().getItemRenderer().renderItem(new ItemStack(blockState.getBlock().asItem()), ModelTransformationMode.GROUND,light,overlay,matrices,vertexConsumers,entity.getWorld(),0);
        matrices.pop();
    }
}
