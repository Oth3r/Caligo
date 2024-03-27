package one.oth3r.caligo.entity;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModModelLayers {
    public static final EntityModelLayer STROW =
            new EntityModelLayer(new Identifier(Caligo.MOD_ID, "strow"), "main");
    public static final EntityModelLayer STATUE =
            new EntityModelLayer(new Identifier(Caligo.MOD_ID, "statue"), "main");
}
