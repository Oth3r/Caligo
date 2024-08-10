package one.oth3r.caligo.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.entity.ModEntities;

public class ModItems {
    public static final Item STROW_ESSENCE = registerItem("strow_essence",
            new Item(new Item.Settings()));

    public static final Item LARGE_ORE_REMAINS = registerItem("large_ore_remains",
            new Item(new Item.Settings()));

    public static final Item ORE_REMAINS = registerItem("ore_remains",
            new Item(new Item.Settings()));

    public static final Item SMALL_ORE_REMAINS = registerItem("small_ore_remains",
            new Item(new Item.Settings()));

    public static final Item STROW_SPAWN_EGG = registerItem("strow_spawn_egg",
            new SpawnEggItem(ModEntities.STROW, 0x808080, 0xa0a0a0, new Item.Settings()));

    public static final Item DEEP_STROW_SPAWN_EGG = registerItem("deep_strow_spawn_egg",
            new SpawnEggItem(ModEntities.DEEP_STROW, 0x33333b, 0x797979, new Item.Settings()));

    public static final Item STATUE = registerItem("statue",
            new BlockItem(ModBlocks.STATUE_BLOCK, new Item.Settings()));

    public static final Item DEEPSLATE_STATUE = registerItem("deepslate_statue",
            new BlockItem(ModBlocks.DEEPSLATE_STATUE_BLOCK, new Item.Settings()));

    public static final Item LUMIN_CRYSTAL = registerItem("lumin_crystal",
            new BlockItem(ModBlocks.LUMIN_CRYSTAL_BLOCK, new Item.Settings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Caligo.MOD_ID,name),item);
    }

    public static void register() {
        // add the spawn eggs to the spawn eggs tab, make it alphabetical
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
            content.addAfter(Items.STRIDER_SPAWN_EGG,STROW_SPAWN_EGG);
            content.addAfter(Items.CREEPER_SPAWN_EGG,DEEP_STROW_SPAWN_EGG);
        });
    }
}
