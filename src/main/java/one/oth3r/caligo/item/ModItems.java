package one.oth3r.caligo.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.entity.ModEntities;

public class ModItems {
    public static final Item STROW_SPAWN_EGG = registerSpawnEgg("strow_spawn_egg",
    public static final Item STROW_SPAWN_EGG = registerItem("strow_spawn_egg",
            new SpawnEggItem(ModEntities.STROW, 0x808080, 0xa0a0a0, new FabricItemSettings()));
    public static final Item STATUE = registerItem("statue",
            new BlockItem(ModBlocks.STATUE_BLOCK, new FabricItemSettings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Caligo.MOD_ID,name),item);
    }
    public static void register() {
        // add the spawn eggs to the spawn eggs tab, make it alphabetical
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
            content.addAfter(Items.STRIDER_SPAWN_EGG,STROW_SPAWN_EGG);
        });
    }
}
