package one.oth3r.caligo.entity.strow.deep;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.strow.StrowEntityRenderState;
import one.oth3r.caligo.entity.strow.StrowRenderer;

public class DeepStrowRenderer extends StrowRenderer {

    private static final Identifier TEXTURE = Identifier.of(Caligo.MOD_ID, "textures/entity/deep_strow/deep_strow.png");

    public DeepStrowRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(StrowEntityRenderState state) {
        return TEXTURE;
    }
}
