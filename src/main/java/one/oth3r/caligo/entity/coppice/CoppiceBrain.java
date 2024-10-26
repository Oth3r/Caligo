package one.oth3r.caligo.entity.coppice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.kinds.K1;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import one.oth3r.caligo.loot_table.ModLootTables;
import one.oth3r.caligo.sound.ModSounds;
import one.oth3r.caligo.tag.ModItemTags;

import java.util.*;
import java.util.function.Predicate;

public class CoppiceBrain {

    private static final int ADMIRING_DISABLED_EXPIRY = 300;
    private static final int ADMIRE_TIME = 300;
    private static final float WALKING_SPEED;
    private static final float RUNNING_SPEED;
    private static final UniformIntProvider AVOID_MEMORY_DURATION;

    public CoppiceBrain() {}

    public static Predicate<ItemStack> getTemptItemPredicate() {
        return (stack) -> stack.isIn(ModItemTags.COPPICE_FOOD);
    }

    protected static Brain<?> create(CoppiceEntity coppice, Brain<CoppiceEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addAdmireItemActivities(brain);
        addAvoidActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<CoppiceEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new StayAboveWaterTask<>(.8f),
                new LookAroundTask(UniformIntProvider.create(40,50),90,0,0),
                new MoveToTargetTask(200,500), AdmireTask.create(ADMIRE_TIME), new TickCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                RemoveHandItemTask.create()));
    }

    private static void addIdleActivities(Brain<CoppiceEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
//                Pair.of(0, new BreedTask(ModEntities.COPPICE)),
//                Pair.of(1, new TemptTask((coppice) -> WALKING_SPEED, (entity) -> entity.isBaby() ? 1 : 1.5)), // todo speeds
                Pair.of(2, WalkToNearestPlayerHoldingWantedItemTask.create(CoppiceBrain::hasPlayerHoldingWantedItemNearby, WALKING_SPEED, true, 14)),
                Pair.of(3, makeRandomLookTask()), Pair.of(4, makeRandomWanderTask())
//                FindInteractionTargetTask.create(EntityType.PLAYER, 4)
        ));
    }

    private static void addAdmireItemActivities(Brain<CoppiceEntity> brain) {
        brain.setTaskList(Activity.ADMIRE_ITEM, 10, ImmutableList.of(
                WalkTowardsNearestVisibleWantedItemTask.create(CoppiceEntity::doesNotHaveItemInHand, WALKING_SPEED, true, 9),
                GoToRememberedPositionTask.createEntityBased(MemoryModuleType.AVOID_TARGET, RUNNING_SPEED, 5, true),
                WantMoreItemTask.create(9),
                AdmireTimeLimitTask.create(200, 200), makeRandomWanderTask()), MemoryModuleType.ADMIRING_ITEM);
    }

    private static void addAvoidActivities(Brain<CoppiceEntity> brain) {
        brain.setTaskList(Activity.AVOID, 10, ImmutableList.of(
                GoToRememberedPositionTask.createEntityBased(MemoryModuleType.AVOID_TARGET, RUNNING_SPEED, 12, true),
                makeRandomLookTask(), makeRandomWanderTask()), MemoryModuleType.AVOID_TARGET);
    }

    private static RandomTask<LivingEntity> makeRandomLookTask() {
        return new RandomTask<>(ImmutableList.of(
                Pair.of(LookAtMobTask.create(EntityType.PLAYER, 8.0F), 2),
                Pair.of(LookAtMobTask.create(8.0F), 2),
                Pair.of(new WaitTask(80, 120), 1)));
    }

    private static RandomTask<CoppiceEntity> makeRandomWanderTask() {
        return new RandomTask<>(ImmutableList.of(
                Pair.of(StrollTask.create(WALKING_SPEED), 2),
                Pair.of(TaskTriggerer.runIf(CoppiceBrain::canWander,GoToLookTargetTask.create(WALKING_SPEED, 3)), 2),
                Pair.of(makeGoToDripleafTask(), 1),
                Pair.of(new WaitTask(80, 120), 1)));
    }

    private static Task<PathAwareEntity> makeGoToDripleafTask() {
        return GoToRememberedPositionTask.createPosBased(MemoryModuleType.NEAREST_REPELLENT, WALKING_SPEED, 8, false);
    }

    protected static void tickActivities(CoppiceEntity entity) {
        if (entity.getSoundCooldown() > 0) entity.setSoundCooldown(entity.getSoundCooldown()-1);
        Brain<CoppiceEntity> brain = entity.getBrain();

        Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);

        // if admiring
        if (brain.hasMemoryModule(MemoryModuleType.ADMIRING_ITEM) && !entity.doesNotHaveItemInHand()) {
            float expiry = brain.getMemoryExpiry(MemoryModuleType.ADMIRING_ITEM);
            // if 20 ticks from finishing, start the eating animation
            if (expiry == 20f) {
                entity.setEating(true);
                entity.playSound(ModSounds.COPPICE_EAT);
            }
            if (expiry == 15f) {
                entity.playSound(ModSounds.COPPICE_EAT);
            }
            if (expiry == 10f) {
                entity.playSound(ModSounds.COPPICE_EAT);
            }
            if (expiry == 5f) {
                entity.playSound(ModSounds.COPPICE_EAT);
            }
            // if at the end of the admiration, stop the animation & play the eating finish sound
            if (expiry == 0f) {
                entity.setEating(false);
                entity.playSound(ModSounds.COPPICE_EAT);
            }
        }

        brain.resetPossibleActivities(ImmutableList.of(Activity.ADMIRE_ITEM, Activity.AVOID, Activity.RIDE, Activity.IDLE));
        Activity activity2 = brain.getFirstPossibleNonCoreActivity().orElse(null);

        // if doing a new activity, get the sound
        if (activity != activity2 && entity.getSoundCooldown() == 0) {
            Optional<SoundEvent> sound = getCurrentActivitySound(entity);
            Objects.requireNonNull(entity);
            sound.ifPresent(entity::playSound);
            entity.setSoundCooldown(80);
        }

    }

    protected static void loot(CoppiceEntity entity, ItemEntity drop) {
        // stop admiring and disable it
        Brain<CoppiceEntity> brain = entity.getBrain();
        if (brain.hasMemoryModule(MemoryModuleType.ADMIRING_DISABLED)) {
            return;
        }

        // stop nav
        stopWalking(entity);
        ItemStack itemStack;

        // pickup one from the stack
        entity.sendPickup(drop, 1);
        itemStack = getItemFromStack(drop);

        // figure out what is picked up
        if (isWantedItem(itemStack)) {
            entity.getBrain().forget(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            entity.equipToMainHand(itemStack);
            setAdmiringItem(entity);
        }
    }

    /**
     * gets one item from a stack of item, while editing the original stack
     */
    private static ItemStack getItemFromStack(ItemEntity stack) {
        ItemStack itemStack = stack.getStack();
        ItemStack item = itemStack.split(1);
        // if only 1, discard
        if (itemStack.isEmpty()) stack.discard();
        // else set to the reduced stack size
        else stack.setStack(itemStack);
        // return the singular item itemstack
        return item;
    }

    /**
     * consumes the item currently held by the coppice, dropping the loot needed
     * @param entity
     */
    protected static void consumeHandItem(CoppiceEntity entity) {
        ItemStack handStack = entity.getStackInHand(Hand.MAIN_HAND);

        // empty the hand stack
        entity.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);

        // if the entity isnt a baby, start the drop code
        if (!entity.isBaby()) {
            dropItems(entity, generateDropItem(entity, handStack));
        }

    }

    /**
     * drops a list of itemstacks on the floor
     */
    private static void dropItems(CoppiceEntity entity, List<ItemStack> items) {
        if (!items.isEmpty()) {
            // swing the hand
            entity.swingHand(Hand.MAIN_HAND);

            Iterator<ItemStack> item = items.iterator();
            // iterate through the items
            while (item.hasNext()) {
                // drop the item
                TargetUtil.give(entity, item.next(), findGround(entity).add(0.0, 1.0, 0.0));
            }
        }
    }

    /**
     * generates the drop item using the currently admiring item and the entity
     * @return the item to drop
     */
    private static List<ItemStack> generateDropItem(CoppiceEntity entity, ItemStack admiringItem) {
        LootTable lootTable = entity.getWorld().getServer().getReloadableRegistries().getLootTable(ModLootTables.COPPICE_RAW_REMAINS);
        // if the item held is of high tier drops, and the coppice isnt a baby, change the loottable to the better one
        if (admiringItem.isIn(ModItemTags.COPPICE_HIGH_TIER) && !entity.isBaby()) lootTable = entity.getWorld().getServer().getReloadableRegistries().getLootTable(ModLootTables.COPPICE_GEM_REMAINS);

        // generate the item from the loottable and return it
        return lootTable.generateLoot((new LootWorldContext.Builder((ServerWorld)entity.getWorld())).add(LootContextParameters.THIS_ENTITY, entity).build(LootContextTypes.BARTER));
    }

    /**
     * if the coppice can gather an item, (if they arent holding + they want it + they arent admiring an item)
     */
    protected static boolean canGather(CoppiceEntity entity, ItemStack stack) {
        return canPickupItem(stack) && entity.doesNotHaveItemInHand() && !CoppiceBrain.isAdmiringItem(entity);
    }

    protected static void onAttacked(CoppiceEntity entity, LivingEntity attacker) {
        if (!(attacker instanceof CoppiceEntity)) {
            // get rid of the item in hand when attacked
            if (!entity.doesNotHaveItemInHand()) {
                entity.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            }

            // stop admiring and disable it
            Brain<CoppiceEntity> brain = entity.getBrain();
            brain.forget(MemoryModuleType.ADMIRING_ITEM);
            if (attacker instanceof PlayerEntity) {
                brain.remember(MemoryModuleType.ADMIRING_DISABLED, true, ADMIRING_DISABLED_EXPIRY);
            }


            // if not already avoiding target / forget them
            getAvoiding(entity).ifPresent((avoiding) -> {
                if (avoiding.getType() != attacker.getType()) {
                    brain.forget(MemoryModuleType.AVOID_TARGET);
                }
            });

            // avoid the target
            runAwayFrom(entity, attacker);
        }
    }

    /**
     * gets a sound based on the current activity
     */
    public static Optional<SoundEvent> getCurrentActivitySound(CoppiceEntity entity) {
        return entity.getBrain().getFirstPossibleNonCoreActivity().map(act -> getSound(entity,act));
    }

    /**
     * gets a sound based on activity
     */
    private static SoundEvent getSound(CoppiceEntity entity, Activity activity) {
        if (activity == Activity.AVOID) {
            return null;
        } else if (activity == Activity.ADMIRE_ITEM && entity.doesNotHaveItemInHand()) {
            return ModSounds.COPPICE_AMIRE;
        } else {
            return null;
        }
    }

    private static void stopWalking(CoppiceEntity entity) {
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
        entity.getNavigation().stop();
    }

    public static Optional<LivingEntity> getAvoiding(CoppiceEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.AVOID_TARGET) ? entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.AVOID_TARGET) : Optional.empty();
    }

    public static Optional<PlayerEntity> getNearestDetectedPlayer(CoppiceEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER) ? entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER) : Optional.empty();
    }

    protected static void runAwayFrom(CoppiceEntity entity, LivingEntity target) {
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
        entity.getBrain().remember(MemoryModuleType.AVOID_TARGET, target, AVOID_MEMORY_DURATION.get(entity.getWorld().random));
    }

    private static Vec3d findGround(CoppiceEntity entity) {
        Vec3d vec3d = FuzzyTargeting.find(entity, 4, 2);
        return vec3d == null ? entity.getPos() : vec3d;
    }

    private static void setAdmiringItem(CoppiceEntity entity) {
        entity.getBrain().remember(MemoryModuleType.ADMIRING_ITEM, true, ADMIRE_TIME);
        if (CoppiceBrain.getNearestDetectedPlayer(entity).isPresent()) {
            entity.playSound(ModSounds.COPPICE_PICKUP);
            runAwayFrom(entity, CoppiceBrain.getNearestDetectedPlayer(entity).get());
        }
    }

    private static boolean isAdmiringItem(CoppiceEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.ADMIRING_ITEM);
    }

    private static boolean hasPlayerHoldingWantedItemNearby(LivingEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
    }

    private static boolean canWander(LivingEntity entity) {
        return !hasPlayerHoldingWantedItemNearby(entity);
    }

    protected static boolean isWantedItem(ItemStack stack) {
        return canPickupItem(stack) || stack.isIn(ItemTags.PICKAXES);
    }

    protected static boolean canPickupItem(ItemStack stack) {
        return stack.isIn(ModItemTags.COPPICE_LOW_TIER) || stack.isIn(ModItemTags.COPPICE_HIGH_TIER);
    }

    public static boolean isWantedHoldingPlayer(LivingEntity target) {
        return target.getType() == EntityType.PLAYER && target.isHolding(CoppiceBrain::isWantedItem);
    }

    protected static boolean isPanicking(CoppiceEntity entity) {
        return entity.getBrain().hasMemoryModule(MemoryModuleType.AVOID_TARGET);
    }

    static {
        AVOID_MEMORY_DURATION = TimeHelper.betweenSeconds(10, 20);
        WALKING_SPEED = CoppiceEntity.WALKING_SPEED;
        RUNNING_SPEED = CoppiceEntity.RUNNING_SPEED;
    }



    /**
     * walks to the nearest player holding a wanted item,
     * uses the tempt cooldown
     */
    public static class WalkToNearestPlayerHoldingWantedItemTask {
        public WalkToNearestPlayerHoldingWantedItemTask() {}

        public static <E extends LivingEntity> Task<E> create(Predicate<E> startCondition, float speed, boolean requiresWalkTarget, int radius) {
            return TaskTriggerer.task((context) -> {
                TaskTriggerer<E, ? extends MemoryQueryResult<? extends K1, WalkTarget>> taskTriggerer = requiresWalkTarget ? context.queryMemoryOptional(MemoryModuleType.WALK_TARGET) : context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET);
                return context.group(context.queryMemoryOptional(MemoryModuleType.LOOK_TARGET), taskTriggerer, context.queryMemoryValue(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM), context.queryMemoryOptional(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS))
                        .apply(context, (lookTarget, walkTarget, nearestPlayerHoldingWantedItem, temptingTicks) -> (world, entity, time) -> {
                            // if the task can't start, false
                            if (!startCondition.test(entity)) return false;
                            // get the player
                            PlayerEntity holdingPlayer = context.getValue(nearestPlayerHoldingWantedItem);

                            // if temping cooldown is empty, the player is in range & inside the world border
                            if (context.getOptionalValue(temptingTicks).isEmpty() && holdingPlayer.isInRange(entity, radius) && entity.getWorld().getWorldBorder().contains(holdingPlayer.getBlockPos())) {
                                WalkTarget walkTargetx = new WalkTarget(new EntityLookTarget(holdingPlayer, false), speed, 1);
                                lookTarget.remember(new EntityLookTarget(holdingPlayer, true));
                                walkTarget.remember(walkTargetx);
                                temptingTicks.remember(world.random.nextBetweenExclusive(200,500));
                                return true;
                            } else {
                                return false;
                            }
                        });
            });
        }
    }

    /**
     * sets a time limit for admiring an item
     */
    protected static class AdmireTimeLimitTask {
        public static Task<CoppiceEntity> create(int cooldown, int timeLimit) {
            return TaskTriggerer.task(context -> context.group(
                    context.queryMemoryValue(MemoryModuleType.ADMIRING_ITEM),
                    context.queryMemoryValue(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM),
                    context.queryMemoryOptional(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM),
                    context.queryMemoryOptional(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM))
                    .apply(context, (admiringItem, nearestVisibleWantedItem, timeTryingToReachAdmireItem, disableWalkToAdmireItem) -> (world, entity, time) -> {

                if (!entity.getMainHandStack().isEmpty()) {
                    return false;
                }

                Optional<Integer> optionalTimeTrying = context.getOptionalValue(timeTryingToReachAdmireItem);
                // if there's no time trying, remember a time
                if (optionalTimeTrying.isEmpty()) {
                    timeTryingToReachAdmireItem.remember(0);
                }
                // else count up the timer
                else {
                    int timeTryingToReach = optionalTimeTrying.get();
                    // if the time trying to reach is more than the time limit, disable trying to get the item
                    if (timeTryingToReach > timeLimit) {
                        admiringItem.forget();
                        timeTryingToReachAdmireItem.forget();
                        disableWalkToAdmireItem.remember(true, cooldown);
                    }
                    // else count up
                    else timeTryingToReachAdmireItem.remember(timeTryingToReach + 1);
                }
                return true;
            }));
        }
    }

    /**
     * admires the item if wanted
     */
    protected static class AdmireTask {
        public static Task<CoppiceEntity> create(int duration) {
            return TaskTriggerer.task(context -> context.group(
                    context.queryMemoryValue(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM), context.queryMemoryAbsent(MemoryModuleType.ADMIRING_ITEM),
                    context.queryMemoryAbsent(MemoryModuleType.ADMIRING_DISABLED), context.queryMemoryAbsent(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM))
                    .apply(context, (nearestVisibleWantedItem, admiringItem, admiringDisabled, disableWalkToAdmireItem) -> (world, entity, time) -> {
                // get the item
                ItemEntity itemEntity = context.getValue(nearestVisibleWantedItem);
                // return if unwanted
                if (!isWantedItem(itemEntity.getStack())) return false;
                // if the item is a wanted item, admire it for the duration entered
                admiringItem.remember(true, duration);
                return true;
            }));
        }
    }

    /**
     * makes the entity want more items by forgetting the admiring item
     */
    protected static class WantMoreItemTask {
        public static Task<CoppiceEntity> create(int range) {
            return TaskTriggerer.task(context -> context.group(
                    context.queryMemoryValue(MemoryModuleType.ADMIRING_ITEM),
                    context.queryMemoryOptional(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM))
                    .apply(context, (admiringItem, nearestVisibleWantedItem) -> (world, entity, time) -> {
                // don't be greedy! return if already have item in hand
                if (!entity.getMainHandStack().isEmpty()) return false;

                Optional<ItemEntity> optionalWantedItem = context.getOptionalValue(nearestVisibleWantedItem);
                // return if in range
                if (optionalWantedItem.isPresent() && optionalWantedItem.get().isInRange(entity, range)) {
                    return false;
                }
                // forgets the admiring item if it's not present or was picked up, making it able to pick up other items
                admiringItem.forget();
                return true;
            }));
        }
    }

    /**
     * removes the item from the hand when possible
     */
    protected static class RemoveHandItemTask {
        public static Task<CoppiceEntity> create() {
            return TaskTriggerer.task(context -> context.group(context.queryMemoryAbsent(MemoryModuleType.ADMIRING_ITEM))
                    .apply(context, admiringItem -> (world, entity, time) -> {
                // don't run if empty
                if (entity.getMainHandStack().isEmpty()) return false;
                // try to consume if not
                consumeHandItem(entity);
                return true;
            }));
        }
    }
}
