package one.oth3r.caligo.datagen.custom;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.item.ModItems;
import one.oth3r.caligo.tag.ModBlockTags;
import one.oth3r.caligo.tag.ModItemTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class StatueProviders {

    public static final ArrayList<Block> PICKAXE_MINEABLE = new ArrayList<>(Arrays.asList(ModBlocks.STATUE_BLOCK,ModBlocks.DEEPSLATE_STATUE_BLOCK));
    public static final ArrayList<Block> NEEDS_IRON_TOOL = new ArrayList<>(PICKAXE_MINEABLE);

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
            super(output);
        }

        @Override
        public void generate(Consumer<RecipeJsonProvider> exporter) {
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STATUE_BLOCK)
                    .pattern(" E ").pattern("SSS").pattern(" S ")
                    .input('S', Items.STONE)
                    .input('E', ModItems.STROW_ESSENCE)
                    .criterion(FabricRecipeProvider.hasItem(ModItems.STROW_ESSENCE),
                            FabricRecipeProvider.conditionsFromItem(ModItems.STROW_ESSENCE))
                    .offerTo(exporter);

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DEEPSLATE_STATUE_BLOCK)
                    .pattern(" E ").pattern("SSS").pattern(" S ")
                    .input('S', Items.DEEPSLATE)
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

    public static class BlockLootTable extends FabricBlockLootTableProvider {

        public BlockLootTable(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataOutput);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.STATUE_BLOCK, statueDrop(ModBlocks.STATUE_BLOCK));
            addDrop(ModBlocks.DEEPSLATE_STATUE_BLOCK, statueDrop(ModBlocks.DEEPSLATE_STATUE_BLOCK));

        }

        public LootTable.Builder statueDrop(Block statue) {
            return new LootTable.Builder().pool(addSurvivesExplosionCondition(statue, LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(statue)
                            .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS,EntityPredicate.Builder.create().type(EntityType.PLAYER))))));
        }

        @Override
        public String getName() {
            return "Statue "+super.getName();
        }
    }
}
