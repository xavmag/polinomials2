package ru.build23.polinomials.artemis

import com.artemis.*
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.GdxRuntimeException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KClass

/**
 * Create a [Component] by calling [EntityEdit.create].
 *
 * The [Component] must have a visible no-arg constructor.
 *
 * @param T the type of [Component] to get or create.
 * @param configure inlined function with [T] as the receiver to allow further configuration.
 * @return an [Component] instance of the selected type.
 * @throws [CreateComponentException] if the world was unable to create the component
 */
@OptIn(ExperimentalContracts::class)
inline fun <reified T : Component> EntityEdit.with(configure: T.() -> Unit = {}): T {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  return try {
    create(T::class.java) ?: throw NullPointerException("The component of ${T::class.java} type is null.")
  } catch (exception: Throwable) {
    throw CreateComponentException(T::class, exception)
  }.apply(configure)
}

@OptIn(ExperimentalContracts::class)
inline fun World.edit(id: Int, configure: EntityEdit.() -> Unit = {}): EntityEdit {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  return this.edit(id).apply { configure() }
}

/**
 * Create and add an [Entity] to the [World].
 *
 * @param configure inlined function with the created [Entity] as the receiver to allow further configuration of
 * the [Entity]. The [Entity] holds the [Entity] created and the [World] that created it.
 * @return the created [Entity].
 */
@OptIn(ExperimentalContracts::class)
inline fun World.entity(configure: EntityEdit.() -> Unit): Entity {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  val entity = createEntity()
  entity.edit().configure()
  return entity
}


inline fun World.entity(): Entity {
  return createEntity()
}

/**
 * Create and add an entity to the [World].
 *
 * @param configure inlined function with the created [Entity] as the receiver to allow further configuration of
 * the [Entity]. The [Entity] holds the [Entity] created and the [World] that created it.
 * @return the ID of created entity.
 */
@OptIn(ExperimentalContracts::class)
inline fun World.entityId(configure: EntityEdit.() -> Unit): Int {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  val entity = create()
  edit(entity).configure()
  return entity
}

inline fun World.entityId(): Int {
  return create()
}

/**
 * @param T type of the system to retrieve.
 * @return the [BaseSystem] of the given type.
 * @throws MissingSystemException if no system under [T] type is registered.
 * @see World.getSystem
 */
inline fun <reified T : BaseSystem> World.getSystem(): T =
  getSystem(T::class.java) ?: throw MissingSystemException(T::class)

inline fun <reified T : BaseSystem> World.enable(): T = getSystem(T::class.java).apply { isEnabled = true }

inline fun <reified T : BaseSystem> World.disable(): T = getSystem(T::class.java).apply { isEnabled = false }

/**
 * @param type type of the system to retrieve.
 * @return the [BaseSystem] of the given type. May be null if it does not exist.
 * @see World.getSystem
 */
operator fun <T : BaseSystem> World.get(type: KClass<T>): T? = getSystem(type.java)

/**
 * Builder function for [World].
 *
 * @param configure inlined function with *this* [WorldConfigurationBuilder] as the receiver to allow further configuration.
 */
@OptIn(ExperimentalContracts::class)
inline fun ecsWorld(configure: (@ArtemisDsl WorldConfigurationBuilder).() -> Unit): World {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  val setup = WorldConfigurationBuilder().apply{
    configure()
  }.build()
  return World(setup)
}

@OptIn(ExperimentalContracts::class)
inline fun worldConfiguration(configure: (@ArtemisDsl WorldConfigurationBuilder).() -> Unit): WorldConfiguration {
  contract { callsInPlace(configure, InvocationKind.EXACTLY_ONCE) }
  return WorldConfigurationBuilder().apply {
    configure()
  }.build()
}

fun WorldConfiguration.registerAll(vararg entries: Any): WorldConfiguration {
  for (e in entries) {
    register(e)
  }
  return this
}

class KtxWorld : World, Disposable {
  constructor() : super()
  constructor(config: WorldConfiguration) : super(config)

  override fun dispose() {
    super.dispose()
  }
}

/**
 * Thrown when unable to create a component of given type.
 */
class CreateComponentException(type: KClass<*>, cause: Throwable? = null) : RuntimeException(
  "Could not create component ${type.javaObjectType} - is a visible no-arg constructor available?", cause
)

/**
 * Thrown when accessing an [BaseSystem] via [getSystem] that does not exist in the [World].
 */
class MissingSystemException(type: KClass<out BaseSystem>) : GdxRuntimeException(
  "Could not access system of type ${type.qualifiedName} - is it added to the world?"
)
