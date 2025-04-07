package one.oth3r.caligo.generation.data.providers.grouped;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.registry.RegistryWrapper;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.tag.ModBlockTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.client.data.BlockStateModelGenerator.createModelVariant;

/**
 * everything added to the ice caves biome
 */
public class IceCavesProviders {
    public static final ArrayList<Block> OVERWORLD_CARVER = new ArrayList<>(Arrays.asList(
            ModBlocks.FROSTED_STONE, ModBlocks.FROSTED_DEEPSLATE));
    public static final ArrayList<Block> PICKAXE_MINEABLE = new ArrayList<>(Arrays.asList(ModBlocks.FROSTED_STONE,ModBlocks.FROSTED_DEEPSLATE,
            ModBlocks.FROZEN_MAGMA_BLOCK));
    public static final ArrayList<Block> SHOVEL_MINEABLE = new ArrayList<>(Arrays.asList(ModBlocks.COMPACTED_SNOW));

    public static class Model {

        public static void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//            blockStateModelGenerator.registerRandomHorizontalRotations(TexturedModel.CUBE_ALL, ModBlocks.FROSTED_STONE);

            registerFrostedDeepslate(blockStateModelGenerator);
            registerFrostedStone(blockStateModelGenerator);

            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SNOW_PATH);

            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.COMPACTED_SNOW);

            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FROZEN_MAGMA_BLOCK);
        }

        public static void registerFrostedDeepslate(BlockStateModelGenerator blockStateModelGenerator) {
            Block block = ModBlocks.FROSTED_DEEPSLATE;
            TextureMap map = TextureMap.sideAndEndForTop(block);
            ModelVariant modelVariant = createModelVariant(Models.CUBE_COLUMN.upload(block, map, blockStateModelGenerator.modelCollector));
            blockStateModelGenerator.blockStateCollector
                    .accept(BlockStateModelGenerator.createDeepslateState(block,
                            modelVariant,TextureMap.sideAndEndForTop(block),blockStateModelGenerator.modelCollector));

        }

        public static void registerFrostedStone(BlockStateModelGenerator blockStateModelGenerator) {
            Block block = ModBlocks.FROSTED_STONE;
            TextureMap map = TextureMap.all(block);
            ModelVariant modelVariant = createModelVariant(Models.CUBE_ALL.upload(block, map, blockStateModelGenerator.modelCollector));
            blockStateModelGenerator.blockStateCollector
                    .accept(BlockStateModelGenerator.createStoneState(block,
                            modelVariant,map,blockStateModelGenerator.modelCollector));
        }

        public static void generateItemModels(ItemModelGenerator itemModelGenerator) {}
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
            // new stone types
            addDrop(ModBlocks.FROSTED_STONE, block -> this.drops(block,Blocks.COBBLESTONE));
            addDrop(ModBlocks.FROSTED_DEEPSLATE, block -> this.drops(block,Blocks.COBBLED_DEEPSLATE));

            addDrop(ModBlocks.FROZEN_MAGMA_BLOCK);
            addDrop(ModBlocks.COMPACTED_SNOW); // todo compracted snowball drop w/o silk touch

        }

        @Override
        public String getName() {
            return "Ice Caves Loot Tables";
        }
    }
}
