package one.oth3r.caligo.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.datagen.custom.IceCavesProviders;
import one.oth3r.caligo.datagen.custom.LuminCrystalProviders;
import one.oth3r.caligo.datagen.custom.LushBiomeProviders;
import one.oth3r.caligo.datagen.custom.StatueProviders;
import one.oth3r.caligo.item.ModItems;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ModTagProvider {
    public static class ItemTag extends FabricTagProvider.ItemTagProvider {

        public ItemTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {

            // flowers
            getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS)
                    .add(ModItems.LUSH_MARIGOLD);
            getOrCreateTagBuilder(ItemTags.FLOWERS)
                    .add(ModItems.LUSH_MARIGOLD)
                    .add(ModItems.PETUNIA);
            getOrCreateTagBuilder(ItemTags.TALL_FLOWERS)
                    .add(ModItems.PETUNIA);
        }
    }

    public static class BlockTag extends FabricTagProvider.BlockTagProvider {
        public BlockTag(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            // add all pickaxe mineable tags
            ArrayList<Block> pickaxe_mineable = new ArrayList<>();
            pickaxe_mineable.addAll(LuminCrystalProviders.PICKAXE_MINEABLE);
            pickaxe_mineable.addAll(StatueProviders.PICKAXE_MINEABLE);

            pickaxe_mineable.forEach(block -> getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block));

            // add all hoe mineable tags
            ArrayList<Block> hoe_mineable = new ArrayList<>();
            hoe_mineable.addAll(LushBiomeProviders.HOE_MINEABLE);

            hoe_mineable.forEach(block -> getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(block));


            // add all needs iron tag
            ArrayList<Block> needs_iron_tool = new ArrayList<>();
            needs_iron_tool.addAll(StatueProviders.NEEDS_IRON_TOOL);

            needs_iron_tool.forEach(block -> getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(block));

            // flower
            getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS)
                    .add(ModBlocks.LUSH_MARIGOLD);
            getOrCreateTagBuilder(BlockTags.FLOWERS)
                    .add(ModBlocks.LUSH_MARIGOLD)
                    .add(ModBlocks.PETUNIA).add(ModBlocks.PETUNIA_FLOWER);
            getOrCreateTagBuilder(BlockTags.TALL_FLOWERS)
                    .add(ModBlocks.PETUNIA).add(ModBlocks.PETUNIA_FLOWER);

            // add all overworld carver
            ArrayList<Block> overworld_carver_replaceable = new ArrayList<>();
            overworld_carver_replaceable.addAll(IceCavesProviders.OVERWORLD_CARVER);

            overworld_carver_replaceable.forEach(block -> getOrCreateTagBuilder(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(block));
        }
    }
}
