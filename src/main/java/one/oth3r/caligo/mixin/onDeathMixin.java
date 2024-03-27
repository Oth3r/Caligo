package one.oth3r.caligo.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import one.oth3r.caligo.Utl;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.block.statue.StatueBlock;
import one.oth3r.caligo.block.statue.StatueBlockEntity;
import one.oth3r.caligo.entity.strow.StrowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class onDeathMixin {
    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;drop(Lnet/minecraft/entity/damage/DamageSource;)V"))
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
        ServerPlayerEntity player = ((ServerPlayerEntity)(Object)this);
        if (damageSource.getType().msgId().equalsIgnoreCase("caligo_petrified") || damageSource.getSource() instanceof StrowEntity) {
            World world = player.getWorld();
            if (!world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
                BlockPos pos = Utl.statue.getPlacement(world,player.getBlockPos());
                if (pos == null) return;
                DefaultedList<ItemStack> items = DefaultedList.of();
                int xp = player.totalExperience;
                ((PlayerInvoker) player).callVanishCursedItems();
                items.addAll(player.getInventory().main);
                items.addAll(player.getInventory().armor);
                items.addAll(player.getInventory().offHand);
                player.getInventory().clear();
                player.experienceLevel = 0;
                BlockState blockState = ModBlocks.STATUE_BLOCK.getDefaultState()
                        .with(StatueBlock.ROTATION, (RotationPropertyHelper.fromYaw(player.getYaw())+8)%15) // FLIP THE STATUE 180
                        .with(StatueBlock.STATE, Utl.statue.getPlacementState(player));
                while (!world.setBlockState(pos, blockState.with(StatueBlock.HALF, DoubleBlockHalf.LOWER)))
                    System.out.println("FAILED TO PLACE BLOCK, TRYING AGAIN");
                while (!world.setBlockState(pos.up(), blockState.with(StatueBlock.HALF, DoubleBlockHalf.UPPER)))
                    System.out.println("FAILED TO PLACE BLOCK, TRYING AGAIN");
                StatueBlockEntity statueBlockEntity = (StatueBlockEntity) world.getBlockEntity(pos);
                statueBlockEntity.setInv(items);
                statueBlockEntity.setXp(xp);
                statueBlockEntity.markDirty();
            }
        }
    }
}
