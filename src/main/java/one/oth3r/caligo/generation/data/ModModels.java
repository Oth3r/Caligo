package one.oth3r.caligo.generation.data;

import net.minecraft.client.data.*;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

import java.util.Optional;

public class ModModels {
    public static final Model STATUE_BLOCK = block("statue_block", TextureKey.ALL);

    // helper method for creating Models
    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(Caligo.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    // helper method for creating Models with variants
    private static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(Caligo.MOD_ID, "block/" + parent)), Optional.of(variant), requiredTextureKeys);
    }

    public static class Textured {
        public static final TexturedModel.Factory STATUE_BLOCK = TexturedModel.makeFactory(TextureMap::all, ModModels.STATUE_BLOCK);
    }
}
