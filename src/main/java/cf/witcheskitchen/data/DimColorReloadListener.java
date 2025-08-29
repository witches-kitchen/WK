package cf.witcheskitchen.data;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class DimColorReloadListener extends SimpleJsonResourceReloadListener<Pair<String, Integer>> {
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
        super(CODEC, FileToIdConverter.json("dimension_color"));
    }

    @Override
    protected void apply(Map<ResourceLocation, Pair<String, Integer>> prepared, ResourceManager manager, ProfilerFiller profiler) {
        COLOR_DATA.clear();
        COLOR_DATA.addAll(prepared.values());
    }
}
