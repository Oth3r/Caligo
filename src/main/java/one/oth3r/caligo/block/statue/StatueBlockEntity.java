package one.oth3r.caligo.block.statue;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import one.oth3r.caligo.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

public class StatueBlockEntity extends BlockEntity {
    //set to max player items
    private DefaultedList<ItemStack> inv = DefaultedList.ofSize(size(),ItemStack.EMPTY);

    private int xp = 0;

    public StatueBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.STATUE_BLOCK_ENTITY, pos, state);
    }

    public void setInv(DefaultedList<ItemStack> inv) {
        this.inv = inv;
    }

    public int size() {
        return 50;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, this.inv, registryLookup);
        super.writeNbt(nbt, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        this.inv = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, this.inv, registryLookup);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public boolean isRemoved() {
        return super.isRemoved();
    }

    public DefaultedList<ItemStack> getInv() {
        return this.inv;
    }

    public Integer getXp() {
        return this.xp;
    }

}
