package one.oth3r.caligo.generation.world.features.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import one.oth3r.caligo.generation.world.biome.ModBiomes;

/**
 * freezes all top uncovered water, searching the whole chunk. ONLY FOR ICE CAVES
 */
public class IceCavesWaterFeature extends Feature<DefaultFeatureConfig> {

    public IceCavesWaterFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        DynamicRegistryManager drm = structureWorldAccess.getRegistryManager();
        Registry<Biome> biomeReg = drm.getOrThrow(RegistryKeys.BIOME);

        BlockPos originPos = context.getOrigin();
        BlockPos.Mutable scanTarget = new BlockPos.Mutable();

        // create loop to scan 16x16 from origin
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                int targetX = originPos.getX() + i;
                int targetZ = originPos.getZ() + j;

                // get the topmost block in current colum
                int topY = structureWorldAccess.getTopY(Heightmap.Type.MOTION_BLOCKING, targetX, targetZ);

                // loop through the whole column TOP -> BOTTOM
                for (int targetY = topY; targetY > structureWorldAccess.getBottomY(); targetY--) {
                    // set the scan target
                    scanTarget.set(targetX,targetY,targetZ);

                    // skip the pos if the biome is incorrect
                    Biome biome = structureWorldAccess.getBiome(scanTarget).value();
                    if (!biome.equals(biomeReg.get(ModBiomes.ICE_CAVES))) {
                        continue;
                    }

                    // if can set ice, set ice
                    if (canSetIce(structureWorldAccess, scanTarget)) {
                        structureWorldAccess.setBlockState(scanTarget, Blocks.ICE.getDefaultState(), Block.FORCE_STATE);
                    }

                    // todo maybe set snow layers as well
                }
            }
        }

        return true;
    }

    /**
     * checks if a position can be ice or not, using the position above
     */
    private boolean canSetIce(WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        FluidState fluidState = world.getFluidState(pos);
        // if block is water & the light level is below 10,
        if (fluidState.getFluid() == Fluids.WATER && blockState.getBlock() instanceof FluidBlock
                && world.getLightLevel(LightType.BLOCK, pos) < 10) {
            // if the base is water, start checking above
            BlockPos posAbove = pos.up();
            BlockState aboveBlockSate = world.getBlockState(posAbove);
            FluidState aboveFluidState = world.getFluidState(posAbove);
            // if air above, true
            return aboveFluidState.getFluid() == Fluids.EMPTY && aboveBlockSate.getBlock() instanceof AirBlock;
        }

        return false;
    }
}
