package one.oth3r.caligo.entity.statue.states;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.entity.ModModelLayers;
import one.oth3r.caligo.entity.statue.StatueEntity;

public class StatueIdleRenderer extends MobEntityRenderer<StatueEntity, LivingEntityRenderState, StatueIdleModel> {
    public StatueIdleRenderer(EntityRendererFactory.Context context) {
        super(context, new StatueIdleModel(context.getPart(ModModelLayers.STATUE)), 0.5f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return StatueEntity.TEXTURE_NORMAL;
    }
}
