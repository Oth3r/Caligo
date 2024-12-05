package one.oth3r.caligo.entity.strow;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class StrowEntityRenderState extends LivingEntityRenderState {
    public boolean isAngry;
    public boolean isActive;
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState activeAnimationState = new AnimationState();
}
