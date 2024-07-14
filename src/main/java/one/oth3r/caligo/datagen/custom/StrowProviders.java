package one.oth3r.caligo.datagen.custom;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantedCountIncreaseLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.*;
import net.minecraft.registry.*;
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
        public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            try {
                lootTableBiConsumer.accept(ModLootTables.STROW, LootTable.builder()
                        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1F))
                                .with(ItemEntry.builder(ModItems.STROW_ESSENCE)
                                        .apply(SetCountLootFunction.builder(BinomialLootNumberProvider.create(1, 0.65F)))
                                        .apply(new EnchantedCountIncreaseLootFunction.Builder(regLookup.get().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.LOOTING),UniformLootNumberProvider.create(0,1)))))
                        .randomSequenceId(ModLootTables.STROW.getRegistry()));


                lootTableBiConsumer.accept(ModLootTables.DEEP_STROW, LootTable.builder()
                        .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1F))
                                .with(ItemEntry.builder(ModItems.STROW_ESSENCE)
                                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)))
                                        .apply(new EnchantedCountIncreaseLootFunction.Builder(regLookup.get().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.LOOTING),UniformLootNumberProvider.create(0,1)))))
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
}
