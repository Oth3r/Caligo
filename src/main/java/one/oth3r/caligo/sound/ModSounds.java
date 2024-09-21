package one.oth3r.caligo.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModSounds {
    public static SoundEvent STROW_CAW = registerSound("strow_caw",50);
    public static SoundEvent STROW_ACTIVE = registerSound("strow_active",25);
    public static SoundEvent STROW_DAMAGE = registerSound("strow_damage");
    public static SoundEvent STROW_DEATH = registerSound("strow_death");

    public static SoundEvent PETRIFIED_DAMAGE = registerSound("petrified_damage");

    public static final SoundEvent COPPICE_DAMAGE = registerSound("coppice_damage");
    public static final SoundEvent COPPICE_DEATH = registerSound("coppice_death");
    public static final SoundEvent COPPICE_EAT = registerSound("coppice_eat");
    public static final SoundEvent COPPICE_AMIRE = registerSound("coppice_admire");
    public static final SoundEvent COPPICE_PICKUP = registerSound("coppice_pickup");

    public static void register() {}

    private static SoundEvent registerSound(String id) {
        return registerSound(id,16F);
    }

    private static SoundEvent registerSound(String id, float distanceToTravel) {
        Identifier identifier = Identifier.of(Caligo.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier,distanceToTravel));
    }
}
