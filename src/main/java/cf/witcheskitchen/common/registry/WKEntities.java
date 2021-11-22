package cf.witcheskitchen.common.registry;



public class WKEntities {
    public static final EntityType<CuSith> CUSITH = Registry.register(
        Registry.ENTITY_TYPE,
        new Identifier("witcheskitchen","cusith"),
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CuSith::new).dimensions(EntityDimensions.fixed(0.775f,0.75f)).build()
    );
}
