package cf.witcheskitchen.common.component.entity;

import com.mojang.datafixers.util.Pair;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class WKPlayerComponent implements AutoSyncedComponent {
    private final PlayerEntity player;

    private static final EntityAttributeModifier SPEED_LOW = new EntityAttributeModifier(UUID.fromString("e4430625-eae6-472d-8d01-096e96bf3545"), "Speed Low", 0.04, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier SPEED_MEDIUM = new EntityAttributeModifier(UUID.fromString("67e11108-66aa-4184-99ab-a4454b90f566"), "Speed Medium", 0.08, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier SPEED_HIGH = new EntityAttributeModifier(UUID.fromString("036de4ca-388a-4925-a85b-96a2f8e54c2a"), "Speed High", 0.16, EntityAttributeModifier.Operation.ADDITION);

    private static final EntityAttributeModifier ARMOR_LOW = new EntityAttributeModifier(UUID.fromString("de1a15e9-a551-4780-a45e-70052d5a29c6"), "Armor Low", 4, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier ARMOR_MEDIUM = new EntityAttributeModifier(UUID.fromString("70420eb5-0238-4eed-977c-b890f5d611c0"), "Armor Medium", 8, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier ARMOR_HIGH = new EntityAttributeModifier(UUID.fromString("809e002c-622f-4cf1-94b4-361da12d2985"), "Armor High", 16, EntityAttributeModifier.Operation.ADDITION);

    private static final EntityAttributeModifier ARMOR_TOUGHNESS_LOW = new EntityAttributeModifier(UUID.fromString("cbd34291-f853-4e69-a12a-1f83a2809ad4"), "Armor Toughness Low", 4, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier ARMOR_TOUGHNESS_MEDIUM = new EntityAttributeModifier(UUID.fromString("c8fbbb9e-a0fc-4e73-8f9e-eedd23d394e4"), "Armor Toughness Medium", 8, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier ARMOR_TOUGHNESS_HIGH = new EntityAttributeModifier(UUID.fromString("1de91e57-ebb1-4098-9131-08368e1327f6"), "Armor Toughness High", 16, EntityAttributeModifier.Operation.ADDITION);

    private static final EntityAttributeModifier ATTACK_LOW = new EntityAttributeModifier(UUID.fromString("cbd34291-f853-4e69-a12a-1f83a2809ad4"), "Attack Low", 2, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier ATTACK_MEDIUM = new EntityAttributeModifier(UUID.fromString("c8fbbb9e-a0fc-4e73-8f9e-eedd23d394e4"), "Attack Medium", 4, EntityAttributeModifier.Operation.ADDITION);
    private static final EntityAttributeModifier ATTACK_HIGH = new EntityAttributeModifier(UUID.fromString("1de91e57-ebb1-4098-9131-08368e1327f6"), "Attack High", 8, EntityAttributeModifier.Operation.ADDITION);

    private final Set<EntityAttributeModifier> SPEED_SET = new HashSet<>(List.of(SPEED_LOW, SPEED_MEDIUM, SPEED_HIGH));
    private final Set<EntityAttributeModifier> ARMOR_SET = new HashSet<>(List.of(ARMOR_LOW, ARMOR_MEDIUM, ARMOR_HIGH));
    private final Set<EntityAttributeModifier> ARMOR_THOUGHNESS_SET= new HashSet<>(List.of(ARMOR_TOUGHNESS_LOW, ARMOR_TOUGHNESS_MEDIUM, ARMOR_TOUGHNESS_HIGH));
    private final Set<EntityAttributeModifier> ATTACK_SET = new HashSet<>(List.of(ATTACK_LOW, ATTACK_MEDIUM, ATTACK_HIGH));

    public WKPlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {

    }

    @Override
    public void writeToNbt(NbtCompound nbt) {

    }

    public void addOrReplaceAttribute(EntityAttributeModifier attributeMod){
        EntityAttributeInstance speed = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if(speed != null && SPEED_SET.contains(attributeMod)){
            for(EntityAttributeModifier mod : SPEED_SET){
                if (speed.hasModifier(mod)) {
                    speed.removeModifier(mod);
                }
            }
            speed.addPersistentModifier(attributeMod);
        }

        EntityAttributeInstance armor = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR);
        if(armor != null && ARMOR_SET.contains(attributeMod)){
            for(EntityAttributeModifier mod : ARMOR_SET){
                if (armor.hasModifier(mod)) {
                    armor.removeModifier(mod);
                }
            }
            armor.addPersistentModifier(attributeMod);
        }

        EntityAttributeInstance armorToughness = player.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
        if(armorToughness != null && ARMOR_THOUGHNESS_SET.contains(attributeMod)){
            for(EntityAttributeModifier mod : ARMOR_THOUGHNESS_SET){
                if (armorToughness.hasModifier(mod)) {
                    armorToughness.removeModifier(mod);
                }
            }
            armorToughness.addPersistentModifier(attributeMod);
        }

        EntityAttributeInstance attack = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if(attack != null && ATTACK_SET.contains(attributeMod)){
            for(EntityAttributeModifier mod : ATTACK_SET){
                if (attack.hasModifier(mod)) {
                    attack.removeModifier(mod);
                }
            }
            attack.addPersistentModifier(attributeMod);
        }
    }
}
