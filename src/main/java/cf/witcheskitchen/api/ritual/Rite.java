package cf.witcheskitchen.api.ritual;

import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.Box;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Set;

public interface Rite {
    String getId();

    Set<RitualCircle> getCircles();

    List<Ingredient> getIngredients();

    Box getBoundingBox();
}
