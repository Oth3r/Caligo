package one.oth3r.caligo.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import one.oth3r.caligo.Caligo;

public class ModEffects {
    public static StatusEffect PETRIFIED = register("petrified", new PetrifiedEffect()
                .addAttributeModifier(EntityAttributes.MOVEMENT_SPEED, Identifier.of(Caligo.MOD_ID,"petrified_slowness"),
            -0.35f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static RegistryEntry<StatusEffect> getEffect(StatusEffect effect) {
        return Registries.STATUS_EFFECT.getEntry(effect);
    }

    public static void register() {}

    private static StatusEffect register(String id, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, Identifier.of(Caligo.MOD_ID, id), statusEffect);
    }
}
