package ru.build23.polinomials

import com.artemis.ComponentMapper
import com.artemis.utils.IntBag
import com.badlogic.gdx.math.Vector2
import ktx.collections.GdxFloatArray
import ktx.math.vec2
import ru.build23.polinomials.artemis.*
import ru.build23.polinomials.components.*

class NurbsComposerSystem : IteratingDslSystem(
  all(Nurbs::class, CurveKnots::class)
) {
  lateinit var mNurbs: ComponentMapper<Nurbs>
  lateinit var mPoints: ComponentMapper<CurvePoints>
  lateinit var mControlPoint: ComponentMapper<CurveControlPoint>
  lateinit var mWeights: ComponentMapper<CurveWeights>
  lateinit var mKnots: ComponentMapper<CurveKnots>

  override fun process(entityId: Int) {
    val points = entityId[mPoints].values
    val nurbs = entityId[mNurbs]
    repeat(Global.CURVE_ITERATIONS) {
      val v = curvePoint(nurbs.degree, entityId[mKnots].values, nurbs.controlPoints, it.toFloat() / Global.CURVE_ITERATIONS)
      points.add(v.x, v.y)
    }
    val v = curvePoint(nurbs.degree, entityId[mKnots].values, nurbs.controlPoints, 1f)
    points.add(v.x, v.y)
  }

  private fun curvePoint(degree: Int, knots: GdxFloatArray, cps: IntBag, param: Float): Vector2 {
    val point = vec2()

    val span = findSpan(degree, knots, param)
    val n = bSplineBasis(degree, span, knots, param)

    repeat(degree) {
      val v = cps[span - degree + it][mControlPoint].vec
      point.add(n[it] * v.x, n[it] * v.y)
    }
    return point
  }

  private fun findSpan(degree: Int, knots: GdxFloatArray, param: Float): Int {
    val n = knots.size - degree - 2

    if (param > (knots[n+1] - Global.EPSILON)) {
      return n
    }
    if (param < (knots[degree] + Global.EPSILON)) {
      return degree
    }

    // Двоичный поиск
    var low = degree
    var high = n + 1
    var mid = ((low + high) / 2.0f).toInt()
    while (param < knots[mid] || param >= knots[mid + 1] ) {
      if (param < knots[mid] ) {
        high = mid
      } else {
        low = mid
      }
      mid = ((low + high) / 2.0f).toInt()
    }
    return mid
  }

  private fun bSplineBasis(degree: Int, span: Int, knots: GdxFloatArray, param: Float): FloatArray {
    val n = FloatArray(degree + 1) { 0f }
    val left = FloatArray(degree + 1) { 0f }
    val right = FloatArray(degree + 1) { 0f }
    var saved: Float
    var tmp: Float

    repeat(degree) {
      left[it] = (param - knots[span + 1 - it])
      right[it] = knots[span + it] - param
      saved = 0f
      repeat(it) { j ->
        tmp = n[j] / (right[j + 1] + left[it - j])
        n[j] = saved + right[j + 1] * tmp
        saved = left[it - j] * tmp
      }
      n[it] = saved
    }
    return n
  }
}