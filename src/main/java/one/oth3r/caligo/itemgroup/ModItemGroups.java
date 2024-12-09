package one.oth3r.caligo.itemgroup;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.item.ModItems;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> CALIGO_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Caligo.MOD_ID, "caligo_group"));
    public static final ItemGroup CALIGO_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.PETUNIA))
            .displayName(Text.translatable("itemGroup.caligo"))
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, CALIGO_GROUP_KEY, CALIGO_GROUP);
        fill();
    }

    /**
     * fills the item groups with items
     */
    private static void fill() {
        ItemGroupEvents.modifyEntriesEvent(CALIGO_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.STATUE);
            itemGroup.add(ModItems.DEEPSLATE_STATUE);
            itemGroup.add(ModItems.STROW_SPAWN_EGG);
            itemGroup.add(ModItems.DEEP_STROW_SPAWN_EGG);
            itemGroup.add(ModItems.STROW_ESSENCE);
            itemGroup.add(ModItems.LUMIN_CRYSTAL);
            itemGroup.add(ModItems.SMALL_ORE_REMAINS);
            itemGroup.add(ModItems.ORE_REMAINS);
            itemGroup.add(ModItems.LARGE_ORE_REMAINS);
            itemGroup.add(ModItems.DRIPLEAF_VINES);
            itemGroup.add(ModItems.LUSH_MARIGOLD);
            itemGroup.add(ModItems.PETUNIA);
            itemGroup.add(ModItems.COPPICE_SPAWN_EGG);

            itemGroup.add(ModItems.FROSTED_STONE);
            itemGroup.add(ModItems.FROSTED_DEEPSLATE);
            itemGroup.add(ModItems.COMPACTED_SNOW);
        });

        // add the spawn eggs to the spawn eggs tab, make it alphabetical
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
            content.addAfter(Items.STRIDER_SPAWN_EGG,ModItems.STROW_SPAWN_EGG);
            content.addAfter(Items.COD_SPAWN_EGG,ModItems.DEEP_STROW_SPAWN_EGG);
            content.addAfter(Items.CREEPER_SPAWN_EGG,ModItems.COPPICE_SPAWN_EGG);
        });
    }
}
