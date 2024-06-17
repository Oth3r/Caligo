package one.oth3r.caligo.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import one.oth3r.caligo.damage.ModDamageTypes;

public class PetrifiedEffect extends StatusEffect {
    protected PetrifiedEffect() {
        super(StatusEffectCategory.HARMFUL, 0x8f8f8f);
    }
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(entity.getDamageSources().create(ModDamageTypes.PETRIFY), 1.0F);
        return true;
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
