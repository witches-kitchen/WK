package cf.witcheskitchen.common.registry;

import net.minecraft.entity.damage.DamageSource;

public interface WKDamageSources {

    DamageSource ON_OVEN = new WKFireDamageSource("on_oven");
    DamageSource HOLY = new WKHolyDamageSource("holy");
    DamageSource HUGGING_BLACKTHORN = new BlackthornDamageSource("hugging_blackthorn");
    DamageSource PUNCHING_BLACKTHORN = new BlackthornDamageSource("punching_blackthorn");

    // Used to control in which order static constructors are called
    static void init() {

    }

    class WKFireDamageSource extends DamageSource {

        protected WKFireDamageSource(String name) {
            super(name);
            this.setBypassesArmor();
            this.setFire();
        }
    }

    class WKHolyDamageSource extends DamageSource {

        protected WKHolyDamageSource(String name) {
            super(name);
            this.setBypassesArmor();
            this.setFire();
            this.isMagic();
            this.setUnblockable();
        }
    }

    class BlackthornDamageSource extends DamageSource {
        public BlackthornDamageSource(String name) {
            super(name);
        }
    }
}
