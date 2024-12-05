package one.oth3r.caligo.datagen.custom;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.data.server.recipe.RecipeExporter;
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

    public static class Model extends FabricModelProvider {

        public Model(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FROSTED_STONE);

            Identifier modelId = ModelIds.getBlockModelId(ModBlocks.FROSTED_DEEPSLATE);
            Identifier identifier = Models.CUBE_COLUMN.upload(ModBlocks.FROSTED_DEEPSLATE, TextureMap.sideAndEndForTop(ModBlocks.FROSTED_DEEPSLATE), blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector
                    .accept(BlockStateModelGenerator.createBlockStateWithTwoModelAndRandomInversion(ModBlocks.FROSTED_DEEPSLATE, modelId, identifier)
                            .coordinate(BlockStateModelGenerator.createAxisRotatedVariantMap()));

            blockStateModelGenerator.registerParentedItemModel(ModBlocks.FROSTED_DEEPSLATE, identifier);

            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SNOW_PATH);

            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.COMPACTED_SNOW);
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
        public void generate(RecipeExporter exporter) {
        }

        @Override
        public String getName() {
            return "Ice Caves "+super.getName();
        }
    }
}
