package one.oth3r.caligo.generation.data.providers.grouped;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.generation.data.providers.ModModelProvider;
import one.oth3r.caligo.item.ModItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

public class LuminCrystalProviders {

    public static final ArrayList<Block> PICKAXE_MINEABLE = new ArrayList<>(Collections.singletonList(ModBlocks.LUMIN_CRYSTAL_BLOCK));

    public static class Model extends FabricModelProvider {

        public Model(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            blockStateModelGenerator.blockStateCollector
                    .accept(VariantsBlockStateSupplier.create(ModBlocks.LUMIN_CRYSTAL_BLOCK,
                                    BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(Caligo.MOD_ID,"block/lumin_crystal")))
                            .coordinate(blockStateModelGenerator.createUpDefaultFacingVariantMap()));
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            itemModelGenerator.register(ModItems.LUMIN_CRYSTAL, ModModelProvider.getBlockItem("lumin_crystal"));
        }

        @Override
        public String getName() {
            return "Lumin Crystal "+super.getName();
        }
    }

    public static class Recipe extends FabricRecipeProvider {

        public Recipe(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
            return new RecipeGenerator(registryLookup, exporter) {
                @Override
                public void generate() {
                    createShaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LUMIN_CRYSTAL_BLOCK)
                            .pattern(" A ").pattern("SES").pattern("SAS")
                            .input('A', Items.AMETHYST_SHARD)
                            .input('S', Items.STONE)
                            .input('E', ModItems.STROW_ESSENCE)
                            .criterion(hasItem(ModItems.STROW_ESSENCE), conditionsFromItem(ModItems.STROW_ESSENCE))
                            .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                            .offerTo(exporter);
                }
            };
        }

        @Override
        public String getName() {
            return "Lumin Crystal Recipe Gen";
        }
    }

    public static class LootTable extends FabricBlockLootTableProvider {

        public LootTable(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.LUMIN_CRYSTAL_BLOCK);
        }

        @Override
        public String getName() {
            return "Lumin Crystal "+super.getName();
        }
    }
}
