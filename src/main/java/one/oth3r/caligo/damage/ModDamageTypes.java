package one.oth3r.caligo.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> PETRIFY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Caligo.MOD_ID, "petrify"));

    public static DamageSource of(Entity receiver, RegistryKey<DamageType> key) {
        Registry<DamageType> registry = receiver.getWorld().getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE);

        return new DamageSource(registry.getEntry(registry.get(key)));
    }
}
