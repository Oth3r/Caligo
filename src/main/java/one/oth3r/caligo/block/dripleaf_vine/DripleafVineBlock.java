package one.oth3r.caligo.block.dripleaf_vine;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import one.oth3r.caligo.block.ModBlocks;

public class DripleafVineBlock extends AbstractPlantStemBlock {
    public static final MapCodec<DripleafVineBlock> CODEC = createCodec(DripleafVineBlock::new);
    @Override
    protected MapCodec<? extends AbstractPlantStemBlock> getCodec() {
        return null;
    }


    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);

    public DripleafVineBlock(Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false, 0.1);
    }

    @Override
    protected Block getPlant() {
        return ModBlocks.DRIPLEAF_VINES_PLANT;
    }

    @Override
    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return state.isAir();
    }
}
