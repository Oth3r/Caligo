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
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.generation.data.providers.ModModelProvider;
import one.oth3r.caligo.item.ModItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * everything added to the lush biome
 */
public class LushBiomeProviders {
    public static final ArrayList<Block> HOE_MINEABLE = new ArrayList<>(Arrays.asList(
            ModBlocks.PETUNIA, ModBlocks.PETUNIA_FLOWER, ModBlocks.DRIPLEAF_VINES, ModBlocks.DRIPLEAF_VINES_PLANT,
            ModBlocks.LUSH_MARIGOLD));

    public static class Model extends FabricModelProvider {

        public Model(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            blockStateModelGenerator.registerPlantPart(ModBlocks.DRIPLEAF_VINES_PLANT, ModBlocks.DRIPLEAF_VINES, BlockStateModelGenerator.TintType.NOT_TINTED);
            blockStateModelGenerator.registerPlantPart(ModBlocks.PETUNIA_FLOWER, ModBlocks.PETUNIA, BlockStateModelGenerator.TintType.NOT_TINTED);
            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.POTTED_PETUNIA);

            // marigold
            Identifier[] marigold = new Identifier[]{
                    Identifier.of(Caligo.MOD_ID,"block/marigold_1"),
                    Identifier.of(Caligo.MOD_ID,"block/marigold_2"),
                    Identifier.of(Caligo.MOD_ID,"block/marigold_3"),
                    Identifier.of(Caligo.MOD_ID,"block/marigold_4")
            };

            blockStateModelGenerator.blockStateCollector.accept(ModModelProvider.createFlowerBlockState(ModBlocks.LUSH_MARIGOLD, marigold));
            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.POTTED_LUSH_MARIGOLD);
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            itemModelGenerator.register(ModItems.DRIPLEAF_VINES, Models.GENERATED);
            itemModelGenerator.register(ModItems.LUSH_MARIGOLD, Models.GENERATED);
        }

        @Override
        public String getName() {
            return "Lush Biome "+super.getName();
        }
    }

    public static class LootTable extends FabricBlockLootTableProvider {

        public LootTable(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            addVinePlantDrop(ModBlocks.DRIPLEAF_VINES, ModBlocks.DRIPLEAF_VINES_PLANT);
            addDrop(ModBlocks.LUSH_MARIGOLD);

            // i only want the stem to randomly drop
            addVinePlantDrop(ModBlocks.PETUNIA, ModBlocks.PETUNIA_FLOWER);
            // 100% on the flower
            addDrop(ModBlocks.PETUNIA);
        }

        @Override
        public String getName() {
            return "Lush Biome "+super.getName();
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
                    offerShapelessRecipe(Items.YELLOW_DYE,ModItems.LUSH_MARIGOLD,"yellow_dye",1);
                    offerShapelessRecipe(Items.GREEN_DYE,ModItems.DRIPLEAF_VINES,"green_dye",1);
                    offerShapelessRecipe(Items.MAGENTA_DYE,ModItems.PETUNIA,"magenta_dye",1);
                }
            };
        }

        @Override
        public String getName() {
            return "Lush Biome Recipe Gen";
        }
    }
}
