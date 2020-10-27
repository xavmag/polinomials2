package ru.build23.polinomials.artemis

import com.artemis.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

val Entity.edit: EntityEdit
  inline get() = edit()

/**
 * Gets the specified [Component] from the [Entity] with a [ComponentMapper].
 *
 * @param T the [Component] type to get
 * @param mapper the [ComponentMapper] to retrieve the [Component] with
 * @return the specified [Component]. Otherwise `null` if the [Entity] does not have it.
 * @see ComponentMapper.get
 * @see mapperFor
 */
operator fun <T : Component> Entity.get(mapper: ComponentMapper<T>): T = mapper.get(this)

/**
 * Gets the specified [Component] from the [Entity] with a [ComponentMapper].
 *
 * @param T the [Component] type to get
 * @param mapper the [ComponentMapper] to retrieve the [Component] with
 * @return the specified [Component]. Otherwise `null` if the [Entity] does not have it.
 * @see ComponentMapper.get
 * @see mapperFor
 */
inline operator fun <T : Component> Int.get(mapper: ComponentMapper<T>): T = mapper.get(this)

/**
 * Gets the specified [Component] from the [Entity].
 *
 * Note that this function provides `O(log n)` performance for [Component] retrieval. It is recommended that retrieving
 * a [Component] is done using [get] consuming a [ComponentMapper].
 *
 * @param T the [Component] type to search for.
 * @return the specified [Component]. Otherwise `null` if the [Entity] does not have it.
 * @see ComponentMapper
 */
inline fun <reified T : Component> Entity.get(): T? = this.getComponent(T::class.java)

/**
 * Checks whether the [Entity] has the specified [Component].
 *
 * @param T the [Component] type to inspect.
 * @param mapper the [ComponentMapper] to check the [Component] with.
 * @return `true` if the [Entity] has the specified component, and `false` otherwise.
 * @see ComponentMapper.has
 */
inline fun <T : Component> Entity.has(mapper: ComponentMapper<T>): Boolean = mapper.has(this)

/**
 * Checks whether the entity created with this ID has the specified [Component].
 *
 * @param T the [Component] type to inspect.
 * @param mapper the [ComponentMapper] to check the [Component] with.
 * @return `true` if the [Entity] has the specified component, and `false` otherwise.
 * @see ComponentMapper.has
 */
inline fun<T: Component> Int.has(mapper: ComponentMapper<T>): Boolean = mapper.has(this)

/**
 * Checks whether the [Entity] does not have the specified [Component].
 *
 * @param T the [Component] type to inspect.
 * @param mapper the [ComponentMapper] to check the [Component] with.
 * @return `true` if the [Entity] does not have the specified component, and `false` otherwise.
 * @see ComponentMapper.has
 */
inline fun <T : Component> Entity.hasNot(mapper: ComponentMapper<T>): Boolean = !has(mapper)

/**
 * Checks whether the entity created with this ID does not have the specified [Component].
 *
 * @param T the [Component] type to inspect.
 * @param mapper the [ComponentMapper] to check the [Component] with.
 * @return `true` if the [Entity] does not have the specified component, and `false` otherwise.
 * @see ComponentMapper.has
 */
inline fun <T : Component> Int.hasNot(mapper: ComponentMapper<T>): Boolean = !has(mapper)

/**
 * Checks whether the [Entity] has the specified [Component].
 *
 * @param T the [Component] type to inspect.
 * @param mapper the [ComponentMapper] to check the [Component] with.
 * @return `true` if the [Entity] has the specified component, and `false` otherwise.
 * @see ComponentMapper.has
 */
operator fun <T : Component> Entity.contains(mapper: ComponentMapper<T>): Boolean = mapper.has(this)

/**
 * Checks whether the entity created with this ID has the specified [Component].
 *
 * @param T the [Component] type to inspect.
 * @param mapper the [ComponentMapper] to check the [Component] with.
 * @return `true` if the [Entity] has the specified component, and `false` otherwise.
 * @see ComponentMapper.has
 */
inline operator fun <T : Component> Int.contains(mapper: ComponentMapper<T>): Boolean = mapper.has(this)

/**
 * Adds a [Component] to this [Entity]. If a [Component] of the same type already exists, it will be replaced.
 *
 * @param T type of component to add. Must have a no-argument constructor.
 * @return a [Component] instance of the chosen type.
 * @throws [CreateComponentException] if the engine was unable to create the component.
 * @see EntityEdit.with
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T : Component> Entity.add(configure: (@ArtemisDsl T).() -> Unit = {}): T {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  val component = edit().with<T>()
  component.configure()
  return component
}

@OptIn(ExperimentalContracts::class)
inline fun <reified T : Component> Entity.add(
  mapper: ComponentMapper<T>,
  configure: (@ArtemisDsl T).() -> Unit = {}
): T {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  val component = mapper.create(id)
  component.configure()
  return component
}

@OptIn(ExperimentalContracts::class)
inline fun <reified T : Component> EntityEdit.add(configure: (@ArtemisDsl T).() -> Unit = {}): T {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  val component = with<T>()
  component.configure()
  return component
}

@OptIn(ExperimentalContracts::class)
inline fun <reified T : Component> EntityEdit.add(
  mapper: ComponentMapper<T>,
  configure: (@ArtemisDsl T).() -> Unit = {}
): T {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  val component = mapper.create(entityId)
  component.configure()
  return component
}

@OptIn(ExperimentalContracts::class)
inline fun <reified T : Component> ComponentMapper<T>.create(
  entityId: Int,
  configure: (@ArtemisDsl T).() -> Unit = {}
): T {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  val component = this.create(entityId)
  component.configure()
  return component
}


inline fun <reified T : Component> EntityEdit.remove(
  mapper: ComponentMapper<T>
) = mapper.remove(entityId)

@OptIn(ExperimentalContracts::class)
inline fun Entity.edit(configure: EntityEdit.() -> Unit): Entity {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  edit().configure()
  return this
}

/**
 * Adds a [Component] to the entity created with this ID in desired [World]. If a [Component] of the same type already exists, it will be replaced.
 *
 * @param T type of component to add. Must have a no-argument constructor.
 * @return a [Component] instance of the chosen type.
 * @throws [CreateComponentException] if the engine was unable to create the component.
 * @see EntityEdit.with
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T : Component> Int.add(mapper: ComponentMapper<T>, configure: (@ArtemisDsl T).() -> Unit = {}): T {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  val component = mapper.create(this)
  component.configure()
  return component
}

/**
 * Removes a [Component] from this [Entity]. Does nothing if already removed or absent.
 *
 * @param T type of component to remove.
 * @param mapper the [ComponentMapper] to check the [Component] with
 * @see EntityEdit.with
 */
inline fun<T : Component> Entity.remove(mapper: ComponentMapper<T>) {
  mapper.remove(this)
}

/**
 * Removes a [Component] from the entity created with this ID in desired [World]. Does nothing if already removed or absent.
 *
 * @param T type of component to remove.
 * @param mapper the [ComponentMapper] to check the [Component] with
 * @see EntityEdit.with
 */
inline fun<T : Component> Int.remove(mapper: ComponentMapper<T>) {
  mapper.remove(this)
}

/**
 * A builder function for [EntityTransmuter]. Do not invoke [build()] inside lambda because it will be invoked inside the builder function.
 *
 */
@OptIn(ExperimentalContracts::class)
inline fun transmuter(world: World, configure: (@ArtemisDsl EntityTransmuterFactory).() -> Unit = {}): EntityTransmuter {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  return EntityTransmuterFactory(world).apply(configure).build()
}

operator fun EntityTransmuter.invoke(entityId: Int) = this.transmute(entityId)

operator fun EntityTransmuter.invoke(entity: Entity) = this.transmute(entity)

inline fun<reified T : Component> EntityTransmuterFactory.add() {
  this.add(T::class.java)
}

inline fun<reified T : Component> EntityTransmuterFactory.remove() {
  this.remove(T::class.java)
}

