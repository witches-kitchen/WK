package cf.witcheskitchen;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = WK.MODID)
public class WKConfig implements ConfigData {

    @Comment("Enables debug mode. May be useful for devs.")
    public boolean debugMode = false;

    public static WKConfig get() {
        return AutoConfig.getConfigHolder(WKConfig.class).getConfig();
    }
}
