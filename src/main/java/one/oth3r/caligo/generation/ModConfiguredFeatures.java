package one.oth3r.caligo.generation;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.RandomizedIntBlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.block.dripleaf_vine.DripleafVineBlock;

import java.util.Arrays;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?,?>> DRIPLEAF_VINES_KEY = registerKey("dripleaf_vines");
    public static final RegistryKey<ConfiguredFeature<?,?>> LUSH_MARIGOLD_KEY = registerKey("lush_marigold");

    public static void boostrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, DRIPLEAF_VINES_KEY, Feature.BLOCK_COLUMN,
                new BlockColumnFeatureConfig(
                        Arrays.asList(
                        new BlockColumnFeatureConfig.Layer(
                                new WeightedListIntProvider(new DataPool.Builder<IntProvider>()
                                        .add(UniformIntProvider.create(0,23),2)
                                        .add(UniformIntProvider.create(0,2),3)
                                        .add(UniformIntProvider.create(0,6),10)
                                        .build()
                                ),
                                BlockStateProvider.of(ModBlocks.DRIPLEAF_VINES_PLANT)
                        ),
                        new BlockColumnFeatureConfig.Layer(
                                ConstantIntProvider.create(1),
                                new RandomizedIntBlockStateProvider(SimpleBlockStateProvider.of(ModBlocks.DRIPLEAF_VINES),
                                        DripleafVineBlock.AGE, UniformIntProvider.create(23,25)))
                        ),
                        Direction.DOWN,
                        BlockPredicate.IS_AIR,
                        true
                )
        );

        register(context, LUSH_MARIGOLD_KEY, Feature.RANDOM_PATCH,
                new RandomPatchFeatureConfig(
                        352,6,3,
                        PlacedFeatures.createEntry(
                            Feature.SIMPLE_BLOCK,
                            new SimpleBlockFeatureConfig(SimpleBlockStateProvider.of(ModBlocks.LUSH_MARIGOLD)),
                            BlockPredicate.anyOf(
                                    BlockPredicate.allOf(
                                            BlockPredicate.IS_AIR,
                                            BlockPredicate.matchingBlockTag(new Vec3i(0,-1,0), TagKey.of(RegistryKeys.BLOCK, BlockTags.SMALL_DRIPLEAF_PLACEABLE.id()))
                                    ),
                                    BlockPredicate.allOf(
                                            BlockPredicate.IS_AIR,
                                            BlockPredicate.matchingFluids(new Vec3i(0,-1,0), Fluids.WATER)
                                    ),
                                    BlockPredicate.matchingBlocks(Blocks.SHORT_GRASS)
                            )
                        )
                ));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Caligo.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}