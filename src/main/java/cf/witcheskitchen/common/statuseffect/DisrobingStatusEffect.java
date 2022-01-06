package cf.witcheskitchen.common.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        BlockPos pos = entity.getBlockPos();
        World world = entity.getEntityWorld();
        if (entity.hasStatusEffect(WKStatusEffects.COOLDOWN)) {
            return;
        }
        final Random random = entity.getRandom();
        final int i = random.nextInt(100) + 1;
        if (i <= 50) {
            final EquipmentSlot dice = getRandomArmor(random);
            final ItemStack equippedArmor = entity.getEquippedStack(dice);
            if (equippedArmor.isEmpty()) {
                return;//fast fail, if there is no item in slot.
            } else if (EnchantmentHelper.hasBindingCurse(equippedArmor)) {
                return;//item has binding curse
            } else if (EnchantmentHelper.hasVanishingCurse(equippedArmor)) {
                return;//item should disappear on death.
            }
            if (entity.dropItem(equippedArmor.getItem(), 1) != null) {
                equippedArmor.decrement(1);
            }
        } else {
            final Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(entity);
            if (component.isPresent()) {
                //non empty slots
                final List<Pair<SlotReference, ItemStack>> trinketInventory = component.get().getAllEquipped();
                final int size = trinketInventory.size();
                if (trinketInventory.isEmpty()) {
                    return;//all slots are empty
                    //plus, we can get an exception from random
                }
                final int targetIndex = size == 1 ? 0 : random.nextInt(size) + 1;//if size is 1 there is only 1 equipped slot at index 0
                //else generate a random number 1..= size
                for (int slotIndex = 0; slotIndex < size; slotIndex++) {
                    if (targetIndex == slotIndex) {
                        final Pair<SlotReference, ItemStack> slotData = trinketInventory.get(slotIndex);
                        final int index = slotData.getLeft().index();
                        final Item itemInSlot = slotData.getRight().getItem();
                        entity.dropItem(itemInSlot, 1);
                        slotData.getLeft().inventory().getStack(index).decrement(1);
                        world.playSound(null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.HOSTILE, 1, 1);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(WKStatusEffects.COOLDOWN, 6000, 0));
    }
}
