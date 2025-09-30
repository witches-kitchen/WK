package cf.witcheskitchen.api.poppet;


//Todo: Need help with making some stuff for poppets here. Kind of hard to think of them. Check the Discord for my ideas, even though
//Todo: Not all will make it in the first release.
//Todo: After setting up everything, add java docs
public record PoppetDefinition(

    boolean canEffectBeUsedOnSelf,
    boolean isPoppetEffectPositive,
    boolean isPoppetEffectInstant,
    boolean canAttractWitchHunters,
    boolean isEffectActivatedByHitting,
    boolean isEffectActivatedByDropping,
    boolean canEffectFireOnDeath,
    boolean isEffectActivatedByHolding,
    boolean isEffectActivatedByPricking,
    boolean canTakeDamageInPlaceOfPlayer,
    boolean isEffectActivatedByDroppingBlock,
    boolean isEffectActivatedByFire,
    boolean isEffectActivatedByExplosion,
    boolean isEffectActivatedByHoldingInOffhand,
    boolean isEffectActivatedInAnotherDimension,
    boolean isEffectActivatedByGivingItems,
    boolean isEffectActivatedByThrowingInContainer,
    boolean isEffectActivatedByYouSleeping,
    boolean isEffectActivatedByVictimSleeping,
    boolean isEffectActivatedByDroppingIntoVoid,
    boolean isEffectActivatedBySound,
    boolean isEffectActivatedByAnotherMob,
    boolean isEffectActivatedByMoonlight,
    boolean isEffectActivatedBySunlight,

    /**
    * @param taglockVictimID This is the UUID of the player or mob targeted.
    */

    int taglockVictimID,
    int effectDurationMinimum,
    int effectDurationMaximum
) {


}
