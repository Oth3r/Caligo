package one.oth3r.caligo.loot_table;

import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModLootTables {
    public static final RegistryKey<LootTable> STROW = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Caligo.MOD_ID, "entities/strow"));
    public static final RegistryKey<LootTable> DEEP_STROW = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Caligo.MOD_ID, "entities/deep_strow"));

    public static final RegistryKey<LootTable> COPPICE_RAW_REMAINS = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Caligo.MOD_ID, "entities/coppice/raw_remains"));
    public static final RegistryKey<LootTable> COPPICE_GEM_REMAINS = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Caligo.MOD_ID, "entities/coppice/gem_remains"));
}
