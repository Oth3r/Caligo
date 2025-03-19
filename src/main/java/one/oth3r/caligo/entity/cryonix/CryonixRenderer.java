package one.oth3r.caligo.entity.cryonix;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.ZombieEntityRenderState;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.ModModelLayers;

public class CryonixRenderer extends ZombieBaseEntityRenderer<CryonixEntity, ZombieEntityRenderState, CryonixModel> {
    private static final Identifier TEXTURE = Identifier.of(Caligo.MOD_ID, "textures/entity/cryonix.png");
    public CryonixRenderer(EntityRendererFactory.Context context) {
        super(
                context,
                new CryonixModel(context.getPart(ModModelLayers.CRYONIX)),
                new CryonixModel(context.getPart(ModModelLayers.CRYONIX)),
                new CryonixModel(context.getPart(ModModelLayers.CRYONIX)),
                new CryonixModel(context.getPart(ModModelLayers.CRYONIX)),
                new CryonixModel(context.getPart(ModModelLayers.CRYONIX)),
                new CryonixModel(context.getPart(ModModelLayers.CRYONIX))
        );
    }

    @Override
    public Identifier getTexture(ZombieEntityRenderState zombieEntityRenderState) {
        return TEXTURE;
    }

    @Override
    public ZombieEntityRenderState createRenderState() {
        return new ZombieEntityRenderState();
    }
}
