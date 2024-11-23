package one.oth3r.caligo.generation.data.providers.grouped;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.generation.data.providers.ModModelProvider;
import one.oth3r.caligo.item.ModItems;
import one.oth3r.caligo.loot_table.ModLootTables;
import one.oth3r.caligo.tag.ModBlockTags;
import one.oth3r.caligo.tag.ModItemTags;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class CoppiceProviders {
    public static class ItemTag extends FabricTagProvider.ItemTagProvider {

        public ItemTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModItemTags.COPPICE_LOW_TIER)
                    .addOptionalTag(ConventionalItemTags.RAW_ORES)
                    .addOptionalTag(ItemTags.COALS);

            getOrCreateTagBuilder(ModItemTags.COPPICE_HIGH_TIER)
                    .addOptionalTag(ConventionalItemTags.GEMS);

            getOrCreateTagBuilder(ModItemTags.COPPICE_FOOD)
                    .add(ModItems.DRIPLEAF_VINES)
                    .add(Items.BIG_DRIPLEAF)
                    .add(Items.SMALL_DRIPLEAF);
        }

        @Override
        public String getName() {
            return "Coppice "+super.getName();
        }
    }

    public static class BlockTag extends FabricTagProvider.BlockTagProvider {

        public BlockTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModBlockTags.COPPICE_INTERESTS)
                    .add(Blocks.BIG_DRIPLEAF);
        }

        @Override
        public String getName() {
            return "Coppice "+super.getName();
        }
    }

    public static class EntityLootTable extends SimpleFabricLootTableProvider {
        public EntityLootTable(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, LootContextTypes.ENTITY);
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(ModLootTables.COPPICE_RAW_REMAINS, LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(1,2))
                            .with(ItemEntry.builder(ModItems.SMALL_ORE_REMAINS).weight(2))
                            .with(ItemEntry.builder(ModItems.ORE_REMAINS)))
                    .randomSequenceId(ModLootTables.COPPICE_RAW_REMAINS));

            lootTableBiConsumer.accept(ModLootTables.COPPICE_GEM_REMAINS, LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(1,2))
                            .with(ItemEntry.builder(ModItems.ORE_REMAINS).weight(2))
                            .with(ItemEntry.builder(ModItems.LARGE_ORE_REMAINS)))
                    .randomSequenceId(ModLootTables.COPPICE_GEM_REMAINS));
        }

        @Override
        public String getName() {
            return "Coppice "+super.getName();
        }


    }

    public static class Model extends FabricModelProvider {

        public Model(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            // SPAWN EGGS
            itemModelGenerator.register(ModItems.COPPICE_SPAWN_EGG, ModModelProvider.SPAWN_EGG);
        }

        @Override
        public String getName() {
            return "Coppice "+super.getName();
        }
    }
}
