package one.oth3r.caligo.block.deepslate_statue;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import one.oth3r.caligo.block.statue.StatueBlock;
import one.oth3r.caligo.block.statue.StatueBlockEntity;

public class DeepslateStatueBlock extends StatueBlock implements BlockEntityProvider {
    public DeepslateStatueBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StatueBlockEntity(pos, state);
    }
}
