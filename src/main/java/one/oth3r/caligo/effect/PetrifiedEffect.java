package one.oth3r.caligo.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.damage.ModDamageTypes;

public class PetrifiedEffect extends StatusEffect {
    protected PetrifiedEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        if (!entity.getWorld().isClient) {
            StatusEffectInstance instance = entity.getStatusEffect(ModEffects.PETRIFIED);
            if (instance == null) return;
            entity.damage(ModDamageTypes.of(entity,ModDamageTypes.PETRIFIED), 1);
        }
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 50 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}
