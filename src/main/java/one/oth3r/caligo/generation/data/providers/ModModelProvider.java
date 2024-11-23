package one.oth3r.caligo.generation.data.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    public static VariantsBlockStateSupplier createFlowerBlockState(Block block,@NotNull Identifier... modelIds) {
        BlockStateVariant[] variants = new BlockStateVariant[]{};
        // create random rotations for every model id
        for (Identifier modelID : modelIds) {
            BlockStateVariant[] random = BlockStateModelGenerator.createModelVariantWithRandomHorizontalRotations(modelID);

            variants = ArrayUtils.addAll(variants,random);
        }

        return VariantsBlockStateSupplier.create(block, variants);
    }

    public static final Model SPAWN_EGG = new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty());

    public static Model getBlockItem(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(Caligo.MOD_ID,"block/"+parent)),Optional.empty(), requiredTextureKeys);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
