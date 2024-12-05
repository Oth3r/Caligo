package one.oth3r.caligo.entity;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModModelLayers {
    public static final EntityModelLayer STROW =
            new EntityModelLayer(Identifier.of(Caligo.MOD_ID, "strow"), "main");

    public static final EntityModelLayer DEEP_STROW =
            new EntityModelLayer(Identifier.of(Caligo.MOD_ID, "deep_strow"), "main");

    public static final EntityModelLayer STATUE =
            new EntityModelLayer(Identifier.of(Caligo.MOD_ID, "statue"), "main");

    public static final EntityModelLayer COPPICE =
            new EntityModelLayer(Identifier.of(Caligo.MOD_ID, "coppice"), "main");

    public static final EntityModelLayer COPPICE_BABY =
            new EntityModelLayer(Identifier.of(Caligo.MOD_ID, "coppice_baby"), "main");
}
