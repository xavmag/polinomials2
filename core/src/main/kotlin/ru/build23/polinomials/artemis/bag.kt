package ru.build23.polinomials.artemis

import com.artemis.utils.Bag
import com.artemis.utils.IntBag
import ktx.collections.defaultArraySize

val IntBag.size: Int
  inline get() = this.size()

val<E> Bag<E>.size: Int
  inline get() = this.size()

inline fun <reified Type : Any> bagOf(initialCapacity: Int = defaultArraySize): Bag<Type> =
  Bag(Type::class.java, initialCapacity)

inline fun <Type : Any> bagOf(vararg elements: Type): Bag<Type> {
  val bag = Bag<Type>()
  for (e in elements) {
    bag.add(e)
  }
  return bag
}

val IntBag.isNotEmpty: Boolean
  inline get() = !isEmpty

fun IntBag.peek(): Int = data[size - 1]

val <Type : Any> Bag<Type>.isNotEmpty: Boolean
  inline get() = !isEmpty

fun <Type : Any> Bag<Type>.peek(): Type = data[size - 1]