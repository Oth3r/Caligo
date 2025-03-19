package one.oth3r.caligo;

import net.fabricmc.api.ClientModInitializer;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.entity.ModEntities;
import one.oth3r.caligo.particle.ModParticles;

public class CaligoClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModEntities.registerClient();
        ModBlocks.registerClient();
        ModParticles.registerClient();
    }
}
