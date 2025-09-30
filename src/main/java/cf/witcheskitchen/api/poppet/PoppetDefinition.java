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
     * @param canEffectFireOnDeath Can this fire on the death of either you or a targeted entity?
     * @param isEffectActivatedByHolding Do you need to hold this in the main hand or offhand for this to fire?
     * @param isEffectActivatedByPricking Do you need to prick the poppet with a needle or other sharp object for this to fire?
     * @param canTakeDamageInPlaceOfPlayer Can this take damage in your place?
     * @param isEffectActivatedByDroppingBlock Do you need to drop a gravity-affected block (i.e. sand, anvils, etc) onto the poppet for this to fire?
     * @param isEffectActivatedByFire Must you burn the poppet for the effect to fire?
     * @param isEffectActivatedByExplosion Does the poppet need to be near an explosion to fire?
     * @param isEffectActivatedInAnotherDimension Does the poppet need to be in another dimension for its effects to fire?
     * @param isEffectActivatedByGivingItems Does this effect fire if you give the poppet specific items?
     * @param isEffectActivatedByThrowingInContainer Does this effect fire if you throw it into a container? (I.e. a barrel, a cauldron, etc)
     * @param isEffectActivatedBySleep Does this effect fire if you or the target are asleep?
     * @param isEffectActivatedByDroppingIntoVoid Does this effect fire if you drop the item into the void?
     * @param isEffectActivatedBySound Does this effect fire via exposure to specific sounds?
     * @param isEffectActivatedByAnotherMob Does this effect fire via exposure to specific mobs?
     * @param isEffectActivatedByTime Does this effect need a specific time of day to fire?
     *
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
    boolean isEffectActivatedInAnotherDimension,
    boolean isEffectActivatedByGivingItems,
    boolean isEffectActivatedByThrowingInContainer,
    boolean isEffectActivatedBySleep,
    boolean isEffectActivatedByDroppingIntoVoid,
    boolean isEffectActivatedBySound,
    boolean isEffectActivatedByAnotherMob,
    boolean isEffectActivatedByTime,

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
