package one.oth3r.caligo.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import one.oth3r.caligo.effect.ModEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(InGameHud.class)
public abstract class HudOverlayMixin {

    @Unique private static final Identifier PETRIFIED_OUTLINE = new Identifier("textures/misc/petrified_outline.png");
    @Unique private static final Identifier DEEP_PETRIFIED_OUTLINE = new Identifier("textures/misc/deep_petrified_outline.png");

    @Unique private final Integer MAX = 140;

    @Unique private Double notMovingTimer = 0.0;

    @Unique private final ArrayList<Vec3d> lastPos = new ArrayList<>();

    /**
     * gets the opacity for the overlay, using the notMovingTimer
     * @return 0 - 1
     */
    @Unique
    public float getOpacity() {
        return (float)Math.min(notMovingTimer, MAX) / (float) MAX;
    }

    /**
     * gets the player speed by comparing the position from ~5 ticks ago
     * @return speed as double
     */
    @Unique
    public double playerSpeed(PlayerEntity player) {
        // add the player's pos and remove the y level
        lastPos.add(player.getPos().add(0,player.getPos().y*-1,0));
        // remove the first entry if there are more than 5
        if (lastPos.size() > 5) lastPos.remove(0);
        return lastPos.get(0).distanceTo(lastPos.get(lastPos.size()-1));
    }

    @Shadow @Final private MinecraftClient client;

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I"))
    public void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        PlayerEntity player = this.client.player;
        if (player == null) return;

        // render + logic
        if (player.hasStatusEffect(ModEffects.getEffect(ModEffects.PETRIFIED))) {

            // if player isn't moving increase the timer, if not decrease
            if (playerSpeed(player) == 0) {
                if (notMovingTimer < MAX) notMovingTimer += 0.2;
            } else if (notMovingTimer > 0) notMovingTimer -= 0.4;

            // render the overlay
            if (player.getStatusEffect(ModEffects.getEffect(ModEffects.PETRIFIED)).getAmplifier() > 0) {
                this.renderOverlay(context, DEEP_PETRIFIED_OUTLINE, getOpacity());
            } else {
                this.renderOverlay(context, PETRIFIED_OUTLINE, getOpacity());
            }
        }

        // reset timer when not effected by potion
        else if (notMovingTimer != 0) notMovingTimer = 0.0;
    }
}
