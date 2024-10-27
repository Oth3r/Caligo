package one.oth3r.caligo.entity.coppice;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BigDripleafBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Tilt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import one.oth3r.caligo.tag.ModBlockTags;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class CoppiceSpecificSensor extends Sensor<CoppiceEntity> {
    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.copyOf(CoppiceBrain.MEMORY_MODULE_TYPES);

    }

    @Override
    protected void sense(ServerWorld world, CoppiceEntity entity) {
        Brain<?> brain = entity.getBrain();
        brain.remember(MemoryModuleType.NEAREST_REPELLENT, findCoppiceRepellent(world, entity));

        LivingTargetCache livingTargetCache = brain.getOptionalRegisteredMemory(MemoryModuleType.VISIBLE_MOBS).orElse(LivingTargetCache.empty());
        Iterator<LivingEntity> visibleMobs = livingTargetCache.iterate((livingEntityx) -> true).iterator();

        Optional<PlayerEntity> playerHoldingWanted = Optional.empty();

        while(visibleMobs.hasNext()) {
            LivingEntity livingEntity = visibleMobs.next();
            if (livingEntity instanceof PlayerEntity playerEntity) {
                // if player isnt in spectator
                if (!playerEntity.isSpectator() && CoppiceBrain.isWantedHoldingPlayer(playerEntity)) {
                    playerHoldingWanted = Optional.of(playerEntity);
                }
            }
        }

        brain.remember(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, playerHoldingWanted);
    }

    private static Optional<BlockPos> findCoppiceRepellent(ServerWorld world, LivingEntity entity) {
        Optional<BlockPos> blockPos = BlockPos.findClosest(entity.getBlockPos(), 8, 4, pos -> isCoppiceRepellent(world, pos));
        return blockPos.map(pos -> pos.add(0, 1, 0));
    }

    private static boolean isCoppiceRepellent(ServerWorld world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        boolean correctType = blockState.isIn(ModBlockTags.COPPICE_INTERESTS);
        return correctType && blockState.isOf(Blocks.BIG_DRIPLEAF) ? blockState.get(BigDripleafBlock.TILT).equals(Tilt.NONE) : correctType;
    }
}
