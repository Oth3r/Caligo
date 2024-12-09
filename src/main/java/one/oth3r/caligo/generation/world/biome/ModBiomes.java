package one.oth3r.caligo.generation.world.biome;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.UndergroundPlacedFeatures;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.generation.world.ModCarvers;
import one.oth3r.caligo.generation.world.features.ModPlacedFeatures;

public class ModBiomes {
    public static final RegistryKey<Biome> ICE_CAVES = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(Caligo.MOD_ID, "ice_caves"));

    public static void boostrap(Registerable<Biome> context) {
        RegistryEntryLookup<PlacedFeature> featureLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        RegistryEntryLookup<ConfiguredCarver<?>> carverLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
        context.register(ICE_CAVES, iceCaveBiome(featureLookup,carverLookup));
    }

    /**
     * the ice caves biome settings
     */
    public static Biome iceCaveBiome(RegistryEntryLookup<PlacedFeature> featureLookup, RegistryEntryLookup<ConfiguredCarver<?>> carverLookup) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();

        DefaultBiomeFeatures.addSnowyMobs(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(featureLookup,carverLookup);

//        biomeBuilder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CAVE);
        biomeBuilder.carver(ModCarvers.ICE_CAVE);
//        biomeBuilder.carver(GenerationStep.Carver.AIR, ConfiguredCarvers.CANYON);
        DefaultBiomeFeatures.addAmethystGeodes(biomeBuilder);
        DefaultBiomeFeatures.addDungeons(biomeBuilder);
        DefaultBiomeFeatures.addMineables(biomeBuilder);
        DefaultBiomeFeatures.addFrozenTopLayer(biomeBuilder);
        DefaultBiomeFeatures.addPlainsTallGrass(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);
        DefaultBiomeFeatures.addDefaultDisks(biomeBuilder);

        DefaultBiomeFeatures.addDefaultMushrooms(biomeBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(biomeBuilder);

        biomeBuilder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, UndergroundPlacedFeatures.POINTED_DRIPSTONE);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.ICE_CAVES_SNOW_FLOOR);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.ICE_CAVES_COMPACTED_SNOW_FLOOR);
//        biomeBuilder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.ICE_CAVES_LAVA_PLACED_KEY);
        biomeBuilder.feature(GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.ICE_CAVES_WATER_PLACED_KEY);

        float temp = -0.7f;

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.4f)
                .temperature(temp)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(4020182)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(OverworldBiomeCreator.getSkyColor(temp))
                        .moodSound(BiomeMoodSound.CAVE)
//                        .music(MusicType.createIngameMusic(RegistryEntry.of(ModSounds.MUSIC_OVERWORLD_ICE_CAVES)))
                        .build())
                .build();
    }
}
