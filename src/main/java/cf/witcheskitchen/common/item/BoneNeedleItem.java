package cf.witcheskitchen.common.item;

import cf.witcheskitchen.api.util.ItemUtil;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.component.item.TaglockEntityData;
import cf.witcheskitchen.common.registry.WKItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BoneNeedleItem extends Item {
    public BoneNeedleItem(Properties settings) {
        super(settings.durability(16));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return super.useOn(context);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        ItemStack offhandStack = player.getOffhandItem();
        if (entity.isAlive() && offhandStack.is(Items.GLASS_BOTTLE)) {
            Level world = player.level();
            if (hand == InteractionHand.MAIN_HAND) {
                if (!world.isClientSide()) {
                    if ((!(entity instanceof Player)) || successfulTaglocking(player, entity)) {
                        if (entity instanceof Mob mob) {
                            mob.setPersistenceRequired();
                        }
                        ItemStack taglockStack = writeNbtTaglock(WKItems.TAGLOCK.getDefaultInstance(), entity);
                        ItemUtil.addItemToInventoryAndConsume(player, InteractionHand.OFF_HAND, taglockStack);
                        return InteractionResult.CONSUME;
                    } else {
                        //if fail
                        stack.hurtAndBreak(1, (ServerLevel) player.level(), (ServerPlayer) player, item -> player.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
                        world.playSound(null, entity.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.75f, 1);
                    }
                }
            }

        }
        return InteractionResult.FAIL;
    }

    private boolean successfulTaglocking(Player player, LivingEntity target) {
        double delta = Math.abs((target.yHeadRot + 90.0f) % 360.0f - (player.yHeadRot + 90.0f) % 360.0f);
        double chance = player.isInvisible() ? 0.5 : 0.1;
        double lightLevelPenalty = 0.25 * (player.level().getMaxLocalRawBrightness(player.blockPosition()) / 15d);
        if (360.0 - delta % 360.0 < 90 || delta % 360.0 < 90) {
            chance += player.isShiftKeyDown() ? 0.45 : 0.25;
        }
        return player.getRandom().nextDouble() < (chance - lightLevelPenalty);
    }

    public ItemStack writeNbtTaglock(ItemStack stack, Entity entity) {
        stack.set(WKComponents.TAGLOCK, new TaglockEntityData(entity.getUUID(), entity.getScoreboardName()));
        return stack;
    }
}
