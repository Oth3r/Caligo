package one.oth3r.caligo.generation.world;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.*;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import one.oth3r.caligo.Caligo;

public class ModCarvers {
    public static final RegistryKey<ConfiguredCarver<?>> ICE_CAVE = register("ice_cave");

    public ModCarvers() {
    }

    private static RegistryKey<ConfiguredCarver<?>> register(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_CARVER, Identifier.of(Caligo.MOD_ID, id));
    }

    public static void bootstrap(Registerable<ConfiguredCarver<?>> carverRegisterable) {
        RegistryEntryLookup<Block> registryEntryLookup = carverRegisterable.getRegistryLookup(RegistryKeys.BLOCK);

        carverRegisterable.register(ICE_CAVE, Carver.CAVE.configure(ice_cave_carver(registryEntryLookup)));
    }

    /**
     * the cave carver for the ice caves
     */
    public static CaveCarverConfig ice_cave_carver(RegistryEntryLookup<Block> registryEntryLookup) {
        return new CaveCarverConfig(0.15F,
                UniformHeightProvider.create(YOffset.aboveBottom(8), YOffset.fixed(64)), // height
                UniformFloatProvider.create(0.1F, 0.9F), //yscale
                YOffset.aboveBottom(0), //lava
                CarverDebugConfig.create(false, Blocks.BARRIER.getDefaultState()), // debug
                registryEntryLookup.getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES), // replace
                UniformFloatProvider.create(.8F, 1.2F), // horizontal rad
                UniformFloatProvider.create(2F, 4F), // vert rad
                UniformFloatProvider.create(-1F, -.8F) // floor smoothness
        );
    }
}
