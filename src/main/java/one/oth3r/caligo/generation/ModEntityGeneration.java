package one.oth3r.caligo.generation;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;
import one.oth3r.caligo.entity.ModEntities;
import one.oth3r.caligo.entity.strow.deep.DeepStrowEntity;
import one.oth3r.caligo.entity.strow.StrowEntity;

public class ModEntityGeneration {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER,
                ModEntities.STROW, 35, 1, 2);
        SpawnRestriction.register(ModEntities.STROW, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, StrowEntity::canSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER,
                ModEntities.DEEP_STROW, 35, 2, 4);
        SpawnRestriction.register(ModEntities.DEEP_STROW, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DeepStrowEntity::canSpawn);
    }
}
