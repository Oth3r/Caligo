package one.oth3r.caligo.generation.world.features.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import one.oth3r.caligo.generation.world.biome.ModBiomes;
import one.oth3r.caligo.tag.ModBlockTags;

import java.util.function.Predicate;

public class IceCavesSpikeFeature extends Feature<DefaultFeatureConfig> {
    public IceCavesSpikeFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        final BlockPos origin = context.getOrigin();
        BlockPos spikePos = context.getOrigin();


        Random random = context.getRandom();
        StructureWorldAccess world = context.getWorld();
        DynamicRegistryManager drm = world.getRegistryManager();
        Registry<Biome> biomeReg = drm.getOrThrow(RegistryKeys.BIOME);

        // move upward until an opening is found
        while (!world.getBiome(origin).value().equals(biomeReg.get(ModBiomes.ICE_CAVES)) && !world.isAir(spikePos) && spikePos.getY() < world.getHeight()) {
            spikePos = spikePos.up();
        }

        BlockPos bottomPos = new BlockPos(spikePos);

        // Abort if the block is not frosted stone
        BlockState originState = world.getBlockState(spikePos.down());
        if (originState.isIn(ModBlockTags.ICE_CAVES_REPLACEABLE)) {
            // get the 2 directions that the spike will move in
            Vec3i direction = getRandomXZDirection(random), direction2 = getFlippedXZDirection(direction);

            final int radiusX = random.nextBetween(4,6), radiusZ = random.nextBetween(4,6);
            int radius1 = radiusX, radius2 = radiusZ;

            int layerHeight = getLayerHeight(random);

            while (buildLayer(world, random, origin, spikePos, radius1, radius2)) {
                // move
                spikePos = spikePos.up().add(direction);
                layerHeight--;
                if (layerHeight == 0) {
                    // adjust the radius
                    if (random.nextBoolean() && radius1 > 1) radius1--;
                    else if (radius2 > 2) radius2--;

                    spikePos = spikePos.add(direction2);
                    layerHeight = getLayerHeight(random);
                }

                if (!validateBlocPos(world, spikePos)) break;
                if (radius1 == 1 && radius2 == 2) break;
            }

            // fill below the spike
            bottomPos = bottomPos.down();
            // reset the radius
            radius1 = radiusX;
            radius2 = radiusZ;

            layerHeight = getLayerHeight(random);
            while (buildLayer(world, random, origin, bottomPos, radius1, radius2)) {
                // move
                bottomPos = bottomPos.down().subtract(direction);
                layerHeight--;
                if (layerHeight == 0) {
                    // adjust the radius
                    if (random.nextBoolean() && radius1 > 2) radius1--;
                    else if (radius2 > 2) radius2--;

                    bottomPos = bottomPos.subtract(direction2);
                    layerHeight = getLayerHeight(random);
                }
                if (!validateBlocPos(world, bottomPos)) break;
            }

            return true;
        }
        return false;
    }


    private boolean isWithinBounds(Vec3i position, Vec3i origin) {
        int chunkRadius = 16; // 16 radius (1 chunk)
        int deltaX = position.getX() - origin.getX();
        int deltaZ = position.getZ() - origin.getZ();
        return (deltaX * deltaX + deltaZ * deltaZ) < (chunkRadius * chunkRadius);
    }

    public int getLayerHeight(Random random) {
        return random.nextInt(3) + 1;
    }

    public boolean validateBlocPos(StructureWorldAccess world, BlockPos pos) {
        return world.getHeight() > pos.getY() && world.getBottomY() < pos.getY();
    }

    public boolean circle(int radiusX, int radiusZ, BlockPos circleOrigin, Predicate<BlockPos> func) {
        for (int dx = -radiusX; dx <= radiusX; dx++) {
            for (int dz = -radiusZ; dz <= radiusZ; dz++) {
                if (dx * dx + dz * dz <= radiusX * radiusZ) {
                    BlockPos pos = circleOrigin.add(dx, 0, dz);
                    if (!func.test(pos)) return false;
                }
            }
        }
        return true;
    }

    public boolean buildLayer(StructureWorldAccess world, Random random, BlockPos contextOrigin, BlockPos circleOrigin, int radiusX, int radiusZ) {
        int outerRadiusSq = radiusX * radiusZ;
        int innerRadiusSq = (radiusX - 1) * (radiusZ - 1);
        boolean placed = false;

        // make sure the circle isnt too far from the origin, if it is, try to shrink the circle if possible and check again
        while (!circle(radiusX,radiusZ, circleOrigin, blockPos -> isWithinBounds(blockPos, contextOrigin)) && radiusX > 1 && radiusZ > 1) {
            radiusX--;
            radiusZ--;
        }
        // if still cant make the circle, just give up
        if (!circle(radiusX,radiusZ, circleOrigin, blockPos -> isWithinBounds(blockPos, contextOrigin))) return false;

        for (int dx = -radiusX; dx <= radiusX; dx++) {
            for (int dz = -radiusZ; dz <= radiusZ; dz++) {
                int distSq = dx * dx + dz * dz;
                BlockPos pos = circleOrigin.add(dx, 0, dz);

                // check if point is within the spike
                if (distSq <= radiusX * radiusZ) {
                    // if we can place a block
                    if (world.isAir(pos) && world.isValidForSetBlock(pos)) {

                        Block block = Blocks.PACKED_ICE;
                        // if the point is on the outside edge, 20% chance to be an ice block
                        if (distSq <= outerRadiusSq && distSq >= innerRadiusSq) {
                            if (random.nextFloat() < .2) block = Blocks.ICE;
                        } else {
                            // if not on the outside edge, 0.1% chance to be a diamond block
                            if (random.nextFloat() < 0.001) {
                                block = pos.getY() < 0 ? Blocks.DEEPSLATE_DIAMOND_ORE : Blocks.DIAMOND_ORE;
                            }
                        }

                        boolean hasPlaced = world.setBlockState(pos, block.getDefaultState(), Block.NOTIFY_ALL);
                        // only try to update placed if already haven't
                        if (!placed) placed = hasPlaced;
                    }
                }
            }
        }

        return placed;
    }

    private Vec3i getRandomXZDirection(Random random) {
        int magnitude = 1;
        int axis = random.nextInt(2); // 0 = X, 1 = Z
        int sign = random.nextBoolean() ? 1 : -1;

        if (axis == 0) {
            return new Vec3i(sign * magnitude, 0, 0);
        } else {
            return new Vec3i(0, 0, sign * magnitude);
        }
    }

    private Vec3i getFlippedXZDirection(Vec3i direction) {
        if (direction.getX() != 0) {
            return direction.add(-direction.getX(), 0, direction.getX());
        } else {
            return direction.add(direction.getZ(), 0, -direction.getZ());
        }
    }
}
