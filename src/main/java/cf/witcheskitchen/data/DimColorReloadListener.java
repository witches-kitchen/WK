package cf.witcheskitchen.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DimColorReloadListener extends JsonDataLoader {
    public static final Set<Pair<String, Integer>> COLOR_DATA = new HashSet<>();

    private static final Gson GSON = (new GsonBuilder()).create();

    public DimColorReloadListener() {
        super(GSON, "dimension_color");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        COLOR_DATA.clear();
        for (JsonElement entry : prepared.values()) {
            JsonObject object = entry.getAsJsonObject();
            String name = object.getAsJsonPrimitive("name").getAsString();
            String color = object.getAsJsonPrimitive("color").getAsString();
            int hex = Integer.decode(color);
            COLOR_DATA.add(Pair.of(name, hex));
        }
    }
}
