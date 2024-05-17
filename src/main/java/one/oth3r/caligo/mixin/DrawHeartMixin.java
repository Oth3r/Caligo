package one.oth3r.caligo.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.effect.ModEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class DrawHeartMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "drawHeart", at = @At(value = "HEAD"), cancellable = true)
    private void drawHeart(DrawContext context, InGameHud.HeartType type, int x, int y, boolean hardcore, boolean blinking, boolean half, CallbackInfo ci) {
        if (!type.equals(InGameHud.HeartType.NORMAL)) return;
        PlayerEntity player = this.client.player;
        if (player == null) return;
        if (player.hasStatusEffect(ModEffects.PETRIFIED)) {
            Identifier texture;
            if (player.getStatusEffect(ModEffects.PETRIFIED).getAmplifier() > 0) {
                texture = new Identifier("hud/heart/deep_petrified_full");
                if (half) texture = new Identifier("hud/heart/deep_petrified_half");
            } else {
                texture = new Identifier("hud/heart/petrified_full");
                if (half) texture = new Identifier("hud/heart/petrified_half");
            }

            context.drawGuiTexture(texture, x, y, 9, 9);
            ci.cancel();
        }
    }
}
