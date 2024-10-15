package one.oth3r.caligo;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageEffects;
import one.oth3r.caligo.block.CustomPathNodes;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.effect.ModEffects;
import one.oth3r.caligo.entity.ModEntities;
import one.oth3r.caligo.generation.ModGeneration;
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
        ModSounds.register();
        ModEffects.register();
        ModBlocks.registerModBlocks();
        ModItems.register();
        ModPotions.register();
        ModGeneration.registerAll();
        ModItemGroups.register();

        // load the FabricASM changes to crash the game if something is wrong (mod compatibility ect)
        DamageEffects dmgEffect = ClassTinkerers.getEnum(DamageEffects.class, "PETRIFICATION");
        SpawnGroup group = ClassTinkerers.getEnum(SpawnGroup.class, "UNDERGROUND_CREATURE");
        LOGGER.info("Hello! Caligo injections complete!");

    }
}
