package ru.build23.polinomials

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.scenes.scene2d.Stage
import ru.build23.polinomials.components.*

object Global {
  const val EPSILON = 1E-9
  var CURVE_ITERATIONS = 50

  lateinit var mCamera: ComponentMapper<Camera>
  lateinit var mPosition: ComponentMapper<Position>
  lateinit var mVelocity: ComponentMapper<Velocity>
  lateinit var mNurbs: ComponentMapper<Nurbs>
  lateinit var mBezierPrimitive: ComponentMapper<BezierPrimitive>
  lateinit var mBezierCurve: ComponentMapper<BezierCurve>
  lateinit var mColor: ComponentMapper<Color>
  lateinit var mKnots: ComponentMapper<CurveKnots>
  lateinit var mCurvePoints: ComponentMapper<CurvePoints>
  lateinit var mCurveControlPoint: ComponentMapper<CurveControlPoint>
  lateinit var mActor: ComponentMapper<Actor>
  lateinit var mCircle: ComponentMapper<Circle>
  lateinit var mRect: ComponentMapper<Rect>
  lateinit var mSize: ComponentMapper<Size>

  @Wire
  lateinit var stage: Stage

  var mode = Mode.Create
}