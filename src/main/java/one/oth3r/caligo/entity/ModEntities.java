package one.oth3r.caligo.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.CustomEnum;
import one.oth3r.caligo.entity.strow.deep.DeepStrowEntity;
import one.oth3r.caligo.entity.strow.deep.DeepStrowRenderer;
import one.oth3r.caligo.entity.coppice.CoppiceEntity;
import one.oth3r.caligo.entity.coppice.CoppiceModel;
import one.oth3r.caligo.entity.coppice.CoppiceRenderer;
import one.oth3r.caligo.entity.strow.StrowEntity;
import one.oth3r.caligo.entity.strow.StrowModel;
import one.oth3r.caligo.entity.strow.StrowRenderer;

public class ModEntities {
    public static final EntityType<StrowEntity> STROW = registerEntity("strow",
            EntityType.Builder.create(StrowEntity::new, SpawnGroup.MONSTER)
                    .dimensions(.4f,.6f));

    public static final EntityType<DeepStrowEntity> DEEP_STROW = registerEntity("deep_strow",
            EntityType.Builder.create(DeepStrowEntity::new, SpawnGroup.MONSTER)
                    .dimensions(.4f,.6f));

    public static final EntityType<CoppiceEntity> COPPICE = registerEntity("coppice",
            EntityType.Builder.create(CoppiceEntity::new, CustomEnum.UNDERGROUND_CREATURE)
                    .dimensions(.6f,1.1f).eyeHeight(.62f));

    private static <T extends Entity> EntityType<T> registerEntity(String id, EntityType.Builder<T> type) {
        return registerEntity(keyOf(id), type);
    }

    private static <T extends Entity> EntityType<T> registerEntity(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    private static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Caligo.MOD_ID, id));
    }

    public static void register() {
        FabricDefaultAttributeRegistry.register(ModEntities.STROW, StrowEntity.createStrowAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DEEP_STROW, DeepStrowEntity.createDeepStrowAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.COPPICE, CoppiceEntity.createLushAttributes());
    }

    public static void registerClient() {
        EntityRendererRegistry.register(ModEntities.STROW, StrowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.STROW, StrowModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.DEEP_STROW, DeepStrowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DEEP_STROW, StrowModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.COPPICE, CoppiceRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.COPPICE, CoppiceModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.COPPICE_BABY,
                CoppiceModel::getBabyTexturedModelData);
    }
}
