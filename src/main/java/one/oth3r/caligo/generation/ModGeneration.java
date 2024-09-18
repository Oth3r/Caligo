package one.oth3r.caligo.generation;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import one.oth3r.caligo.entity.ModEntities;
import one.oth3r.caligo.entity.coppice.CoppiceEntity;
import one.oth3r.caligo.entity.strow.deep.DeepStrowEntity;
import one.oth3r.caligo.entity.strow.StrowEntity;

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

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUSH_CAVES), ClassTinkerers.getEnum(SpawnGroup.class, "UNDERGROUND_CREATURE"),
                ModEntities.COPPICE, 100, 1, 1);
        SpawnRestriction.register(ModEntities.COPPICE, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CoppiceEntity::canSpawn);
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
