package one.oth3r.caligo.block.lumin_crystal;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import one.oth3r.caligo.Utl;
import one.oth3r.caligo.block.ModBlocks;

import java.util.List;

public class LuminCrystalBlockEntity extends BlockEntity {

    private final int NEARBY_LIGHT = 8;
    private final int DEFAULT_LIGHT = 2;

    public LuminCrystalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.LUMIN_CRYSTAL_BLOCK_ENTITY, pos, state);
    }

    public void serverTick(ServerWorld world, BlockPos pos, BlockState state, LuminCrystalBlockEntity blockEntity) {
        if (world.getTime() % 2 == 0) return;
        int currentPower = state.get(LuminCrystalBlock.POWER);

        List<ServerPlayerEntity> list = world.getPlayers(player ->
                player.interactionManager.getGameMode() != GameMode.SPECTATOR
                        && pos.toCenterPos().isInRange(player.getPos(), 16));

        if (!list.isEmpty()) {
            for (ServerPlayerEntity player : list) {
                if (Utl.getBlockPosPlayerIsLookingAt(world,player,16).equals(pos)) {
                    setPower(world,15);
                } else {
                    if (currentPower > NEARBY_LIGHT) {
                        setPower(world, currentPower-1);
                    } else if (currentPower < NEARBY_LIGHT) {
                        setPower(world, currentPower+1);
                    }
                }
            }
        } else {
            if (currentPower != DEFAULT_LIGHT) {
                setPower(world, DEFAULT_LIGHT);
            }
        }
    }

    public void setPower(ServerWorld world, int power) {
        this.markDirty();
        world.setBlockState(this.pos, this.getCachedState().with(LuminCrystalBlock.POWER, Math.max(0,Math.min(power, 15))));
    }
}
