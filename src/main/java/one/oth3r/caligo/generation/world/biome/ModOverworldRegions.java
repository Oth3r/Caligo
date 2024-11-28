package one.oth3r.caligo.generation.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

public class ModOverworldRegions extends Region {
    public ModOverworldRegions(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        // add ice cave region parameters
        new ParameterUtils.ParameterPointListBuilder()
                .temperature(ParameterUtils.Temperature.FROZEN)
                .humidity(ParameterUtils.Humidity.span(ParameterUtils.Humidity.ARID, ParameterUtils.Humidity.HUMID))
                .continentalness(ParameterUtils.Continentalness.INLAND,ParameterUtils.Continentalness.OCEAN)
                .erosion(ParameterUtils.Erosion.FULL_RANGE)
                .depth(ParameterUtils.Depth.UNDERGROUND)
                .weirdness(ParameterUtils.Weirdness.PEAK_NORMAL)
                .build().forEach(point -> builder.add(point, ModBiomes.ICE_CAVES));

        // adds the points to the mapper
        builder.build().forEach(mapper);
    }
}
