package one.oth3r.caligo.generation.data.providers.grouped;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import one.oth3r.caligo.generation.data.providers.ModModelProvider;
import one.oth3r.caligo.item.ModItems;
import one.oth3r.caligo.loot_table.ModLootTables;
import one.oth3r.caligo.tag.ModBlockTags;
import one.oth3r.caligo.tag.ModItemTags;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;


/**
 * providers for everything coppice;
 * coppice, ore remains
 */
public class CoppiceProviders {
    public static class ItemTag extends FabricTagProvider.ItemTagProvider {

        public ItemTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModItemTags.COPPICE_LOW_TIER)
                    .addOptionalTag(ConventionalItemTags.RAW_MATERIALS)
                    .addOptionalTag(ItemTags.COALS);

            getOrCreateTagBuilder(ModItemTags.COPPICE_HIGH_TIER)
                    .addOptionalTag(ConventionalItemTags.GEMS);

            getOrCreateTagBuilder(ModItemTags.COPPICE_FOOD)
                    .add(ModItems.DRIPLEAF_VINES)
                    .add(Items.BIG_DRIPLEAF)
                    .add(Items.SMALL_DRIPLEAF);
        }

        @Override
        public String getName() {
            return "Coppice "+super.getName();
        }
    }

    public static class BlockTag extends FabricTagProvider.BlockTagProvider {

        public BlockTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(ModBlockTags.COPPICE_INTERESTS)
                    .add(Blocks.BIG_DRIPLEAF);
        }

        @Override
        public String getName() {
            return "Coppice "+super.getName();
        }
    }

    public static class EntityLootTable extends SimpleFabricLootTableProvider {
        public EntityLootTable(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup, LootContextTypes.ENTITY);
        }

        @Override
        public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(ModLootTables.COPPICE_RAW_REMAINS, LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(1,2))
                            .with(ItemEntry.builder(ModItems.SMALL_ORE_REMAINS).weight(2))
                            .with(ItemEntry.builder(ModItems.ORE_REMAINS)))
                    .randomSequenceId(ModLootTables.COPPICE_RAW_REMAINS.getRegistry()));

            lootTableBiConsumer.accept(ModLootTables.COPPICE_GEM_REMAINS, LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(1,2))
                            .with(ItemEntry.builder(ModItems.ORE_REMAINS).weight(2))
                            .with(ItemEntry.builder(ModItems.LARGE_ORE_REMAINS)))
                    .randomSequenceId(ModLootTables.COPPICE_GEM_REMAINS.getRegistry()));
        }

        @Override
        public String getName() {
            return "Coppice "+super.getName();
        }
    }

    public static class Model {
        public static void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

        public static void generateItemModels(ItemModelGenerator itemModelGenerator) {
            // SPAWN EGGS
            itemModelGenerator.registerSpawnEgg(ModItems.COPPICE_SPAWN_EGG, 0x60772b,0xa0a0a0);
            // ore remains
            itemModelGenerator.register(ModItems.SMALL_ORE_REMAINS, Models.GENERATED);
            itemModelGenerator.register(ModItems.ORE_REMAINS, Models.GENERATED);
            itemModelGenerator.register(ModItems.LARGE_ORE_REMAINS, Models.GENERATED);
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
                    offerReversibleCompactingRecipes(
                            RecipeCategory.MISC,ModItems.SMALL_ORE_REMAINS,RecipeCategory.MISC,ModItems.ORE_REMAINS,
                            "ore_remains/"+getRecipeName(ModItems.SMALL_ORE_REMAINS)+"_compact",null,
                            "ore_remains/"+getRecipeName(ModItems.SMALL_ORE_REMAINS)+"_uncompact",null);
                    offerReversibleCompactingRecipes(
                            RecipeCategory.MISC, ModItems.ORE_REMAINS, RecipeCategory.MISC, ModItems.LARGE_ORE_REMAINS,
                            "ore_remains/"+getRecipeName(ModItems.ORE_REMAINS)+"_compact",null,
                            "ore_remains/"+getRecipeName(ModItems.ORE_REMAINS)+"_uncompact",null);

                    // LARGE RECIPES
                    provideOreRemainsRecipe(exporter,Items.DIAMOND,ModItems.LARGE_ORE_REMAINS,9);
                    provideOreRemainsRecipe(exporter,Items.EMERALD,ModItems.LARGE_ORE_REMAINS,6);
                    provideOreRemainsRecipe(exporter,Items.LAPIS_LAZULI,ModItems.LARGE_ORE_REMAINS,3);

                    // NORMAL RECIPES
                    provideOreRemainsRecipe(exporter,Items.RAW_GOLD,ModItems.ORE_REMAINS,6);
                    provideOreRemainsRecipe(exporter,Items.RAW_IRON,ModItems.ORE_REMAINS,3);

                    // SMALL RECIPES
                    provideOreRemainsRecipe(exporter,Items.COAL,ModItems.SMALL_ORE_REMAINS,6);
                    provideOreRemainsRecipe(exporter,Items.RAW_COPPER,ModItems.SMALL_ORE_REMAINS,3);

                }
                private void provideOreRemainsRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, int amount) {
                    createShapeless(RecipeCategory.MISC,output).input(input, amount)
                            .criterion(hasItem(input),conditionsFromItem(input))
                            .offerTo(exporter,"ore_remains/"+getItemPath(output));
                }
            };
        }

        @Override
        public String getName() {
            return "Ore Remains Recipe Gen";
        }
    }
}
