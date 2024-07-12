package one.oth3r.caligo.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.item.ModItems;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {


        blockStateModelGenerator.blockStateCollector
                .accept(VariantsBlockStateSupplier.create(ModBlocks.LUMIN_CRYSTAL_BLOCK,
                        BlockStateVariant.create().put(VariantSettings.MODEL,Identifier.of(Caligo.MOD_ID,"block/lumin_crystal")))
                        .coordinate(blockStateModelGenerator.createUpDefaultFacingVariantMap()));
    }

    private static final Model SPAWN_EGG = new Model(Optional.of(Identifier.ofVanilla("item/template_spawn_egg")), Optional.empty());

    private static Model getBlockItem(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(Caligo.MOD_ID,"block/"+parent)),Optional.empty(), requiredTextureKeys);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(ModItems.STROW_ESSENCE, Models.GENERATED);

        itemModelGenerator.register(ModItems.LUMIN_CRYSTAL, getBlockItem("lumin_crystal"));


        // SPAWN EGGS
        itemModelGenerator.register(ModItems.STROW_SPAWN_EGG, SPAWN_EGG);
        itemModelGenerator.register(ModItems.DEEP_STROW_SPAWN_EGG, SPAWN_EGG);
    }
}
