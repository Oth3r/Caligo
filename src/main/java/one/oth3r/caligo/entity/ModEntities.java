package one.oth3r.caligo.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
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
    public static final EntityType<StrowEntity> STROW = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Caligo.MOD_ID, "strow"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, StrowEntity::new)
                    .dimensions(EntityDimensions.fixed(.4f,.6f)).build());

    public static final EntityType<DeepStrowEntity> DEEP_STROW = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Caligo.MOD_ID, "deep_strow"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, DeepStrowEntity::new)
                    .dimensions(EntityDimensions.fixed(.4f,.6f)).build());

    public static final EntityType<CoppiceEntity> COPPICE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Caligo.MOD_ID, "coppice"),
            FabricEntityTypeBuilder.create(CustomEnum.UNDERGROUND_CREATURE, CoppiceEntity::new)
                    .dimensions(EntityDimensions.fixed(.6f,1.1f)).build());

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
    }
}
