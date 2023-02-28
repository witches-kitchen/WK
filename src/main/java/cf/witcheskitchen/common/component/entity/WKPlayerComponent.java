package cf.witcheskitchen.common.component.entity;

import cf.witcheskitchen.common.component.WKComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class WKPlayerComponent implements AutoSyncedComponent, ServerTickingComponent {
    private final PlayerEntity player;

    private static final EntityAttributeModifier SPEED_LOW, SPEED_MEDIUM, SPEED_HIGH, ARMOR_LOW, ARMOR_MEDIUM, ARMOR_HIGH, ARMOR_TOUGHNESS_LOW, ARMOR_TOUGHNESS_MEDIUM, ARMOR_TOUGHNESS_HIGH, ATTACK_LOW, ATTACK_MEDIUM, ATTACK_HIGH;
    private final Set<EntityAttributeModifier> SPEED_SET = new HashSet<>(List.of(SPEED_LOW, SPEED_MEDIUM, SPEED_HIGH));
    private final Set<EntityAttributeModifier> ARMOR_SET = new HashSet<>(List.of(ARMOR_LOW, ARMOR_MEDIUM, ARMOR_HIGH));
    private final Set<EntityAttributeModifier> ARMOR_THOUGHNESS_SET= new HashSet<>(List.of(ARMOR_TOUGHNESS_LOW, ARMOR_TOUGHNESS_MEDIUM, ARMOR_TOUGHNESS_HIGH));
    private final Set<EntityAttributeModifier> ATTACK_SET = new HashSet<>(List.of(ATTACK_LOW, ATTACK_MEDIUM, ATTACK_HIGH));

    private int magic = 0;

    //Max magic to be stored
    private int magicCap = 0;

    //To keep track of how much magic a player has used, for calculating magic proficiency
    private long magicConsumed = 0;

    //To disable some clutter for players who have the mod but don't want to se a bunch if HUDs and sizzle
    private boolean isWitch = false;

    public WKPlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    public boolean getIsWitch(){
        return isWitch;
    }

    public int getMagic(){
        return magic;
    }

    public int getMagicCap(){
        return magicCap;
    }

    public void setIsWitch(boolean isWitch){
        this.isWitch = isWitch;
        WKComponents.PLAYER_COMPONENT.sync(this);
    }

    public void modifyMagicCap(int amount){
        magicCap = magicCap + amount;
        WKComponents.PLAYER_COMPONENT.sync(this);
    }

    public void modifyMagic(int amount){
        if(magic + amount >= magicCap){
            magic = magicCap;
        } else {
            magic = Math.max(magic + amount, 0);
        }
        WKComponents.PLAYER_COMPONENT.sync(this);
    }

    @Override
    public void serverTick() {
        if(!getIsWitch())return;

        ServerWorld serverWorld = (ServerWorld) player.world;
        if(canRegenMagic() && serverWorld.getTime() % 20 == 0){
            if(magic < magicCap){
                magic++;
                WKComponents.PLAYER_COMPONENT.sync(this);
            }
        }
    }

    private boolean canRegenMagic() {
        return true;
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        magic = nbt.getInt("Magic");
        magicCap = nbt.getInt("MagicCap");
        magicConsumed = nbt.getLong("MagicConsumed");
        isWitch = nbt.getBoolean("IsWitch");
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putInt("Magic", magic);
        nbt.putInt("MagicCap", magicCap);
        nbt.putLong("MagicConsumed", magicConsumed);
        nbt.putBoolean("IsWitch", isWitch);
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

    static {
        SPEED_LOW = new EntityAttributeModifier(UUID.fromString("e4430625-eae6-472d-8d01-096e96bf3545"), "Speed Low", 0.04, EntityAttributeModifier.Operation.ADDITION);
        SPEED_MEDIUM = new EntityAttributeModifier(UUID.fromString("67e11108-66aa-4184-99ab-a4454b90f566"), "Speed Medium", 0.08, EntityAttributeModifier.Operation.ADDITION);
        SPEED_HIGH = new EntityAttributeModifier(UUID.fromString("036de4ca-388a-4925-a85b-96a2f8e54c2a"), "Speed High", 0.16, EntityAttributeModifier.Operation.ADDITION);

        ARMOR_LOW = new EntityAttributeModifier(UUID.fromString("de1a15e9-a551-4780-a45e-70052d5a29c6"), "Armor Low", 4, EntityAttributeModifier.Operation.ADDITION);
        ARMOR_MEDIUM = new EntityAttributeModifier(UUID.fromString("70420eb5-0238-4eed-977c-b890f5d611c0"), "Armor Medium", 8, EntityAttributeModifier.Operation.ADDITION);
        ARMOR_HIGH = new EntityAttributeModifier(UUID.fromString("809e002c-622f-4cf1-94b4-361da12d2985"), "Armor High", 16, EntityAttributeModifier.Operation.ADDITION);

        ARMOR_TOUGHNESS_LOW = new EntityAttributeModifier(UUID.fromString("cbd34291-f853-4e69-a12a-1f83a2809ad4"), "Armor Toughness Low", 4, EntityAttributeModifier.Operation.ADDITION);
        ARMOR_TOUGHNESS_MEDIUM = new EntityAttributeModifier(UUID.fromString("c8fbbb9e-a0fc-4e73-8f9e-eedd23d394e4"), "Armor Toughness Medium", 8, EntityAttributeModifier.Operation.ADDITION);
        ARMOR_TOUGHNESS_HIGH = new EntityAttributeModifier(UUID.fromString("1de91e57-ebb1-4098-9131-08368e1327f6"), "Armor Toughness High", 16, EntityAttributeModifier.Operation.ADDITION);

        ATTACK_LOW = new EntityAttributeModifier(UUID.fromString("cbd34291-f853-4e69-a12a-1f83a2809ad4"), "Attack Low", 2, EntityAttributeModifier.Operation.ADDITION);
        ATTACK_MEDIUM = new EntityAttributeModifier(UUID.fromString("c8fbbb9e-a0fc-4e73-8f9e-eedd23d394e4"), "Attack Medium", 4, EntityAttributeModifier.Operation.ADDITION);
        ATTACK_HIGH = new EntityAttributeModifier(UUID.fromString("1de91e57-ebb1-4098-9131-08368e1327f6"), "Attack High", 8, EntityAttributeModifier.Operation.ADDITION);

    }
}
