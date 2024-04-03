package one.oth3r.caligo.block.statue;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import one.oth3r.caligo.Utl;
import org.jetbrains.annotations.Nullable;

public class StatueBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final int MAX_ROTATION_INDEX = RotationPropertyHelper.getMax();
    private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final IntProperty ROTATION = Properties.ROTATION;
    public static final IntProperty STATE = Properties.AGE_2;
    public StatueBlock(Settings settings) {
        super(settings);
    }
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StatueBlockEntity(pos, state);
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx)
                .with(ROTATION, RotationPropertyHelper.fromYaw(ctx.getPlayerYaw()))
                .with(STATE, Utl.statue.getPlacementState(ctx.getPlayer()))
                .with(HALF, DoubleBlockHalf.LOWER);
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(ROTATION, rotation.rotate(state.get(ROTATION), MAX_ROTATIONS));
    }
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(ROTATION, mirror.mirror(state.get(ROTATION), MAX_ROTATIONS));
    }
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return blockState.isSideSolidFullSquare(world, blockPos, Direction.UP);
        }
        return blockState.isOf(this);
    }
    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onSyncedBlockEvent(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        // if upper then the statue is one block down
        if (state.get(HALF) == DoubleBlockHalf.UPPER) blockEntity = world.getBlockEntity(pos.down());
        System.out.println("aa"+(state.get(HALF) == DoubleBlockHalf.UPPER));
        if (blockEntity == null) return false;
        return blockEntity.onSyncedBlockEvent(type, data);
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (!(direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        }
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ROTATION);
        builder.add(STATE);
        builder.add(HALF);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getOutlineShape(state, world, pos, context);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int rot = state.get(ROTATION);
        VoxelShape shape = VoxelShapes.empty();
        if (rot <= 2 || rot >= 13 || (rot >= 6 && rot <= 10)) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 1.5, 0.25, 0.75, 2, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.75, 0.375, 1, 1.5, 0.625));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.24375000000000002, 0, 0.375, 0.74375, 0.75, 0.625));
        } else {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 1.5, 0.25, 0.75, 2, 0.75));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0.75, 0, 0.625, 1.5, 1));
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.24375000000000002, 0.625, 0.75, 0.74375));
        }
        if (state.get(HALF) == DoubleBlockHalf.UPPER) return shape.offset(0,-1,0);
        return shape;
    }
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
    }
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        StatueBlockEntity statueBlockEntity = (StatueBlockEntity) world.getBlockEntity(pos);
        // if upper then the statue is one block down
        if (state.get(HALF) == DoubleBlockHalf.UPPER) statueBlockEntity = (StatueBlockEntity) world.getBlockEntity(pos.down());
        if (statueBlockEntity == null) return;
        if (!statueBlockEntity.getInv().isEmpty())
            for (ItemStack item: statueBlockEntity.getInv())
                Block.dropStack(world,pos,item);
        if (statueBlockEntity.getXp() != 0)
            this.dropExperience(world.getServer().getWorld(world.getRegistryKey()),pos,statueBlockEntity.getXp());
        super.onBreak(world, pos, state, player);
    }
}
