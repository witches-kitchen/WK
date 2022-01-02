package cf.witcheskitchen.common.entities.goals;

import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.PathAwareEntity;

//Todo: Make these take tags.
public class FleeGhostEntity extends FleeEntityGoal {
    public FleeGhostEntity(PathAwareEntity mob, Class fleeFromType, float distance, double slowSpeed, double fastSpeed) {
        super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
        //WKTags.GHOST.contains(mob.getType());
    }
}
