package one.oth3r.caligo.block.lumin_crystal;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import one.oth3r.caligo.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

public class LuminCrystalBlock extends BlockWithEntity implements Waterloggable {
    public static final MapCodec<? extends BlockWithEntity> CODEC = LuminCrystalBlock.createCodec(LuminCrystalBlock::new);
    public static final IntProperty POWER = IntProperty.of("power",0,15);
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected final VoxelShape northShape;
    protected final VoxelShape southShape;
    protected final VoxelShape eastShape;
    protected final VoxelShape westShape;
    protected final VoxelShape upShape;
    protected final VoxelShape downShape;

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    public LuminCrystalBlock(Settings settings) {
        super(settings);
        northShape = VoxelShapes.union(
                VoxelShapes.cuboid(0.375, 0.375, 0.625, 0.625, 0.625, 1),
                VoxelShapes.cuboid(0.4375, 0.4375, 0.5625, 0.5625, 0.5625, 0.625));
        southShape = VoxelShapes.union(
                VoxelShapes.cuboid(0.375, 0.375, 0, 0.625, 0.625, 0.375),
                VoxelShapes.cuboid(0.4375, 0.4375, 0.375, 0.5625, 0.5625, 0.4375));
        eastShape = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.375, 0.375, 0.375, 0.625, 0.625),
                VoxelShapes.cuboid(0.375, 0.4375, 0.4375, 0.4375, 0.5625, 0.5625));
        westShape = VoxelShapes.union(
                VoxelShapes.cuboid(0.625, 0.375, 0.375, 1, 0.625, 0.625),
                VoxelShapes.cuboid(0.5625, 0.4375, 0.4375, 0.625, 0.5625, 0.5625));
        upShape = VoxelShapes.union(
                VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.375, 0.625),
                VoxelShapes.cuboid(0.4375, 0.375, 0.4375, 0.5625, 0.4375, 0.5625));
        downShape = VoxelShapes.union(
                VoxelShapes.cuboid(0.375, 0.625, 0.375, 0.625, 1, 0.625),
                VoxelShapes.cuboid(0.4375, 0.5625, 0.4375, 0.5625, 0.625, 0.5625));
        setDefaultState(getDefaultState()
                .with(FACING,Direction.UP)
                .with(WATERLOGGED,false));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case NORTH -> northShape;
            case SOUTH -> southShape;
            case EAST -> eastShape;
            case WEST -> westShape;
            case UP -> upShape;
            case DOWN -> downShape;
        };
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction == state.get(FACING).getOpposite() && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        return this.getDefaultState().with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER).with(FACING, ctx.getSide());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED).booleanValue()) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER,FACING,WATERLOGGED);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(POWER);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LuminCrystalBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlocks.LUMIN_CRYSTAL_BLOCK_ENTITY, ((world1, pos, state1, blockEntity) -> {
            if (world instanceof ServerWorld serverWorld) {
                blockEntity.serverTick(serverWorld,pos,state,blockEntity);
            }
        }));
    }
}
