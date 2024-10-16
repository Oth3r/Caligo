package one.oth3r.caligo.block.plant;

import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class LushMarigoldFlowerBlock extends FlowerBlock {

    protected static final VoxelShape SHAPE = VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.5625, 0.9375);

    public LushMarigoldFlowerBlock(Settings settings) {
        super(StatusEffects.GLOWING,15, settings); //todo check
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return super.getCollisionShape(state, world, pos, context);
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());
        boolean aboveWater = (fluidState.getFluid() == Fluids.WATER || floor.getBlock() instanceof IceBlock) && fluidState2.getFluid() == Fluids.EMPTY;
        return aboveWater || floor.getBlock() == Blocks.CLAY || floor.isOf(Blocks.FARMLAND) ||
                floor.isOf(Blocks.MOSS_BLOCK) || floor.isIn(BlockTags.DIRT);
    }
}
