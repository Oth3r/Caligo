package one.oth3r.caligo.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModSounds {
    private static final Identifier STROW_CAW_ID = new Identifier(Caligo.MOD_ID, "strow_caw");
    public static SoundEvent STROW_CAW = SoundEvent.of(STROW_CAW_ID,30);
    private static final Identifier STROW_ACTIVE_ID = new Identifier(Caligo.MOD_ID, "strow_active");
    public static SoundEvent STROW_ACTIVE = SoundEvent.of(STROW_ACTIVE_ID,30);
    private static final Identifier STROW_DAMAGE_ID = new Identifier(Caligo.MOD_ID, "strow_damage");
    public static SoundEvent STROW_DAMAGE = SoundEvent.of(STROW_DAMAGE_ID,16);
    private static final Identifier STROW_DEATH_ID = new Identifier(Caligo.MOD_ID, "strow_death");
    public static SoundEvent STROW_DEATH = SoundEvent.of(STROW_DEATH_ID,16);
    private static final Identifier PETRIFIED_DAMAGE_ID = new Identifier(Caligo.MOD_ID, "petrified_damage");
    public static SoundEvent PETRIFIED_DAMAGE = SoundEvent.of(PETRIFIED_DAMAGE_ID,16);

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, STROW_CAW_ID, STROW_CAW);
        Registry.register(Registries.SOUND_EVENT, STROW_ACTIVE_ID, STROW_ACTIVE);
        Registry.register(Registries.SOUND_EVENT, STROW_DAMAGE_ID, STROW_DAMAGE);
        Registry.register(Registries.SOUND_EVENT, STROW_DEATH_ID, STROW_DEATH);
        Registry.register(Registries.SOUND_EVENT, PETRIFIED_DAMAGE_ID, PETRIFIED_DAMAGE);
    }
}
