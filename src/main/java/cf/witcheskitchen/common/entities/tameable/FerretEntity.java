package cf.witcheskitchen.common.entities.tameable;

import cf.witcheskitchen.api.WKTameableEntity;
import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class FerretEntity extends WKTameableEntity {

    public static final Set<Item> TAMING_INGREDIENTS;

    static {
        TAMING_INGREDIENTS = Sets.newHashSet(Items.RABBIT, Items.COOKED_RABBIT, Items.CHICKEN, Items.COOKED_CHICKEN, Items.EGG, Items.RABBIT_FOOT);
    }

    public FerretEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int getVariants() {
        return 0;
    }

    @Nullable
    @Override
    public UUID getOwnerUuid() {
        return null;
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
