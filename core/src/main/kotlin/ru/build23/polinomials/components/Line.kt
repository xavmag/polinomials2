package ru.build23.polinomials.components

import com.artemis.PooledComponent
import ktx.math.vec2

class Line : PooledComponent() {
  val v0 = vec2()
  val v1 = vec2()

  override fun reset() {
    v0.set(0f, 0f)
    v1.set(0f, 0f)
  }
}