package one.oth3r.caligo;

import net.fabricmc.api.ModInitializer;
import one.oth3r.caligo.block.CustomPathNodes;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.effect.ModEffects;
import one.oth3r.caligo.entity.ModEntities;
import one.oth3r.caligo.entity.ai.ModSensorTypes;
import one.oth3r.caligo.generation.ModGeneration;
import one.oth3r.caligo.generation.world.features.ModFeatures;
import one.oth3r.caligo.item.ModItems;
import one.oth3r.caligo.itemgroup.ModItemGroups;
import one.oth3r.caligo.potion.ModPotions;
import one.oth3r.caligo.sound.ModSounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Caligo implements ModInitializer {
    public static final String MOD_ID = "caligo";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CustomPathNodes.register();

        ModEntities.register();
        ModSensorTypes.register();
        ModSounds.register();
        ModEffects.register();
        ModBlocks.registerModBlocks();
        ModItems.register();
        ModPotions.register();
        ModGeneration.registerAll();
        ModItemGroups.register();
        ModFeatures.register();

        // load the FabricASM changes to crash the game if something is wrong (mod compatibility ect)
        CustomEnum.load();
        LOGGER.info("[Caligo] Enum injections complete!");
    }
}
