package cf.witcheskitchen.mixin.statuseffect;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(Mob.class)
public abstract class ArthropodEntityMixin extends LivingEntity {

    @Shadow
    @Final
    protected GoalSelector goalSelector;

    protected ArthropodEntityMixin(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void initFleeGoal(CallbackInfo ci) {
        Mob mob = Mob.class.cast(this);

        List<Class<? extends Mob>> fleeEntities = Arrays.asList(Spider.class, Silverfish.class, Endermite.class, Bee.class);

        for (Class<? extends Mob> entityClass : fleeEntities) {
            if (entityClass.isInstance(mob)) {
                this.goalSelector.addGoal(3, new AvoidEntityGoal<>((PathfinderMob) mob, Player.class, 12.0F, 1.0D, 1.6D));
                break;
            }
        }
    }
}
