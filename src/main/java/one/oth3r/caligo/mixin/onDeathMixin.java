package one.oth3r.caligo.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import one.oth3r.caligo.Utl;
import one.oth3r.caligo.block.ModBlocks;
import one.oth3r.caligo.block.statue.StatueBlock;
import one.oth3r.caligo.block.statue.StatueBlockEntity;
import one.oth3r.caligo.effect.ModEffects;
import one.oth3r.caligo.entity.deep_strow.DeepStrowEntity;
import one.oth3r.caligo.entity.strow.StrowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class onDeathMixin extends PlayerEntity {

    /**
     * unused
     */
    public onDeathMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Shadow public abstract ServerWorld getWorld();

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;drop(Lnet/minecraft/entity/damage/DamageSource;)V"))
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
        // STATUE SPAWNING

        // exit if not the strow effects
        if (!(damageSource.getType().msgId().equalsIgnoreCase("caligo_petrified") || damageSource.getSource() instanceof StrowEntity)) return;
        World world = this.getWorld();

        // get the statue block placement
        BlockPos pos = Utl.statue.getPlacement(world,this.getBlockPos());
        if (pos == null) return;

        // get the blockstate for the statue
        BlockState blockState = ModBlocks.STATUE_BLOCK.getDefaultState();

        StatusEffect petrified = ModEffects.getEffect(ModEffects.PETRIFIED);

        // if deep strow or strong petrification, deepslate statue
        if ((this.hasStatusEffect(petrified) && this.getStatusEffect(petrified).getAmplifier() > 0)
                || damageSource.getSource() instanceof DeepStrowEntity) {
            blockState = ModBlocks.DEEPSLATE_STATUE_BLOCK.getDefaultState();
        }

        // rotate the statue and get the state
        blockState
                .with(StatueBlock.ROTATION, (RotationPropertyHelper.fromYaw(this.getYaw())+8)%15) // FLIP THE STATUE 180
                .with(StatueBlock.STATE, Utl.statue.getPlacementState(this));

        // place the blocks into the world
        while (!world.setBlockState(pos, blockState.with(StatueBlock.HALF, DoubleBlockHalf.LOWER)))
            System.out.println("FAILED TO PLACE BLOCK, TRYING AGAIN");
        while (!world.setBlockState(pos.up(), blockState.with(StatueBlock.HALF, DoubleBlockHalf.UPPER)))
            System.out.println("FAILED TO PLACE BLOCK, TRYING AGAIN");

        // if keep inventory is off, yank the player items and put them in the staute
        if (!world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {

            DefaultedList<ItemStack> items = DefaultedList.of();
            int xp = this.totalExperience;
            this.vanishCursedItems();
            items.addAll(this.getInventory().main);
            items.addAll(this.getInventory().armor);
            items.addAll(this.getInventory().offHand);
            this.getInventory().clear();
            this.experienceLevel = 0;
            
            StatueBlockEntity statueBlockEntity = (StatueBlockEntity) world.getBlockEntity(pos);
            statueBlockEntity.setInv(items);
            statueBlockEntity.setXp(xp);
            statueBlockEntity.markDirty();
        }
    }
}
