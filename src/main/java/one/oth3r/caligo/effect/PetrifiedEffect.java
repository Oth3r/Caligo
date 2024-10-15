package one.oth3r.caligo.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import one.oth3r.caligo.damage.ModDamageTypes;
import one.oth3r.caligo.entity.strow.StrowEntity;

public class PetrifiedEffect extends StatusEffect {

    protected PetrifiedEffect() {
        super(StatusEffectCategory.HARMFUL, 0x8f8f8f);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!(entity instanceof StrowEntity)) {
            amplifier++;
            entity.damage(ModDamageTypes.of(entity, ModDamageTypes.PETRIFY), amplifier * 0.75F);
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
