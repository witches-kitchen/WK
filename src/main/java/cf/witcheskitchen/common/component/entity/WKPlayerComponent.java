package cf.witcheskitchen.common.component.entity;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.component.WKEntityComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WKPlayerComponent implements AutoSyncedComponent, ServerTickingComponent {
    private static final AttributeModifier SPEED_LOW, SPEED_MEDIUM, SPEED_HIGH, ARMOR_LOW, ARMOR_MEDIUM, ARMOR_HIGH, ARMOR_TOUGHNESS_LOW, ARMOR_TOUGHNESS_MEDIUM, ARMOR_TOUGHNESS_HIGH, ATTACK_LOW, ATTACK_MEDIUM, ATTACK_HIGH;

    static {
        SPEED_LOW = new AttributeModifier(WitchesKitchen.id("speed_low"), 0.04, AttributeModifier.Operation.ADD_VALUE);
        SPEED_MEDIUM = new AttributeModifier(WitchesKitchen.id("speed_medium"), 0.08, AttributeModifier.Operation.ADD_VALUE);
        SPEED_HIGH = new AttributeModifier(WitchesKitchen.id("speed_high"), 0.16, AttributeModifier.Operation.ADD_VALUE);

        ARMOR_LOW = new AttributeModifier(WitchesKitchen.id("armor_low"), 4, AttributeModifier.Operation.ADD_VALUE);
        ARMOR_MEDIUM = new AttributeModifier(WitchesKitchen.id("armor_medium"), 8, AttributeModifier.Operation.ADD_VALUE);
        ARMOR_HIGH = new AttributeModifier(WitchesKitchen.id("armor_high"), 16, AttributeModifier.Operation.ADD_VALUE);

        ARMOR_TOUGHNESS_LOW = new AttributeModifier(WitchesKitchen.id("armor_toughness_low"), 4, AttributeModifier.Operation.ADD_VALUE);
        ARMOR_TOUGHNESS_MEDIUM = new AttributeModifier(WitchesKitchen.id("armor_toughness_medium"), 8, AttributeModifier.Operation.ADD_VALUE);
        ARMOR_TOUGHNESS_HIGH = new AttributeModifier(WitchesKitchen.id("armor_toughness_high"), 16, AttributeModifier.Operation.ADD_VALUE);

        ATTACK_LOW = new AttributeModifier(WitchesKitchen.id("attack_low"), 2, AttributeModifier.Operation.ADD_VALUE);
        ATTACK_MEDIUM = new AttributeModifier(WitchesKitchen.id("attack_medium"), 4, AttributeModifier.Operation.ADD_VALUE);
        ATTACK_HIGH = new AttributeModifier(WitchesKitchen.id("attack_high"), 8, AttributeModifier.Operation.ADD_VALUE);

    }

    private final Player player;
    private final Set<AttributeModifier> SPEED_SET = new HashSet<>(List.of(SPEED_LOW, SPEED_MEDIUM, SPEED_HIGH));
    private final Set<AttributeModifier> ARMOR_SET = new HashSet<>(List.of(ARMOR_LOW, ARMOR_MEDIUM, ARMOR_HIGH));
    private final Set<AttributeModifier> ARMOR_THOUGHNESS_SET = new HashSet<>(List.of(ARMOR_TOUGHNESS_LOW, ARMOR_TOUGHNESS_MEDIUM, ARMOR_TOUGHNESS_HIGH));
    private final Set<AttributeModifier> ATTACK_SET = new HashSet<>(List.of(ATTACK_LOW, ATTACK_MEDIUM, ATTACK_HIGH));
    private int magic = 0;
    //Max magic to be stored
    private int magicCap = 0;
    //To keep track of how much magic a player has used, for calculating magic proficiency
    private long magicConsumed = 0;
    //To disable some clutter for players who have the mod but don't want to se a bunch if HUDs and sizzle
    private boolean isWitch = false;

    public WKPlayerComponent(Player player) {
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

        ServerLevel serverWorld = (ServerLevel) player.level();
        if (canRegenMagic() && serverWorld.getGameTime() % 20 == 0) {
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
    public void readData(ValueInput data) {
        magic = data.getIntOr("Magic", 0);
        magicCap = data.getIntOr("MagicCap", 0);
        magicConsumed = data.getLongOr("MagicConsumed", 0);
        isWitch = data.getBooleanOr("IsWitch", false);
    }

    @Override
    public void writeData(ValueOutput data) {
        data.putInt("Magic", magic);
        data.putInt("MagicCap", magicCap);
        data.putLong("MagicConsumed", magicConsumed);
        data.putBoolean("IsWitch", isWitch);
    }

    public void addOrReplaceAttribute(AttributeModifier attributeMod) {
        AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null && SPEED_SET.contains(attributeMod)) {
            for (AttributeModifier mod : SPEED_SET) {
                if (speed.hasModifier(mod.id())) {
                    speed.removeModifier(mod);
                }
            }
            speed.addPermanentModifier(attributeMod);
        }

        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
        if (armor != null && ARMOR_SET.contains(attributeMod)) {
            for (AttributeModifier mod : ARMOR_SET) {
                if (armor.hasModifier(mod.id())) {
                    armor.removeModifier(mod);
                }
            }
            armor.addPermanentModifier(attributeMod);
        }

        AttributeInstance armorToughness = player.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (armorToughness != null && ARMOR_THOUGHNESS_SET.contains(attributeMod)) {
            for (AttributeModifier mod : ARMOR_THOUGHNESS_SET) {
                if (armorToughness.hasModifier(mod.id())) {
                    armorToughness.removeModifier(mod);
                }
            }
            armorToughness.addPermanentModifier(attributeMod);
        }

        AttributeInstance attack = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null && ATTACK_SET.contains(attributeMod)) {
            for (AttributeModifier mod : ATTACK_SET) {
                if (attack.hasModifier(mod.id())) {
                    attack.removeModifier(mod);
                }
            }
            attack.addPermanentModifier(attributeMod);
        }
    }
}
