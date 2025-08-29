package cf.witcheskitchen.common.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import java.util.List;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class DisrobingStatusEffect extends InstantenousMobEffect {

    public DisrobingStatusEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    private static EquipmentSlot getRandomArmor(final RandomSource random) {
        final int i = random.nextInt(4);
        switch (i) {
            case 0 -> {
                return EquipmentSlot.HEAD;
            }
            case 1 -> {
                return EquipmentSlot.CHEST;
            }
            case 2 -> {
                return EquipmentSlot.LEGS;
            }
            case 3 -> {
                return EquipmentSlot.FEET;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        if (entity.hasEffect(WKStatusEffects.COOLDOWN)) {
            return false;
        }
        final RandomSource random = entity.getRandom();
        final int i = random.nextInt(100) + 1;
        if (i <= 50) {
            final EquipmentSlot dice = getRandomArmor(random);
            final ItemStack equippedArmor = entity.getItemBySlot(dice);
            if (equippedArmor.isEmpty()) {
                return false;//fast fail, if there is no item in slot.
            } else if (EnchantmentHelper.has(equippedArmor, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) {
                return false;//item has binding curse
            } else if (EnchantmentHelper.has(equippedArmor, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
                return false;//item should disappear on death.
            }
            if (entity.spawnAtLocation(world, equippedArmor.getItem(), 1) != null) {
                equippedArmor.shrink(1);
            }
        } else {
            AccessoriesCapability capability = entity.accessoriesCapability();
            if (capability != null) {
                //non empty slots
                final List<SlotEntryReference> accessories = capability.getAllEquipped();
                final int size = accessories.size();
                if (accessories.isEmpty()) {
                    return false;//all slots are empty
                    //plus, we can get an exception from random
                }
                final int targetIndex = size == 1 ? 0 : random.nextInt(size) + 1;//if size is 1 there is only 1 equipped slot at index 0
                //else generate a random number 1..= size
                for (int slotIndex = 0; slotIndex < size; slotIndex++) {
                    if (targetIndex == slotIndex) {
                        final SlotEntryReference slotData = accessories.get(slotIndex);
                        final Item itemInSlot = slotData.stack().getItem();
                        entity.spawnAtLocation(world, itemInSlot, 1);
                        ItemStack stack = slotData.stack();
                        stack.shrink(1);
                        slotData.reference().setStack(stack);
                        break;
                    }
                }
            }
        }

        return true;
    }

    // TODO: is this correct?
    @Override
    public void onMobRemoved(ServerLevel world, LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
        entity.addEffect(new MobEffectInstance(WKStatusEffects.COOLDOWN, 6000, 0));
    }
}
