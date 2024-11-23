package one.oth3r.caligo.generation.data.providers.grouped;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import one.oth3r.caligo.item.ModItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class OreRemainsProvider {
    public static class Recipe extends FabricRecipeProvider {

        public Recipe(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output);
        }

        private void provideOreRemainsRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input, int amount) {
            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC,output).input(input, amount)
                    .criterion(hasItem(input),conditionsFromItem(input))
                    .offerTo(exporter,"ore_remains/"+getItemPath(output));
        }

        @Override
        public void generate(Consumer<RecipeJsonProvider> exporter) {
            offerReversibleCompactingRecipes(exporter,RecipeCategory.MISC,ModItems.SMALL_ORE_REMAINS,RecipeCategory.MISC,ModItems.ORE_REMAINS,
                    "ore_remains/"+getRecipeName(ModItems.SMALL_ORE_REMAINS)+"_compact",null,
                    "ore_remains/"+getRecipeName(ModItems.SMALL_ORE_REMAINS)+"_uncompact",null);
            offerReversibleCompactingRecipes(exporter,RecipeCategory.MISC,ModItems.ORE_REMAINS,RecipeCategory.MISC,ModItems.LARGE_ORE_REMAINS,
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

        @Override
        public String getName() {
            return "Ore Remains "+super.getName();
        }
    }

    public static class Model extends FabricModelProvider {

        public Model(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {}

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            itemModelGenerator.register(ModItems.SMALL_ORE_REMAINS, Models.GENERATED);
            itemModelGenerator.register(ModItems.ORE_REMAINS, Models.GENERATED);
            itemModelGenerator.register(ModItems.LARGE_ORE_REMAINS, Models.GENERATED);
        }

        @Override
        public String getName() {
            return "Ore Remains "+super.getName();
        }
    }
}
