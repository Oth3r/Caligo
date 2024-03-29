package one.oth3r.caligo;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Utl {
    public static class statue {
        public static int getPlacementState(PlayerEntity player) {
            if (player.isSneaking()) return 2;
            if (player.isSprinting()) return 1;
            return 0;
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
