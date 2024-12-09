package one.oth3r.caligo.generation.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import one.oth3r.caligo.generation.data.providers.grouped.*;
import one.oth3r.caligo.generation.data.providers.ModEntityLootTableProvider;
import one.oth3r.caligo.generation.data.providers.ModModelProvider;
import one.oth3r.caligo.generation.data.providers.ModTagProvider;
import one.oth3r.caligo.generation.world.ModCarvers;
import one.oth3r.caligo.generation.world.ModWorldGenerator;
import one.oth3r.caligo.generation.world.biome.ModBiomes;
import one.oth3r.caligo.generation.world.features.ModConfiguredFeatures;
import one.oth3r.caligo.generation.world.features.ModPlacedFeatures;

public class CaligoDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModTagProvider.ItemTag::new);
        pack.addProvider(ModTagProvider.BlockTag::new);

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
        pack.addProvider(CoppiceProviders.Model::new);
        pack.addProvider(CoppiceProviders.EntityLootTable::new);

        // LUSH
        pack.addProvider(LushBiomeProviders.Model::new);
        pack.addProvider(LushBiomeProviders.LootTable::new);
        pack.addProvider(LushBiomeProviders.Recipe::new);

        // ORE REMAINS
        pack.addProvider(OreRemainsProvider.Recipe::new);
        pack.addProvider(OreRemainsProvider.Model::new);

        // ICE CAVES
        pack.addProvider(IceCavesProviders.Model::new);
        pack.addProvider(IceCavesProviders.BlockTag::new);
        pack.addProvider(IceCavesProviders.LootTable::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::boostrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::boostrap);
        registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::boostrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_CARVER, ModCarvers::bootstrap);
    }
}


