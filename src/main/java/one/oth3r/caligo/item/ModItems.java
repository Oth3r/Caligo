package one.oth3r.caligo.item;

import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.entity.ModEntities;

import java.util.function.Function;

public class ModItems {
    public static final Item STROW_SPAWN_EGG = registerItem("strow_spawn_egg",
            settings -> new SpawnEggItem(ModEntities.STROW, 0x808080, 0xa0a0a0, settings), new Item.Settings());

    public static final Item DEEP_STROW_SPAWN_EGG = registerItem("deep_strow_spawn_egg",
            settings -> new SpawnEggItem(ModEntities.DEEP_STROW, 0x33333b, 0x797979, settings), new Item.Settings());

    public static final Item COPPICE_SPAWN_EGG = registerItem("coppice_spawn_egg",
            settings -> new SpawnEggItem(ModEntities.COPPICE, 0x60772b,0xa0a0a0, settings), new Item.Settings());


    public static final Item STROW_ESSENCE = registerItem("strow_essence");

    public static final Item LARGE_ORE_REMAINS = registerItem("large_ore_remains");

    public static final Item ORE_REMAINS = registerItem("ore_remains");

    public static final Item SMALL_ORE_REMAINS = registerItem("small_ore_remains");

    public static final Item STATUE = registerBlockItem("statue",
            settings -> new BlockItem(ModBlocks.STATUE_BLOCK, settings));

    public static final Item DEEPSLATE_STATUE = registerBlockItem("deepslate_statue",
            settings -> new BlockItem(ModBlocks.DEEPSLATE_STATUE_BLOCK, settings));

    public static final Item LUMIN_CRYSTAL = registerBlockItem("lumin_crystal",
            settings -> new BlockItem(ModBlocks.LUMIN_CRYSTAL_BLOCK, settings));

    public static final Item DRIPLEAF_VINES = registerBlockItem("dripleaf_vines",
            settings -> new BlockItem(ModBlocks.DRIPLEAF_VINES, settings));

    public static final Item PETUNIA = registerBlockItem("petunia",
            settings -> new BlockItem(ModBlocks.PETUNIA, settings));

    public static final Item LUSH_MARIGOLD = registerBlockItem("lush_marigold",
            settings -> new PlaceableOnWaterItem(ModBlocks.LUSH_MARIGOLD, settings));

    private static Item registerItem(String name) {
        return registerItem(name, Item::new, new Item.Settings());
    }

    private static Item registerBlockItem(String name, Function<Item.Settings, Item> factory) {
        return registerItem(name, factory, new Item.Settings().useBlockPrefixedTranslationKey());
    }

    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return Items.register(keyOf(name), factory, settings);
    }

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Caligo.MOD_ID, id));
    }

    public static void register() {

    }
}
