package one.oth3r.caligo.entity.statue.states;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.entity.ModModelLayers;
import one.oth3r.caligo.entity.statue.StatueEntity;

public class StatueCrouchRenderer extends MobEntityRenderer<StatueEntity, StatueCrouchModel> {
    public StatueCrouchRenderer(EntityRendererFactory.Context context) {
        super(context, new StatueCrouchModel(context.getPart(ModModelLayers.STATUE)), 0.5f);
    }
    @Override
    public Identifier getTexture(StatueEntity entity) {
        return StatueEntity.TEXTURE_NORMAL;
    }
}
