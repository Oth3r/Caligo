package one.oth3r.caligo.block.plant.dripleaf_vine;

import net.minecraft.block.*;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import one.oth3r.caligo.block.ModBlocks;

public class DripleafVinePlantBlock extends AbstractPlantBlock {

    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

    public DripleafVinePlantBlock(Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false);
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModBlocks.DRIPLEAF_VINES;
    }
}
