package one.oth3r.caligo;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageEffects;

public class CustomEnum {
    public static final DamageEffects PETRIFICATION = ClassTinkerers.getEnum(DamageEffects.class, "PETRIFICATION");
    public static final SpawnGroup UNDERGROUND_CREATURE = ClassTinkerers.getEnum(SpawnGroup.class, "UNDERGROUND_CREATURE");

    public static void load() {}
}
