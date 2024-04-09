package one.oth3r.caligo;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import one.oth3r.caligo.block.statue.StatueState;

public class Utl {
    public static class statue {
        /**
         * returns a StatueState based on the player's current action
         * @param player the player
         * @return the StatueState
         */
        public static StatueState getPlacementState(PlayerEntity player) {
            if (player.isSneaking()) return StatueState.CROUCH;
            if (player.isSprinting()) return StatueState.RUN;
            return StatueState.WALK;
        }
        public static BlockPos getPlacement(World world, BlockPos deathPos) {
            if (checkPos(world,deathPos)) return deathPos;
            // 3x4x3 around deathpos
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int yOffset = -2; yOffset <= 1; yOffset++) {
                    for (int zOffset = -1; zOffset <= 1; zOffset++) {
                        BlockPos checkPos = deathPos.add(xOffset, yOffset, zOffset);
                        if (checkPos(world, checkPos)) return checkPos;
                    }
                }
            }
            // if none are valid, null
            return null;
        }
        private static boolean checkPos(World world, BlockPos block) {
            BlockState below = world.getBlockState(block.add(0,-1,0));
            // below cant be air or not a full block
            if (below.getBlock() instanceof AirBlock || !below.isFullCube(world,block)) return false;
            return world.getBlockState(block).getBlock() instanceof AirBlock &&
                    world.getBlockState(block.add(0, 1, 0)).getBlock() instanceof AirBlock;
        }
    }
}
