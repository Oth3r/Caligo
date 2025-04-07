package one.oth3r.caligo.generation.data.providers;


import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.generation.data.providers.grouped.CoppiceProviders;
import one.oth3r.caligo.generation.data.providers.grouped.IceCavesProviders;
import one.oth3r.caligo.generation.data.providers.grouped.LushBiomeProviders;
import one.oth3r.caligo.generation.data.providers.grouped.StrowProviders;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static net.minecraft.client.data.BlockStateModelGenerator.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        StrowProviders.Model.generateBlockStateModels(blockStateModelGenerator);
        CoppiceProviders.Model.generateBlockStateModels(blockStateModelGenerator);
        LushBiomeProviders.Model.generateBlockStateModels(blockStateModelGenerator);
        IceCavesProviders.Model.generateBlockStateModels(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        StrowProviders.Model.generateItemModels(itemModelGenerator);
        CoppiceProviders.Model.generateItemModels(itemModelGenerator);
        LushBiomeProviders.Model.generateItemModels(itemModelGenerator);
        IceCavesProviders.Model.generateItemModels(itemModelGenerator);
    }

    public static BlockModelDefinitionCreator createFlowerBlockState(Block block, @NotNull Identifier... modelIds) {
        ModelVariant[] variants = Arrays.stream(modelIds).map(ModelVariant::new).toArray(ModelVariant[]::new);
        ArrayList<ModelVariant> variantList = new ArrayList<>();
        for (ModelVariant variant : variants) {
            variantList.add(variant);
            variantList.add(variant.with(ROTATE_Y_90));
            variantList.add(variant.with(ROTATE_Y_180));
            variantList.add(variant.with(ROTATE_Y_270));
        }
        return VariantsBlockModelDefinitionCreator.of(block, BlockStateModelGenerator.createWeightedVariant(variantList.toArray(ModelVariant[]::new)));
    }

    public static final Model SPAWN_EGG = new Model(Optional.of(Identifier.ofVanilla("item/template_spawn_egg")), Optional.empty());

    public static Model getBlockItem(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(Caligo.MOD_ID,"block/"+parent)),Optional.empty(), requiredTextureKeys);
    }
}
