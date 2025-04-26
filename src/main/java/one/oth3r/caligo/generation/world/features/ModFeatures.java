package one.oth3r.caligo.generation.world.features;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.feature.*;
import one.oth3r.caligo.generation.world.features.feature.IceCavesSpikeFeature;
import one.oth3r.caligo.generation.world.features.feature.IceCavesFluidFeature;

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> ICE_CAVES_FLUID = registerFeature("ice_caves_fluid", new IceCavesFluidFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> ICE_CAVES_SPIKE = registerFeature("ice_caves_spike", new IceCavesSpikeFeature(DefaultFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registries.FEATURE, name, feature);
    }

    public static void register() {}
}
