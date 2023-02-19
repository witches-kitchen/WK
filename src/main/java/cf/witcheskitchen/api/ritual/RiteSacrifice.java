package cf.witcheskitchen.api.ritual;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface RiteSacrifice extends Rite{
    default List<Entity> getSacrifices(){
        return List.of();
    }
}
