package one.oth3r.caligo.entity.stulter;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.ModModelLayers;

public class StulterRenderer extends MobEntityRenderer<StulterEntity, LivingEntityRenderState, StulterModel> {
    public StulterRenderer(EntityRendererFactory.Context context) {
        super(context, new StulterModel(context.getPart(ModModelLayers.STULTER)), 1);
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return Identifier.of(Caligo.MOD_ID, "textures/entity/");
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
