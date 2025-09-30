package cf.witcheskitchen.api.poppet;


//Todo: Need help with making some stuff for poppets here. Kind of hard to think of them. Check the Discord for my ideas, even though
//Todo: Not all will make it in the first release.
//Todo: After setting up everything, add java docs
public record PoppetDefinition(

    /**
     *
     * @param canEffectBeUsedOnSelf Can you use this on yourself?
     * @param isPoppetEffectPositive Does this poppet has a positive effect?
     * @param isPoppetEffectInstant Does this poppet effect fire instantly?
     * @param canAttractWitchHunters Can this effect attract witch hunters to you?
     * @param isEffectActivatedByHitting Do you need to punch the poppet entity to activate the effect?
     * @param isEffectActivatedByDropping Do you need to drop the item to activate the effect?
     */

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
     *
     * @param taglockVictimID This is the UUID of the player or mob targeted.
     * @param effectDurationMinimum How long does a poppet effect last at minimum? Measured in ticks. One tick is 50 milliseconds. 20 ticks are in a second.
     * @param effectDurationMaximum How long does a poppet effect last at maximum? Measured in ticks. One tick is 50 milliseconds. 20 ticks are in a second.
     */

    int taglockVictimID,
    int effectDurationMinimum,
    int effectDurationMaximum
) {


}
