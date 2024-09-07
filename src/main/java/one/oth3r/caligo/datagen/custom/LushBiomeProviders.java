package one.oth3r.caligo.datagen.custom;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.client.*;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.datagen.ModModelProvider;
import one.oth3r.caligo.item.ModItems;

import java.util.concurrent.CompletableFuture;

/**
 * everything added to the lush biome
 */
public class LushBiomeProviders {
    public static class Model extends FabricModelProvider {

        public Model(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
            blockStateModelGenerator.registerPlantPart(ModBlocks.DRIPLEAF_VINES_PLANT, ModBlocks.DRIPLEAF_VINES, BlockStateModelGenerator.TintType.NOT_TINTED);
            blockStateModelGenerator.registerPlantPart(ModBlocks.PETUNIA_FLOWER, ModBlocks.PETUNIA, BlockStateModelGenerator.TintType.NOT_TINTED);

            Identifier[] marigold = new Identifier[]{
                    Identifier.of(Caligo.MOD_ID,"block/marigold_1"),
                    Identifier.of(Caligo.MOD_ID,"block/marigold_2"),
                    Identifier.of(Caligo.MOD_ID,"block/marigold_3"),
                    Identifier.of(Caligo.MOD_ID,"block/marigold_4")
            };

            blockStateModelGenerator.blockStateCollector.accept(ModModelProvider.createFlowerBlockState(ModBlocks.LUSH_MARIGOLD, marigold));
        }

        @Override
        public void generateItemModels(ItemModelGenerator itemModelGenerator) {
            itemModelGenerator.register(ModItems.DRIPLEAF_VINES, Models.GENERATED);
            itemModelGenerator.register(ModItems.PETUNIA, Models.GENERATED);
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
}
