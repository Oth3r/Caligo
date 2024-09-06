package one.oth3r.caligo.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class LushMarigoldFlowerBlock extends FlowerBlock {
    public static final MapCodec<LushMarigoldFlowerBlock> CODEC = createCodec(LushMarigoldFlowerBlock::new);
    @Override
    public MapCodec<LushMarigoldFlowerBlock> getCodec() {
        return CODEC;
    }

    protected static final VoxelShape SHAPE = VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.5625, 0.9375);

    public LushMarigoldFlowerBlock(Settings settings) {
        super(FlowerBlock.createStewEffectList(StatusEffects.GLOWING,15), settings); //todo check
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return super.getCollisionShape(state, world, pos, context);
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());
        boolean aboveWater = (fluidState.getFluid() == Fluids.WATER || floor.getBlock() instanceof IceBlock) && fluidState2.getFluid() == Fluids.EMPTY;
        return aboveWater || floor.getBlock() == Blocks.CLAY;
    }
}
