package one.oth3r.caligo.datagen.custom;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.block.statue.StatueBlock;
import one.oth3r.caligo.item.ModItems;
import one.oth3r.caligo.tag.ModBlockTags;
import one.oth3r.caligo.tag.ModItemTags;

import java.util.concurrent.CompletableFuture;

public class StatueProviders {

    public static class ItemTag extends FabricTagProvider.ItemTagProvider {

        public ItemTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModItemTags.STATUES)
                    .add(ModItems.STATUE)
                    .add(ModItems.DEEPSLATE_STATUE);
        }

        @Override
        public String getName() {
            return "Statue "+super.getName();
        }
    }

    public static class BlockTag extends FabricTagProvider.BlockTagProvider {

        public BlockTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModBlockTags.STATUES)
                    .add(ModBlocks.STATUE_BLOCK)
                    .add(ModBlocks.DEEPSLATE_STATUE_BLOCK);

            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                    .add(ModBlocks.STATUE_BLOCK)
                    .add(ModBlocks.DEEPSLATE_STATUE_BLOCK);

            getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                    .add(ModBlocks.STATUE_BLOCK)
                    .add(ModBlocks.DEEPSLATE_STATUE_BLOCK);
        }

        @Override
        public String getName() {
            return "Statue "+super.getName();
        }
    }

    public static class Model extends FabricModelProvider {

        public Model(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STATUE_BLOCK);
            blockStateModelGenerator.excludeFromSimpleItemModelGeneration(ModBlocks.STATUE_BLOCK);
            blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_STATUE_BLOCK);
            blockStateModelGenerator.excludeFromSimpleItemModelGeneration(ModBlocks.DEEPSLATE_STATUE_BLOCK);
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        }

        @Override
        public String getName() {
            return "Statue "+super.getName();
        }
    }

    public static class Recipe extends FabricRecipeProvider {

        public Recipe(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void generate(RecipeExporter exporter) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STATUE_BLOCK)
                    .pattern(" E ").pattern("SSS").pattern(" S ")
                    .input('S', Blocks.STONE)
                    .input('E', ModItems.STROW_ESSENCE)
                    .criterion(FabricRecipeProvider.hasItem(ModItems.STROW_ESSENCE),
                            FabricRecipeProvider.conditionsFromItem(ModItems.STROW_ESSENCE))
                    .offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DEEPSLATE_STATUE_BLOCK)
                    .pattern(" E ").pattern("SSS").pattern(" S ")
                    .input('S', Blocks.DEEPSLATE)
                    .input('E', ModItems.STROW_ESSENCE)
                    .criterion(FabricRecipeProvider.hasItem(ModItems.STROW_ESSENCE),
                            FabricRecipeProvider.conditionsFromItem(ModItems.STROW_ESSENCE))
                    .offerTo(exporter);
        }

        @Override
        public String getName() {
            return "Statue "+super.getName();
        }
    }

    public static class LootTable extends FabricBlockLootTableProvider {

        public LootTable(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput, registryLookup);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.STATUE_BLOCK, dropsWithProperty(ModBlocks.STATUE_BLOCK, StatueBlock.HALF, DoubleBlockHalf.LOWER));
            addDrop(ModBlocks.DEEPSLATE_STATUE_BLOCK, dropsWithProperty(ModBlocks.DEEPSLATE_STATUE_BLOCK, StatueBlock.HALF, DoubleBlockHalf.LOWER));
        }

        @Override
        public String getName() {
            return "Statue "+super.getName();
        }
    }
}
