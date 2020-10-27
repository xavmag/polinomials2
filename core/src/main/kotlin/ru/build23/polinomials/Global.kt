package ru.build23.polinomials

import com.artemis.ComponentMapper
import ru.build23.polinomials.components.*

object Global {
  const val EPSILON = 1E-9
  var NURBS_ITERATIONS = 100

  lateinit var mCamera: ComponentMapper<Camera>
  lateinit var mPosition: ComponentMapper<Position>
  lateinit var mVelocity: ComponentMapper<Velocity>
  lateinit var mNurbs: ComponentMapper<Nurbs>
  lateinit var mColor: ComponentMapper<Color>
  lateinit var mKnots: ComponentMapper<CurveKnots>
  lateinit var mCurvePoints: ComponentMapper<CurvePoints>
  lateinit var mCurveControlPoint: ComponentMapper<CurveControlPoint>
  lateinit var mActor: ComponentMapper<Actor>
}