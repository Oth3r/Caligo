package one.oth3r.caligo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.effect.ModEffects;
import one.oth3r.caligo.entity.ModEntities;
import one.oth3r.caligo.entity.strow.StrowEntity;
import one.oth3r.caligo.generation.ModEntityGeneration;
import one.oth3r.caligo.item.ModItems;
import one.oth3r.caligo.sound.ModSounds;

public class Caligo implements ModInitializer {
    public static final String MOD_ID = "caligo";
    @Override
    public void onInitialize() {
        ModEntities.register();
        ModSounds.register();
        ModEffects.register();
        ModBlocks.registerModBlocks();
        ModItems.register();
        ModEntityGeneration.addSpawns();
        StrowEntity.strowHitLogic();
    }
}
