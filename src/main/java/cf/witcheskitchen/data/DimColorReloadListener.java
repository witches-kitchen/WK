package cf.witcheskitchen.data;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DimColorReloadListener extends JsonDataLoader<Pair<String, Integer>> {
    public static final Codec<Pair<String, Integer>> CODEC = Codec.pair(Codec.STRING,
        Codec.withAlternative(Codec.INT, Codec.STRING.comapFlatMap(s -> {
            try {
                return DataResult.success(Integer.decode(s));
            } catch (NumberFormatException e) {
                return DataResult.error(e::getMessage);
            }
        }, Object::toString)));
    public static final Set<Pair<String, Integer>> COLOR_DATA = new HashSet<>();

    public DimColorReloadListener() {
        super(CODEC, ResourceFinder.json("dimension_color"));
    }

    @Override
    protected void apply(Map<Identifier, Pair<String, Integer>> prepared, ResourceManager manager, Profiler profiler) {
        COLOR_DATA.clear();
        COLOR_DATA.addAll(prepared.values());
    }
}
