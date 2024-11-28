package one.oth3r.caligo.generation.world.biome.surface;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import one.oth3r.caligo.generation.world.biome.ModBiomes;
import one.oth3r.caligo.block.ModBlocks;

/**
 * Surface rules for the ice caves biome
 */
public class IceCavesSurfaceRule {
    private static final MaterialRules.MaterialRule FROSTED_DEEPSLATE = makeStateRule(ModBlocks.FROSTED_DEEPSLATE);
    private static final MaterialRules.MaterialRule FROSTED_STONE = makeStateRule(ModBlocks.FROSTED_STONE);
    private static final MaterialRules.MaterialRule BEDROCK = makeStateRule(Blocks.BEDROCK);

    public static MaterialRules.MaterialRule makeRules() {
        return MaterialRules.condition(MaterialRules.biome(ModBiomes.ICE_CAVES),
                MaterialRules.sequence(
                        // the bedrock floor
                        MaterialRules.condition(MaterialRules.verticalGradient("bedrock_floor", YOffset.getBottom(), YOffset.aboveBottom(5)),
                                BEDROCK),
                        // deepslate level + transition
                        MaterialRules.condition(MaterialRules.verticalGradient("deepslate", YOffset.fixed(0), YOffset.fixed(8)),
                                FROSTED_DEEPSLATE),
                        // stone level
                        MaterialRules.condition(MaterialRules.not(MaterialRules.surface()),
                                FROSTED_STONE)
                        )
        );
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
