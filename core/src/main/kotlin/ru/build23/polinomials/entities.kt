package ru.build23.polinomials

import com.artemis.Entity
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import ru.build23.polinomials.Global.mActor
import ru.build23.polinomials.Global.mCamera
import ru.build23.polinomials.Global.mColor
import ru.build23.polinomials.Global.mCurveControlPoint
import ru.build23.polinomials.Global.mCurvePoints
import ru.build23.polinomials.Global.mKnots
import ru.build23.polinomials.Global.mNurbs
import ru.build23.polinomials.Global.mPosition
import ru.build23.polinomials.Global.mVelocity
import ru.build23.polinomials.artemis.add
import ru.build23.polinomials.artemis.entity
import ru.build23.polinomials.artemis.get
import ru.build23.polinomials.artemis.has

fun Entity.camera(): Entity {
  add(mCamera)
  add(mPosition)
  add(mVelocity)
  return this
}

fun Entity.nurbs(degree: Int): Entity {
  add(mNurbs) {
    this.degree = degree
  }
  add(mCurvePoints)
  add(mColor) {
    value = Color.MAGENTA
  }
  add(mKnots)
  return this
}

fun Entity.knots(vararg knots: Float): Entity {
  if (has(mNurbs)) {
    add(mKnots) {
      values.addAll(*knots)
    }
  }
  return this
}

fun Entity.controlPoint(x: Float, y: Float): Entity {
  if (has(mNurbs)) {
    val nurbs = get(mNurbs)
    nurbs.controlPoints.add(world.entity {
      val cp = add(mCurveControlPoint) {
        vec.set(x, y)
      }
      add(mActor) {
        value = Actor().apply {
          setSize(50f, 50f)
          setPosition(cp.vec.x - 50f, cp.vec.y - 50f)
          debug = true
          addListener(object : DragListener() {
            override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int) {

            }

            override fun drag(event: InputEvent, x: Float, y: Float, pointer: Int) {
              setPosition(x, y)
            }

            override fun dragStop(event: InputEvent, x: Float, y: Float, pointer: Int) {

            }
          })
        }
      }
    }.id)
    val knots = get(mKnots).values
    knots.clear()
    val size = nurbs.controlPoints.size() + nurbs.degree
    repeat(size) {
      knots.add(it.toFloat() / size)
    }
  }
  return this
}