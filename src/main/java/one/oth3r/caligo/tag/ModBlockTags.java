package one.oth3r.caligo.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModBlockTags {
    public static final TagKey<Block> STATUES = TagKey.of(RegistryKeys.BLOCK, Identifier.of(Caligo.MOD_ID, "statues"));

    public static final TagKey<Block> COPPICE_INTERESTS = TagKey.of(RegistryKeys.BLOCK, Identifier.of(Caligo.MOD_ID, "coppice/interests"));
}
