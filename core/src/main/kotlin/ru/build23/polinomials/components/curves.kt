package ru.build23.polinomials.components

import com.artemis.Component
import com.artemis.annotations.EntityId
import com.artemis.utils.IntBag
import ktx.collections.GdxFloatArray
import ktx.math.vec2

class Nurbs : Component() {
  var degree: Int = 2
  set(value) {
    field = value.coerceIn(2, 16)
  }
  @EntityId
  val controlPoints = IntBag(4)
}

class BezierCurve : Component() {
  @EntityId
  val curveNodes = IntBag(4)
}

class BezierPrimitive : Component() {
  val controlPoints = IntArray(4)
}

class CurvePoints : Component() {
  val values = GdxFloatArray()
}

class CurveKnots : Component() {
  val values = GdxFloatArray()
}

class CurveWeights : Component() {
  val values = GdxFloatArray()
}

class CurveControlPoint : Component() {
  var vec = vec2()
}