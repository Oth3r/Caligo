package one.oth3r.caligo.generation.data.providers.grouped;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.*;
import net.minecraft.registry.*;
import one.oth3r.caligo.generation.data.providers.ModModelProvider;
import one.oth3r.caligo.item.ModItems;
import one.oth3r.caligo.loot_table.ModLootTables;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class StrowProviders {
    private static CompletableFuture<RegistryWrapper.WrapperLookup> regLookup;

    public static class EntityLootTable extends SimpleFabricLootTableProvider {
        public EntityLootTable(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup, LootContextTypes.ENTITY);
            regLookup = registryLookup;
        }

        @Override
        public void accept(RegistryWrapper.WrapperLookup registryLookup, BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            try {
                lootTableBiConsumer.accept(ModLootTables.STROW, LootTable.builder()
                        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1F))
                                .with(ItemEntry.builder(ModItems.STROW_ESSENCE)
                                        .apply(SetCountLootFunction.builder(BinomialLootNumberProvider.create(1, 0.65F)))
                                        .apply(new LootingEnchantLootFunction.Builder(UniformLootNumberProvider.create(0,1)))))
                        .randomSequenceId(ModLootTables.STROW.getRegistry()));


                lootTableBiConsumer.accept(ModLootTables.DEEP_STROW, LootTable.builder()
                        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1F))
                                .with(ItemEntry.builder(ModItems.STROW_ESSENCE)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                        .apply(new LootingEnchantLootFunction.Builder(UniformLootNumberProvider.create(0,1)))))
                        .randomSequenceId(ModLootTables.DEEP_STROW.getRegistry()));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getName() {
            return "Strow "+super.getName();
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
            itemModelGenerator.register(ModItems.STROW_ESSENCE, Models.GENERATED);

            // SPAWN EGGS
            itemModelGenerator.register(ModItems.STROW_SPAWN_EGG, ModModelProvider.SPAWN_EGG);
            itemModelGenerator.register(ModItems.DEEP_STROW_SPAWN_EGG, ModModelProvider.SPAWN_EGG);
        }

        @Override
        public String getName() {
            return "Strow "+super.getName();
        }
    }
}
