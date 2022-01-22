package cf.witcheskitchen.common.registry;

import net.minecraft.entity.damage.DamageSource;

public class WKDamageSources {

    public static final DamageSource ON_OVEN = new WKFireDamageSource("on_oven");
    public static final DamageSource HOLY = new WKHolyDamageSource("holy");

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
    }
