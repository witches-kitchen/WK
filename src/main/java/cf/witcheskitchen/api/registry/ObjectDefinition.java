package cf.witcheskitchen.api.registry;

import net.minecraft.resources.ResourceLocation;


/**
 * Utility for registering objects.
 *
 * @param id     {@link ResourceLocation} .
 * @param object Object to register.
 * @param <T>    Object.
 */
public record ObjectDefinition<T>(ResourceLocation id, T object) {

}
