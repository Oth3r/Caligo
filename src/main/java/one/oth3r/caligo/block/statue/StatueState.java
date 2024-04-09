package one.oth3r.caligo.block.statue;

import net.minecraft.util.StringIdentifiable;

public enum StatueState implements StringIdentifiable {
    WALK,
    RUN,
    CROUCH;

    @Override
    public String asString() {
        return this.name().toLowerCase();
    }
}
