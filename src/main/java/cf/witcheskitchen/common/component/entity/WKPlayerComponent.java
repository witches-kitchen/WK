package cf.witcheskitchen.common.component.entity;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.component.WKEntityComponents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WKPlayerComponent implements AutoSyncedComponent, ServerTickingComponent {
    private static final EntityAttributeModifier SPEED_LOW, SPEED_MEDIUM, SPEED_HIGH, ARMOR_LOW, ARMOR_MEDIUM, ARMOR_HIGH, ARMOR_TOUGHNESS_LOW, ARMOR_TOUGHNESS_MEDIUM, ARMOR_TOUGHNESS_HIGH, ATTACK_LOW, ATTACK_MEDIUM, ATTACK_HIGH;

    static {
        SPEED_LOW = new EntityAttributeModifier(WitchesKitchen.id("speed_low"), 0.04, EntityAttributeModifier.Operation.ADD_VALUE);
        SPEED_MEDIUM = new EntityAttributeModifier(WitchesKitchen.id("speed_medium"), 0.08, EntityAttributeModifier.Operation.ADD_VALUE);
        SPEED_HIGH = new EntityAttributeModifier(WitchesKitchen.id("speed_high"), 0.16, EntityAttributeModifier.Operation.ADD_VALUE);

        ARMOR_LOW = new EntityAttributeModifier(WitchesKitchen.id("armor_low"), 4, EntityAttributeModifier.Operation.ADD_VALUE);
        ARMOR_MEDIUM = new EntityAttributeModifier(WitchesKitchen.id("armor_medium"), 8, EntityAttributeModifier.Operation.ADD_VALUE);
        ARMOR_HIGH = new EntityAttributeModifier(WitchesKitchen.id("armor_high"), 16, EntityAttributeModifier.Operation.ADD_VALUE);

        ARMOR_TOUGHNESS_LOW = new EntityAttributeModifier(WitchesKitchen.id("armor_toughness_low"), 4, EntityAttributeModifier.Operation.ADD_VALUE);
        ARMOR_TOUGHNESS_MEDIUM = new EntityAttributeModifier(WitchesKitchen.id("armor_toughness_medium"), 8, EntityAttributeModifier.Operation.ADD_VALUE);
        ARMOR_TOUGHNESS_HIGH = new EntityAttributeModifier(WitchesKitchen.id("armor_toughness_high"), 16, EntityAttributeModifier.Operation.ADD_VALUE);

        ATTACK_LOW = new EntityAttributeModifier(WitchesKitchen.id("attack_low"), 2, EntityAttributeModifier.Operation.ADD_VALUE);
        ATTACK_MEDIUM = new EntityAttributeModifier(WitchesKitchen.id("attack_medium"), 4, EntityAttributeModifier.Operation.ADD_VALUE);
        ATTACK_HIGH = new EntityAttributeModifier(WitchesKitchen.id("attack_high"), 8, EntityAttributeModifier.Operation.ADD_VALUE);

    }

    private final PlayerEntity player;
    private final Set<EntityAttributeModifier> SPEED_SET = new HashSet<>(List.of(SPEED_LOW, SPEED_MEDIUM, SPEED_HIGH));
    private final Set<EntityAttributeModifier> ARMOR_SET = new HashSet<>(List.of(ARMOR_LOW, ARMOR_MEDIUM, ARMOR_HIGH));
    private final Set<EntityAttributeModifier> ARMOR_THOUGHNESS_SET = new HashSet<>(List.of(ARMOR_TOUGHNESS_LOW, ARMOR_TOUGHNESS_MEDIUM, ARMOR_TOUGHNESS_HIGH));
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

    public boolean isWitch() {
        return isWitch;
    }

    public int getMagic() {
        return magic;
    }

    public int getMagicCap() {
        return magicCap;
    }

    public void isWitch(boolean isWitch) {
        this.isWitch = isWitch;
        WKEntityComponents.PLAYER_COMPONENT.sync(this);
    }

    public void modifyMagicCap(int amount) {
        magicCap = magicCap + amount;
        WKEntityComponents.PLAYER_COMPONENT.sync(this);
    }

    public void modifyMagic(int amount) {
        if (magic + amount >= magicCap) {
            magic = magicCap;
        } else {
            magic = Math.max(magic + amount, 0);
        }
        WKEntityComponents.PLAYER_COMPONENT.sync(this);
    }

    @Override
    public void serverTick() {
        if (!isWitch()) return;

        ServerWorld serverWorld = (ServerWorld) player.getWorld();
        if (canRegenMagic() && serverWorld.getTime() % 20 == 0) {
            if (magic < magicCap) {
                magic++;
                WKEntityComponents.PLAYER_COMPONENT.sync(this);
            }
        }
    }

    private boolean canRegenMagic() {
        return true;
    }

    @Override
    public void readData(ReadView data) {
        magic = data.getInt("Magic", 0);
        magicCap = data.getInt("MagicCap", 0);
        magicConsumed = data.getLong("MagicConsumed", 0);
        isWitch = data.getBoolean("IsWitch", false);
    }

    @Override
    public void writeData(WriteView data) {
        data.putInt("Magic", magic);
        data.putInt("MagicCap", magicCap);
        data.putLong("MagicConsumed", magicConsumed);
        data.putBoolean("IsWitch", isWitch);
    }

    public void addOrReplaceAttribute(EntityAttributeModifier attributeMod) {
        EntityAttributeInstance speed = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        if (speed != null && SPEED_SET.contains(attributeMod)) {
            for (EntityAttributeModifier mod : SPEED_SET) {
                if (speed.hasModifier(mod.id())) {
                    speed.removeModifier(mod);
                }
            }
            speed.addPersistentModifier(attributeMod);
        }

        EntityAttributeInstance armor = player.getAttributeInstance(EntityAttributes.ARMOR);
        if (armor != null && ARMOR_SET.contains(attributeMod)) {
            for (EntityAttributeModifier mod : ARMOR_SET) {
                if (armor.hasModifier(mod.id())) {
                    armor.removeModifier(mod);
                }
            }
            armor.addPersistentModifier(attributeMod);
        }

        EntityAttributeInstance armorToughness = player.getAttributeInstance(EntityAttributes.ARMOR_TOUGHNESS);
        if (armorToughness != null && ARMOR_THOUGHNESS_SET.contains(attributeMod)) {
            for (EntityAttributeModifier mod : ARMOR_THOUGHNESS_SET) {
                if (armorToughness.hasModifier(mod.id())) {
                    armorToughness.removeModifier(mod);
                }
            }
            armorToughness.addPersistentModifier(attributeMod);
        }

        EntityAttributeInstance attack = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
        if (attack != null && ATTACK_SET.contains(attributeMod)) {
            for (EntityAttributeModifier mod : ATTACK_SET) {
                if (attack.hasModifier(mod.id())) {
                    attack.removeModifier(mod);
                }
            }
            attack.addPersistentModifier(attributeMod);
        }
    }
}
