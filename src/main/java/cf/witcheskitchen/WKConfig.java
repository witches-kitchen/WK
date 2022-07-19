package cf.witcheskitchen;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = WK.MODID)
public class WKConfig implements ConfigData {

    @Comment("Enables debug mode. May be useful for devs.")
    public boolean debugMode = false;

    @Comment(value = "general configuration for the mod")
    @ConfigEntry.Category(value = "General")
    public GeneralEntry general = new GeneralEntry();

    @Comment(value = "Client-side only configuration")
    @ConfigEntry.Category(value = "Client")
    public ClientEntry client = new ClientEntry();

    @Comment(value = "Server-side only configuration")
    @ConfigEntry.Category(value = "Server")
    public ServerEntry server = new ServerEntry();

    public static class GeneralEntry {

    }

    public static class ClientEntry {
    }
    public static class ServerEntry {

    }
    public static WKConfig getInstance() {
        return WK.getConfigFile();
    }
}
