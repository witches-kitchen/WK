package cf.witcheskitchen.common.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class DisrobingStatusEffect extends InstantStatusEffect {

    public DisrobingStatusEffect(StatusEffectCategory type, int color) {
        super(type, color);
    }

    private static EquipmentSlot getRandomArmor(final Random random) {
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
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (entity.hasStatusEffect(WKStatusEffects.COOLDOWN)) {
            return false;
        }
        final Random random = entity.getRandom();
        final int i = random.nextInt(100) + 1;
        if (i <= 50) {
            final EquipmentSlot dice = getRandomArmor(random);
            final ItemStack equippedArmor = entity.getEquippedStack(dice);
            if (equippedArmor.isEmpty()) {
                return false;//fast fail, if there is no item in slot.
            } else if (EnchantmentHelper.hasAnyEnchantmentsWith(equippedArmor, EnchantmentEffectComponentTypes.PREVENT_ARMOR_CHANGE)) {
                return false;//item has binding curse
            } else if (EnchantmentHelper.hasAnyEnchantmentsWith(equippedArmor, EnchantmentEffectComponentTypes.PREVENT_EQUIPMENT_DROP)) {
                return false;//item should disappear on death.
            }
            if (entity.dropItem(world, equippedArmor.getItem(), 1) != null) {
                equippedArmor.decrement(1);
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
                        entity.dropItem(world, itemInSlot, 1);
                        ItemStack stack = slotData.stack();
                        stack.decrement(1);
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
    public void onEntityRemoval(ServerWorld world, LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
        entity.addStatusEffect(new StatusEffectInstance(WKStatusEffects.COOLDOWN, 6000, 0));
    }
}
