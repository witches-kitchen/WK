package cf.witcheskitchen.common.item;

import cf.witcheskitchen.api.util.ItemUtil;
import cf.witcheskitchen.common.registry.WKItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BoneNeedleItem extends Item {
    public BoneNeedleItem(Settings settings) {
        super(settings.maxDamage(16));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        ItemStack offhandStack = player.getOffHandStack();
        if (entity.isAlive() && offhandStack.isOf(Items.GLASS_BOTTLE)) {
            World world = player.world;
            if (hand == Hand.MAIN_HAND) {
                if (!world.isClient()) {
                    if ((!(entity instanceof PlayerEntity)) || successfulTaglocking(player, entity)) {
                        if (entity instanceof MobEntity mob) {
                            mob.setPersistent();
                        }
                        ItemStack taglockStack = writeNbtTaglock(WKItems.TAGLOCK.getDefaultStack(), entity);
                        ItemUtil.addItemToInventoryAndConsume(player, Hand.OFF_HAND, taglockStack);
                        return ActionResult.CONSUME;
                    } else {
                        //if fail
                        stack.damage(1, player, stackUser -> stackUser.sendToolBreakStatus(hand));
                        world.playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.75f, 1);
                    }
                }
            }

        }
        return ActionResult.FAIL;
    }

    private boolean successfulTaglocking(PlayerEntity player, LivingEntity target) {
        double delta = Math.abs((target.headYaw + 90.0f) % 360.0f - (player.headYaw + 90.0f) % 360.0f);
        double chance = player.isInvisible() ? 0.5 : 0.1;
        double lightLevelPenalty = 0.25 * (player.world.getLightLevel(player.getBlockPos()) / 15d);
        if (360.0 - delta % 360.0 < 90 || delta % 360.0 < 90) {
            chance += player.isSneaking() ? 0.45 : 0.25;
        }
        return player.getRandom().nextDouble() < (chance - lightLevelPenalty);
    }

    public ItemStack writeNbtTaglock(ItemStack stack, Entity entity) {
        stack.getOrCreateNbt().putUuid("UUID", entity.getUuid());
        stack.getOrCreateNbt().putString("Name", entity.getEntityName());
        return stack;
    }
}
