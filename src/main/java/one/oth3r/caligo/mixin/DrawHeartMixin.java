package one.oth3r.caligo.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
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
public abstract class DrawHeartMixin extends DrawableHelper {

    @Shadow @Final private MinecraftClient client;


    @Inject(method = "drawHeart", at = @At(value = "HEAD"), cancellable = true)
    private void drawHeart(MatrixStack matrices, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart, CallbackInfo ci) {
        if (!type.equals(InGameHud.HeartType.NORMAL)) return;
        PlayerEntity player = this.client.player;
        if (player == null) return;

        if (player.hasStatusEffect(ModEffects.getEffect(ModEffects.PETRIFIED))) {
            Identifier texture;
            if (player.getStatusEffect(ModEffects.getEffect(ModEffects.PETRIFIED)).getAmplifier() > 0) {
                texture = new Identifier("textures/gui/sprites/hud/heart/deep_petrified_full.png");
                if (halfHeart) texture = new Identifier("textures/gui/sprites/hud/heart/deep_petrified_half.png");
            } else {
                texture = new Identifier("textures/gui/sprites/hud/heart/petrified_full.png");
                if (halfHeart) texture = new Identifier("textures/gui/sprites/hud/heart/petrified_half.png");
            }

            RenderSystem.setShaderTexture(0, texture);
            drawTexture(matrices, x, y, 0, 0, 9, 9,9,9,9);
            RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);

            ci.cancel();
        }
    }
}
