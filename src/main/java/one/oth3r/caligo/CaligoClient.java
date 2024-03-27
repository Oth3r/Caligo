package one.oth3r.caligo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.entity.ModEntities;

public class CaligoClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModEntities.registerClient();
        ModBlocks.registerClient();
    }
}
