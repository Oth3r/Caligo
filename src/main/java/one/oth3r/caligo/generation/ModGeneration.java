package one.oth3r.caligo.generation;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.BoggedEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import one.oth3r.caligo.CustomEnum;
import one.oth3r.caligo.entity.ModEntities;
import one.oth3r.caligo.entity.coppice.CoppiceEntity;
import one.oth3r.caligo.entity.cryonix.CryonixEntity;
import one.oth3r.caligo.entity.strow.deep.DeepStrowEntity;
import one.oth3r.caligo.entity.strow.StrowEntity;
import one.oth3r.caligo.generation.world.biome.ModBiomes;
import one.oth3r.caligo.generation.world.features.ModPlacedFeatures;

import java.util.ArrayList;
import java.util.List;

public class ModGeneration {

    public static void registerAll() {
        addEntitySpawns();
        addLushChanges();
    }

    public static void addEntitySpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER,
                ModEntities.STROW, 35, 1, 2);
        SpawnRestriction.register(ModEntities.STROW, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, StrowEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER,
                ModEntities.DEEP_STROW, 35, 2, 4);
        SpawnRestriction.register(ModEntities.DEEP_STROW, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DeepStrowEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUSH_CAVES), CustomEnum.UNDERGROUND_CREATURE,
                ModEntities.COPPICE, 100, 1, 1);
        SpawnRestriction.register(ModEntities.COPPICE, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CoppiceEntity::canSpawn);

        List<RegistryKey<Biome>> snowyBiomes = List.of(BiomeKeys.SNOWY_BEACH,BiomeKeys.SNOWY_PLAINS,BiomeKeys.SNOWY_SLOPES,BiomeKeys.SNOWY_TAIGA,BiomeKeys.ICE_SPIKES, ModBiomes.ICE_CAVES);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(snowyBiomes), SpawnGroup.MONSTER,
                ModEntities.CRYONIX, 80, 4, 4);
        SpawnRestriction.register(ModEntities.CRYONIX, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CryonixEntity::canMobSpawn);

    }

    public static void addLushChanges() {
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.LUSH_CAVES),
                GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.DRIPLEAF_VINES_PLACED_KEY);

        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.LUSH_CAVES),
                GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.PETUNIA_PLACED_KEY);

        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.LUSH_CAVES),
                GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.LUSH_MARIGOLD_PLACED_KEY);
    }
}
