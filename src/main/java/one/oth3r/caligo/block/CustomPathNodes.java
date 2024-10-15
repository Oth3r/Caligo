package one.oth3r.caligo.block;

import net.fabricmc.fabric.api.registry.LandPathNodeTypesRegistry;
import net.minecraft.block.BigDripleafBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Tilt;
import net.minecraft.entity.ai.pathing.PathNodeType;

public class CustomPathNodes {
    public static void register() {
        // fix entities not being able to pathfind on top of drip leafs
        LandPathNodeTypesRegistry.StaticPathNodeTypeProvider provider = (state, neighbor) -> {
            if (state.isOf(Blocks.BIG_DRIPLEAF) && state.get(BigDripleafBlock.TILT).equals(Tilt.NONE)) return PathNodeType.BLOCKED;
            else return null;
        };
        LandPathNodeTypesRegistry.register(Blocks.BIG_DRIPLEAF, provider);
    }
}
