package one.oth3r.caligo.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModItemTags {

    public static final TagKey<Item> STATUES = TagKey.of(RegistryKeys.ITEM, Identifier.of(Caligo.MOD_ID, "statues"));

    public static final TagKey<Item> COPPICE_LOW_TIER = TagKey.of(RegistryKeys.ITEM, Identifier.of(Caligo.MOD_ID,"coppice/low_tier"));
    public static final TagKey<Item> COPPICE_HIGH_TIER = TagKey.of(RegistryKeys.ITEM, Identifier.of(Caligo.MOD_ID,"coppice/high_tier"));
    public static final TagKey<Item> COPPICE_FOOD = TagKey.of(RegistryKeys.ITEM, Identifier.of(Caligo.MOD_ID,"coppice/food"));

}
