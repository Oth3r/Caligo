package one.oth3r.caligo.generation.world.features;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import net.minecraft.world.gen.placementmodifier.*;
import one.oth3r.caligo.Caligo;

import java.util.Arrays;
import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> DRIPLEAF_VINES_PLACED_KEY = registerKey("dripleaf_vines");
    public static final RegistryKey<PlacedFeature> LUSH_MARIGOLD_PLACED_KEY = registerKey("lush_marigold");
    public static final RegistryKey<PlacedFeature> PETUNIA_PLACED_KEY = registerKey("petunia");

    public static final RegistryKey<PlacedFeature> ICE_CAVES_SNOW_FLOOR = registerKey("ice_caves_snow_floor");
    public static final RegistryKey<PlacedFeature> ICE_CAVES_COMPACTED_SNOW_FLOOR = registerKey("ice_caves_compacted_snow_floor");
    public static final RegistryKey<PlacedFeature> ICE_CAVES_LAVA_PLACED_KEY = registerKey("ice_caves_lava");
    public static final RegistryKey<PlacedFeature> ICE_CAVES_WATER_PLACED_KEY = registerKey("ice_caves_water");

    public static void boostrap(Registerable<PlacedFeature> context) {
        lush_caves(context);
        ice_caves(context);
    }

    public static void lush_caves(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, DRIPLEAF_VINES_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DRIPLEAF_VINES_KEY),
                Arrays.asList(
                        CountPlacementModifier.of(188),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.of(
                                UniformHeightProvider.create(YOffset.aboveBottom(0),YOffset.getTop())
                        ),
                        EnvironmentScanPlacementModifier.of(
                                Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.IS_AIR, 12
                        ),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
                        BiomePlacementModifier.of()
                ));

        register(context, PETUNIA_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PETUNIA_KEY),
                Arrays.asList(
                        CountPlacementModifier.of(80),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.of(
                                UniformHeightProvider.create(YOffset.aboveBottom(0),YOffset.getTop())
                        ),
                        EnvironmentScanPlacementModifier.of(
                                Direction.DOWN, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.IS_AIR, 12
                        ),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                ));

        register(context, LUSH_MARIGOLD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.LUSH_MARIGOLD_KEY),
                Arrays.asList(
                        CountPlacementModifier.of(97),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.of(
                                UniformHeightProvider.create(YOffset.aboveBottom(0),YOffset.getTop())
                        ),
                        EnvironmentScanPlacementModifier.of(
                                Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12
                        ),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()
                ));
    }

    public static void ice_caves(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, ICE_CAVES_SNOW_FLOOR, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ICE_CAVES_SNOW_FLOOR),
                Arrays.asList(
                        CountPlacementModifier.of(64),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.of(
                                UniformHeightProvider.create(YOffset.aboveBottom(0),YOffset.getTop())
                        ),
                        EnvironmentScanPlacementModifier.of(
                                Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12
                        ),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
                        BiomePlacementModifier.of()
                ));

        register(context, ICE_CAVES_COMPACTED_SNOW_FLOOR, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ICE_CAVES_COMPACTED_SNOW_FLOOR),
                Arrays.asList(
                        CountPlacementModifier.of(16),
                        SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.of(
                                UniformHeightProvider.create(YOffset.aboveBottom(0),YOffset.getTop())
                        ),
                        EnvironmentScanPlacementModifier.of(
                                Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12
                        ),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
                        BiomePlacementModifier.of()
                ));

        register(context, ICE_CAVES_LAVA_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ICE_CAVES_LAVA),
                List.of());

        register(context, ICE_CAVES_WATER_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ICE_CAVES_WATER),
                List.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Caligo.MOD_ID,name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                 RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
