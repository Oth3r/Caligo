package one.oth3r.caligo.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModItemTags {

    public static final TagKey<Item> STATUES = TagKey.of(RegistryKeys.ITEM, Identifier.of(Caligo.MOD_ID, "statues"));

}
