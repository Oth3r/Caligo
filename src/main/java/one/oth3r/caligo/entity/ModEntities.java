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
import one.oth3r.caligo.entity.strow.StrowEntity;
import one.oth3r.caligo.entity.strow.StrowModel;
import one.oth3r.caligo.entity.strow.StrowRenderer;

public class ModEntities {
    public static final EntityType<StrowEntity> STROW = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Caligo.MOD_ID, "strow"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, StrowEntity::new)
                    .dimensions(EntityDimensions.fixed(.4f,.6f)).build());
    public static void register() {
        FabricDefaultAttributeRegistry.register(ModEntities.STROW, StrowEntity.createBirdAttributes());
    }
    public static void registerClient() {
        EntityRendererRegistry.register(ModEntities.STROW, StrowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.STROW, StrowModel::getTexturedModelData);
    }
}
