package one.oth3r.caligo.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipieProvider extends FabricRecipeProvider {
    public ModRecipieProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void provideShapelessRecipe(RecipeExporter exporter, ItemConvertible input, int iAmt, ItemConvertible output, int oAmt) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC,output, oAmt).input(input, iAmt)
                .criterion(hasItem(input),conditionsFromItem(input))
                .offerTo(exporter);
    }

    @Override
    public void generate(RecipeExporter exporter) {

    }
}
