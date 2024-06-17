package one.oth3r.caligo;

import net.fabricmc.api.ModInitializer;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.effect.ModEffects;
import one.oth3r.caligo.entity.ModEntities;
import one.oth3r.caligo.generation.ModEntityGeneration;
import one.oth3r.caligo.item.ModItems;
import one.oth3r.caligo.potion.ModPotions;
import one.oth3r.caligo.sound.ModSounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Caligo implements ModInitializer {
    public static final String MOD_ID = "caligo";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModEntities.register();
        ModSounds.register();
        ModEffects.register();
        ModBlocks.registerModBlocks();
        ModItems.register();
        ModPotions.register();
        ModEntityGeneration.addSpawns();
    }
}
