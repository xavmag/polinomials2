package ru.build23.polinomials.artemis

import com.artemis.*
import com.artemis.systems.IteratingSystem

abstract class BaseDslSystem : BaseSystem() {
  inline fun entity(configure: EntityEdit.() -> Unit): Entity {
    val entity = `access$world`.createEntity()
    entity.edit().configure()
    return entity
  }
  
  
  protected fun entity(): Entity {
    return world.createEntity()
  }

  @PublishedApi
  internal var `access$world`: World
    get() = world
    set(value) {
      world = value
    }

  protected inline fun transmuter(configure: (EntityTransmuterFactory).() -> Unit = {}): EntityTransmuter {
    return EntityTransmuterFactory(world).apply(configure).build()
  }
}

abstract class IteratingDslSystem : IteratingSystem {
  protected inline fun entity(configure: EntityEdit.() -> Unit): Entity {
    val entity = world.createEntity()
    entity.edit().configure()
    return entity
  }

  protected fun entity(): Entity {
    return world.createEntity()
  }
  constructor()

  constructor(aspect: Aspect.Builder) : super(aspect)

  protected inline fun transmuter(configure: (EntityTransmuterFactory).() -> Unit = {}): EntityTransmuter {
    return EntityTransmuterFactory(world).apply(configure).build()
  }
}

abstract class UtilitySystem : BaseDslSystem() {
  final override fun checkProcessing(): Boolean {
    return false
  }

  override fun initialize() {
    isEnabled = false
  }

  final override fun processSystem() {
  }
}