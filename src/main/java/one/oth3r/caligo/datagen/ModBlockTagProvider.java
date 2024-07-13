package one.oth3r.caligo.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import one.oth3r.caligo.datagen.custom.StatueProviders;
import one.oth3r.caligo.tag.ModBlockTags;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        // add all pickaxe mineable tags
        ArrayList<Block> pickaxe_mineable = new ArrayList<>();
        pickaxe_mineable.addAll(StatueProviders.PICKAXE_MINEABLE);

        pickaxe_mineable.forEach(block -> getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block));

        // add all needs iron tag
        ArrayList<Block> needs_iron_tool = new ArrayList<>();
        needs_iron_tool.addAll(StatueProviders.NEEDS_IRON_TOOL);

        needs_iron_tool.forEach(block -> getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(block));
    }
}
