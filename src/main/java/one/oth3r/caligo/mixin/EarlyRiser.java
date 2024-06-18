package one.oth3r.caligo.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import one.oth3r.caligo.sound.ModSounds;

public class EarlyRiser implements Runnable {

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        String dmgEffects = remapper.mapClassName("intermediary","net.minecraft.class_8107"); //DamageEffects
        String soundEvent = 'L' + remapper.mapClassName("intermediary","net.minecraft.class_3414") + ';'; //SoundEvent

        // add the petrification damage effect
        ClassTinkerers.enumBuilder(dmgEffects,"Ljava/lang/String;",soundEvent)
                .addEnum("PETRIFICATION",
        () -> new Object[]{"petrification", ModSounds.PETRIFIED_DAMAGE}).build();
    }
}
