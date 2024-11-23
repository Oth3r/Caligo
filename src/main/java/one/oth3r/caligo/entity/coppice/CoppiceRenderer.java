package one.oth3r.caligo.entity.coppice;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.ModModelLayers;

import java.util.Locale;
import java.util.Map;

public class CoppiceRenderer extends MobEntityRenderer<CoppiceEntity, CoppiceModel<CoppiceEntity>> {
    private static final Map<CoppiceEntity.Variant, Identifier> TEXTURES = Util.make(Maps.newHashMap(), variants -> {
        for (CoppiceEntity.Variant variant : CoppiceEntity.Variant.values()) {
            variants.put(variant, Identifier.of(Caligo.MOD_ID,String.format(Locale.ROOT, "textures/entity/coppice/coppice_%s.png", variant.getName())));
        }
    });

    public CoppiceRenderer(EntityRendererFactory.Context context) {
        super(context, new CoppiceModel<>(context.getPart(ModModelLayers.COPPICE)), 0.3f);
        this.addFeature(new HeldItemFeatureRenderer<>(this,context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(CoppiceEntity entity) {
        return TEXTURES.get(entity.getVariant());
    }

}
