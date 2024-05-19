package one.oth3r.caligo.block.statue;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
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
    public static final EnumProperty<StatueState> STATE = EnumProperty.of("state", StatueState.class);
    public static final MapCodec<? extends BlockWithEntity> CODEC = StatueBlock.createCodec(StatueBlock::new);
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }
    public StatueBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
                .with(ROTATION, 0)
                .with(STATE,StatueState.WALK)
                .with(HALF,DoubleBlockHalf.LOWER));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
        builder.add(STATE);
        builder.add(HALF);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StatueBlockEntity(pos, state);
    }

    /**
     * gets the placement state for the player
     */
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (ctx.getPlayer() == null) return super.getPlacementState(ctx);
        return getDefaultState()
                .with(ROTATION, RotationPropertyHelper.fromYaw(ctx.getPlayerYaw()))
                .with(STATE, Utl.statue.getPlacementState(ctx.getPlayer()));
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
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (!(direction.getAxis() != Direction.Axis.Y ||
                doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) ||
                neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        }
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
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

    /**
     * drops statue items
     */
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        // if the block is still there, stop
        if (state.isOf(newState.getBlock())) {
            return;
        }
        // get the block entity
        StatueBlockEntity statueBlockEntity = (StatueBlockEntity) world.getBlockEntity(pos);
        // if upper then get the block entity is one block down
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            statueBlockEntity = (StatueBlockEntity) world.getBlockEntity(pos.down());
        }
        // if null continue
        if (statueBlockEntity == null) return;

        ItemScatterer.spawn(world, pos, statueBlockEntity.getInv());
        world.updateComparators(pos, state.getBlock());
        // drop the xp
        if (statueBlockEntity.getXp() != 0) {
            this.dropExperience(world.getServer().getWorld(world.getRegistryKey()), pos, statueBlockEntity.getXp());
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    /**
     * drops statue xp
     */
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        // get the block entity
        StatueBlockEntity statueBlockEntity = (StatueBlockEntity) world.getBlockEntity(pos);
        // if null continue
        if (statueBlockEntity == null) return;
        // if no xp continue
        if (statueBlockEntity.getXp() == 0) {
            this.dropExperience(world, pos, statueBlockEntity.getXp());
        }
    }
}
