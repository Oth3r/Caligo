package one.oth3r.caligo.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SnowPathBlock extends Block {
    public static final MapCodec<SnowPathBlock> CODEC = createCodec(SnowPathBlock::new);

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);

    public SnowPathBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return true;
    }
    //todo
    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
