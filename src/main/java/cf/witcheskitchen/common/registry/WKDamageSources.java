package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import net.minecraft.entity.damage.DamageSource;

public class WKDamageSources {

    public static final DamageSource ON_OVEN = new WKFireDamageSource("on_oven");
    public static final DamageSource HOLY = new WKHolyDamageSource("holy");
    public static final DamageSource HUGGING_BLACKTHORN = new BlackthornDamageSource("hugging_blackthorn");
    public static final DamageSource PUNCHING_BLACKTHORN = new BlackthornDamageSource("punching_blackthorn");

    static {
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Damage Sources: Successfully Loaded");
        }
    }

    // Used to control in which order static constructors are called
    public static void register() {

    }

    private static class WKFireDamageSource extends DamageSource {

        protected WKFireDamageSource(String name) {
            super(name);
            this.setBypassesArmor();
            this.setFire();
        }
    }

    private static class WKHolyDamageSource extends DamageSource {

        protected WKHolyDamageSource(String name) {
            super(name);
            this.setBypassesArmor();
            this.setFire();
            this.isMagic();
            this.setUnblockable();
        }
    }

    static class BlackthornDamageSource extends DamageSource {
        public BlackthornDamageSource(String name) {
            super(name);
        }
    }
}
