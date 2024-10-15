package one.oth3r.caligo.loot_table;

import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModLootTables {
    public static final Identifier STROW = Identifier.of(Caligo.MOD_ID, "entities/strow");
    public static final Identifier DEEP_STROW = Identifier.of(Caligo.MOD_ID, "entities/deep_strow");

    public static final Identifier COPPICE_RAW_REMAINS = Identifier.of(Caligo.MOD_ID, "entities/coppice/raw_remains");
    public static final Identifier COPPICE_GEM_REMAINS = Identifier.of(Caligo.MOD_ID, "entities/coppice/gem_remains");
}
