package cf.witcheskitchen.api.registry;

import net.minecraft.util.Identifier;


/**
 * Utility for registering objects.
 *
 * @param id     {@link Identifier} .
 * @param object Object to register.
 * @param <T>    Object.
 */
public record ObjectDefinition<T>(Identifier id, T object) {

}
