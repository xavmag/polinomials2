package ru.build23.polinomials.artemis

import com.artemis.BaseComponentMapper
import com.artemis.Component
import com.artemis.World


/**
 * Creates a [BaseComponentMapper] for the specified [Component] type.
 *
 * Provides `O(1)` retrieval of [Component]s for an [com.artemis.Entity].
 *
 * @param T the [Component] type to create a [BaseComponentMapper] for.
 * @return a [BaseComponentMapper] matching the selected component type.
 * @see BaseComponentMapper
 * @see Component
 */
inline fun <reified T : Component> mapperFor(world: World): BaseComponentMapper<T> = BaseComponentMapper.getFor(T::class.java, world)