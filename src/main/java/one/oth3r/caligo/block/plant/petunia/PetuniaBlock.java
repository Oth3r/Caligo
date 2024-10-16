package one.oth3r.caligo.block.plant.petunia;

import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import one.oth3r.caligo.block.ModBlocks;

public class PetuniaBlock extends AbstractPlantStemBlock {

    public static final VoxelShape SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 0.41, 1);

    public PetuniaBlock(Settings settings) {
        super(settings, Direction.UP, SHAPE, false, 0.1);
    }

    @Override
    protected int getGrowthLength(Random random) {
        return 1;
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return state.isAir();
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.PETUNIA_FLOWER;
    }

    /**
     * remove random tick to stop growth without bonemeal
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {}
}
