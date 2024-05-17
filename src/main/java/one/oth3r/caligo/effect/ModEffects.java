package one.oth3r.caligo.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import one.oth3r.caligo.Caligo;

public class ModEffects {
    public static StatusEffect PETRIFIED = register("petrified", new PetrifiedEffect()
                .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MathHelper.randomUuid(Random.createLocal()).toString(),
            -0.35f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static void register() {}

    private static StatusEffect register(String id, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Caligo.MOD_ID, id), statusEffect);
    }
}
