package one.oth3r.caligo.generation.world.features.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import one.oth3r.caligo.generation.world.biome.ModBiomes;

/**
 * converts all lava blocks into a todo different block...
 */
public class IceCavesLavaFeature extends Feature<DefaultFeatureConfig> {
    public IceCavesLavaFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos originPos = context.getOrigin();

        BlockPos.Mutable scanTarget = new BlockPos.Mutable();

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int targetX = originPos.getX() + i;
                int targetZ = originPos.getZ() + j;

                DynamicRegistryManager drm = structureWorldAccess.getRegistryManager();
                Registry<Biome> reg = drm.get(RegistryKeys.BIOME);

                // loop through the whole column
                for (int targetY = 0; targetY > structureWorldAccess.getBottomY(); targetY--) {
                    scanTarget.set(targetX, targetY, targetZ);

                    // skip the pos if the biome is incorrect
                    Biome biome = structureWorldAccess.getBiome(scanTarget).value();
                    if (!biome.equals(reg.get(ModBiomes.ICE_CAVES))) {
                        continue;
                    }

                    if (isLava(structureWorldAccess, scanTarget)) {
                        // todo a custom block
                        structureWorldAccess.setBlockState(scanTarget, Blocks.EMERALD_BLOCK.getDefaultState(), Block.FORCE_STATE);
                    }
                }
            }
        }
        return true;
    }

    /**
     * checks if a position is a lava block or not
     */
    private boolean isLava(WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        FluidState fluidState = world.getFluidState(pos);

        return fluidState.getFluid() == Fluids.LAVA && blockState.getBlock() instanceof FluidBlock; // target is lava
    }
}
