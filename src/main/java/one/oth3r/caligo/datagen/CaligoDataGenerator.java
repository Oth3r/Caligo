package one.oth3r.caligo.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import one.oth3r.caligo.datagen.custom.*;
import one.oth3r.caligo.generation.ModConfiguredFeatures;
import one.oth3r.caligo.generation.ModPlacedFeatures;

public class CaligoDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModBlockTagProvider::new);

        pack.addProvider(ModEntityLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);

        pack.addProvider(ModWorldGenerator::new);

        // STATUES
        pack.addProvider(StatueProviders.ItemTag::new);
        pack.addProvider(StatueProviders.BlockTag::new);
        pack.addProvider(StatueProviders.Model::new);
        pack.addProvider(StatueProviders.Recipe::new);
        pack.addProvider(StatueProviders.BlockLootTable::new);

        // LUMIN CRYSTAL
        pack.addProvider(LuminCrystalProviders.Model::new);
        pack.addProvider(LuminCrystalProviders.Recipe::new);
        pack.addProvider(LuminCrystalProviders.LootTable::new);

        // STROW
        pack.addProvider(StrowProviders.EntityLootTable::new);
        pack.addProvider(StrowProviders.Model::new);

        // COPPICE
        pack.addProvider(CoppiceProviders.ItemTag::new);
        pack.addProvider(CoppiceProviders.BlockTag::new);
        pack.addProvider(CoppiceProviders.EntityLootTable::new);

        // LUSH
        pack.addProvider(LushBiomeProviders.Model::new);
        pack.addProvider(LushBiomeProviders.LootTable::new);

        // ORE REMAINS
        pack.addProvider(OreRemainsProvider.Recipe::new);
        pack.addProvider(OreRemainsProvider.Model::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::boostrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::boostrap);
    }
}


