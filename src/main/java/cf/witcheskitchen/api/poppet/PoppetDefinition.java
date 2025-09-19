package cf.witcheskitchen.api.poppet;


//Todo: Need help with making some stuff for poppets here. Kind of hard to think of them. Check the Discord for my ideas, even though
//Todo: Not all will make it in the first release.
//Todo: After setting up everything, add java docs
public record PoppetDefinition(

    boolean canEffectBeUsedOnSelf,
    boolean isPoppetEffectPositive,
    boolean isPoppetEffectInstant,
    boolean canAttractWitchHunters
) {


}
