package one.oth3r.caligo.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;

public class ModEffects {
    public static StatusEffect PETRIFIED;
    public static void register() {
        PETRIFIED = register("petrified", new PetrifiedEffect(StatusEffectCategory.HARMFUL, 0x8f8f8f)
                .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160899",
                        -0.35f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
    }
    private static StatusEffect register(String id, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Caligo.MOD_ID, id), statusEffect);
    }
}
