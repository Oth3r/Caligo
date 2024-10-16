package one.oth3r.caligo.block.plant.petunia;

import net.minecraft.block.*;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import one.oth3r.caligo.block.ModBlocks;

public class PetuniaFlowerBlock extends AbstractPlantBlock {

    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);

    public PetuniaFlowerBlock(Settings settings) {
        super(settings, Direction.UP, SHAPE, false);
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModBlocks.PETUNIA;
    }
}
