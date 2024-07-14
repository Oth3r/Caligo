package one.oth3r.caligo.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import one.oth3r.caligo.datagen.custom.LuminCrystalProviders;
import one.oth3r.caligo.datagen.custom.StatueProviders;
import one.oth3r.caligo.datagen.custom.StrowProviders;

public class CaligoDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModBlockTagProvider::new);

        pack.addProvider(ModEntityLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);

        // STATUES
        pack.addProvider(StatueProviders.ItemTag::new);
        pack.addProvider(StatueProviders.BlockTag::new);
        pack.addProvider(StatueProviders.Model::new);
        pack.addProvider(StatueProviders.Recipe::new);
        pack.addProvider(StatueProviders.LootTable::new);

        // LUMIN CRYSTAL
        pack.addProvider(LuminCrystalProviders.Model::new);
        pack.addProvider(LuminCrystalProviders.Recipe::new);
        pack.addProvider(LuminCrystalProviders.LootTable::new);

        // STROW
        pack.addProvider(StrowProviders.EntityLootTable::new);
    }
}


