package one.oth3r.caligo.entity.coppice;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class CoppiceEntityRenderState extends LivingEntityRenderState {
    public boolean isPanicking;
    public boolean hasItem;
    public final AnimationState eatingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState holdAnimationState = new AnimationState();
}
