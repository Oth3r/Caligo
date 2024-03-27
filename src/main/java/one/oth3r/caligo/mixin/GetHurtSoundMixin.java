package one.oth3r.caligo.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import one.oth3r.caligo.sound.ModSounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class GetHurtSoundMixin {
    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void getHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
        if (source.getType().msgId().equalsIgnoreCase("caligo_petrified") && source.getType().effects().asString().equalsIgnoreCase("freezing"))
            cir.setReturnValue(ModSounds.PETRIFIED_DAMAGE);
    }
}
