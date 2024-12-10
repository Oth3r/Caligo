package one.oth3r.caligo.generation.data.providers.grouped;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.tag.ModBlockTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * everything added to the ice caves biome
 */
public class IceCavesProviders {
    public static final ArrayList<Block> OVERWORLD_CARVER = new ArrayList<>(Arrays.asList(
            ModBlocks.FROSTED_STONE, ModBlocks.FROSTED_DEEPSLATE));
    public static final ArrayList<Block> PICKAXE_MINEABLE = new ArrayList<>(Arrays.asList(ModBlocks.FROSTED_STONE,ModBlocks.FROSTED_DEEPSLATE));
    public static final ArrayList<Block> SHOVEL_MINEABLE = new ArrayList<>(Arrays.asList(ModBlocks.COMPACTED_SNOW));

    public static class Model extends FabricModelProvider {

        public Model(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//            blockStateModelGenerator.registerRandomHorizontalRotations(TexturedModel.CUBE_ALL, ModBlocks.FROSTED_STONE);

            registerDeepslate(blockStateModelGenerator);
            registerStone(blockStateModelGenerator);

            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SNOW_PATH);

            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.COMPACTED_SNOW);
        }

        public static void registerDeepslate(BlockStateModelGenerator blockStateModelGenerator) {
            Block block = ModBlocks.FROSTED_DEEPSLATE;
            Identifier modelId = ModelIds.getBlockModelId(block);
            Identifier identifier = Models.CUBE_COLUMN.upload(block, TextureMap.sideAndEndForTop(block), blockStateModelGenerator.modelCollector);
            Identifier identifier2 = Models.CUBE_COLUMN_MIRRORED.upload(block, TextureMap.sideAndEndForTop(block), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector
                    .accept(BlockStateModelGenerator.createBlockStateWithTwoModelAndRandomInversion(block, identifier, identifier2)
                            .coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));
        }

        public static void registerStone(BlockStateModelGenerator blockStateModelGenerator) {
            Block block = ModBlocks.FROSTED_STONE;
            Identifier identifier = Models.CUBE_ALL.upload(block, TextureMap.all(block), blockStateModelGenerator.modelCollector);
            Identifier identifier2 = Models.CUBE_MIRRORED_ALL.upload(block, TextureMap.all(block), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createBlockStateWithTwoModelAndRandomInversion(block, identifier, identifier2));
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
//            itemModelGenerator.register(ModItems.F, ModModelProvider.getBlockItem("frosted_deepslate"));
        }

        @Override
        public String getName() {
            return "Ice Caves "+super.getName();
        }
    }

    public static class BlockTag extends FabricTagProvider.BlockTagProvider {

        public BlockTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModBlockTags.ICE_CAVES_REPLACEABLE)
                    .add(ModBlocks.FROSTED_DEEPSLATE)
                    .add(ModBlocks.FROSTED_STONE)
                    .add(Blocks.SNOW_BLOCK)
                    .add(ModBlocks.COMPACTED_SNOW);
        }

        @Override
        public String getName() {
            return "Ice Caves "+super.getName();
        }
    }

    public static class Recipe extends FabricRecipeProvider {

        public Recipe(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
            return null;
        }

        @Override
        public String getName() {
            return "Ice Caves Recipes";
        }
    }

    public static class LootTable extends FabricBlockLootTableProvider {

        public LootTable(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.FROSTED_STONE, block -> this.drops(block,Blocks.COBBLESTONE));
            addDrop(ModBlocks.FROSTED_DEEPSLATE, block -> this.drops(block,Blocks.COBBLED_DEEPSLATE));
            addDrop(ModBlocks.COMPACTED_SNOW); // todo compracted snowball drop w/o silk touch

        }

        @Override
        public String getName() {
            return "Ice Caves Loot Tables";
        }
    }
}
