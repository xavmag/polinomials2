package ru.build23.polinomials

import com.artemis.ComponentMapper
import com.badlogic.gdx.math.Vector2
import ktx.math.vec2
import ru.build23.polinomials.artemis.IteratingDslSystem
import ru.build23.polinomials.artemis.all
import ru.build23.polinomials.artemis.exclude
import ru.build23.polinomials.artemis.get
import ru.build23.polinomials.components.*

class BezierComposerSystem : IteratingDslSystem(
  all(BezierPrimitive::class, CurvePoints::class).exclude(Nurbs::class)
) {
  lateinit var mPoints: ComponentMapper<CurvePoints>
  lateinit var mCp: ComponentMapper<CurveControlPoint>
  lateinit var mBezierPrimitive: ComponentMapper<BezierPrimitive>


  override fun process(entityId: Int) {
    val points = entityId[mPoints].values
    val bezier = entityId[mBezierPrimitive]
    val cp = bezier.controlPoints
    val out = vec2()
    val tmp = vec2()
    points.clear()
    repeat(Global.CURVE_ITERATIONS) {
      val v = curve(
        out,
        cp[0][mCp].vec,
        cp[1][mCp].vec,
        cp[2][mCp].vec,
        cp[3][mCp].vec,
        it.toFloat() / Global.CURVE_ITERATIONS,
        tmp
      )
      points.add(v.x, v.y)
    }
    val v = curve(out, cp[0][mCp].vec, cp[1][mCp].vec, cp[2][mCp].vec, cp[3][mCp].vec, 1f, tmp)
    points.add(v.x, v.y)
  }

  private fun curve(out: Vector2, p0: Vector2, p1: Vector2, p2: Vector2, p3: Vector2, t: Float, tmp: Vector2): Vector2 {
    // B3(t) = (1-t) * (1-t) * (1-t) * p0 + 3 * (1-t) * (1-t) * t * p1 + 3 * (1-t) * t * t * p2 + t * t * t * p3
    val dt: Float = 1f - t
    val dt2 = dt * dt
    val t2: Float = t * t
    return out.set(p0).scl(dt2 * dt).add(tmp.set(p1).scl(3 * dt2 * t)).add(tmp.set(p2).scl(3 * dt * t2))
      .add(tmp.set(p3).scl(t2 * t))
  }

  private fun derivative(out: Vector2, p0: Vector2, p1: Vector2, p2: Vector2, p3: Vector2, t: Float, tmp: Vector2): Vector2 {
    // B3'(t) = 3 * (1-t) * (1-t) * (p1 - p0) + 6 * (1 - t) * t * (p2 - p1) + 3 * t * t * (p3 - p2)
    val dt: Float = 1f - t
    val dt2 = dt * dt
    val t2: Float = t * t
    return out.set(p1).sub(p0).scl(dt2 * 3).add(tmp.set(p2).sub(p1).scl(dt * t * 6))
      .add(tmp.set(p3).sub(p2).scl(t2 * 3))
  }
}