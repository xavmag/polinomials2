package ru.build23.polinomials

import com.artemis.Entity
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.graphics.Color as GdxColor
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.DragListener
import ktx.math.minus
import ktx.math.times
import ru.build23.polinomials.Global.mActor
import ru.build23.polinomials.Global.mBezierCurve
import ru.build23.polinomials.Global.mBezierPrimitive
import ru.build23.polinomials.Global.mCamera
import ru.build23.polinomials.Global.mCircle
import ru.build23.polinomials.Global.mColor
import ru.build23.polinomials.Global.mCurveControlPoint
import ru.build23.polinomials.Global.mCurvePoints
import ru.build23.polinomials.Global.mPosition
import ru.build23.polinomials.Global.mRect
import ru.build23.polinomials.Global.mSize
import ru.build23.polinomials.Global.mVelocity
import ru.build23.polinomials.Global.mode
import ru.build23.polinomials.Global.stage
import ru.build23.polinomials.artemis.*

fun Entity.camera(): Entity {
  add(mCamera)
  add(mPosition)
  add(mVelocity)
  return this
}

fun Entity.bezier(color: GdxColor = GdxColor.ORANGE): Entity {
  add(mBezierCurve)
  add(mColor) {
    value = color
  }
  return this
}

fun Entity.deleteCurvePoint(x: Float, y: Float) {
  if (has(mBezierCurve)) {
    val curve = get(mBezierCurve)

  }
}

fun Entity.bezierCurvePoint(x: Float, y: Float): Entity {
  if (has(mBezierCurve)) {
    val curveColor = get(mColor).value
    if (get(mBezierCurve).curveNodes.isNotEmpty) {
      val last = get(mBezierCurve).curveNodes.peek()
      val lastPos = last[mCurveControlPoint].vec
      val new = world.entity {
        add(mBezierPrimitive)
        add(mColor) {
          value = curveColor
        }
        add(mCurvePoints)
      }
      val bezier = new[mBezierPrimitive]
      bezier.controlPoints[0] = last
      repeat(2) {
        bezier.controlPoints[it + 1] = world.entity {
          val cp = add(mCurveControlPoint) {
            vec.set(MathUtils.lerp(lastPos.x, x, 0.333f + 0.333f * it), MathUtils.lerp(lastPos.y, y, 0.333f + 0.333f * it))
          }
          add(mRect)
          val size = add(mSize) {
            value.set(10f, 10f)
          }
          val color = add(mColor) {
            value = GdxColor.BLUE
          }
          val pos = add(mPosition) {
            vec.set(cp.vec - size.value * 0.5f)
          }
          add(mActor) {
            value = Actor().apply {
              setSize(10f, 10f)
              setPosition(cp.vec.x , cp.vec.y - height * 0.5f)
              addListener(object : DragListener() {
                override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int) {
                  if (mode == Mode.Edit)
                    color.value = GdxColor.CYAN
                }

                override fun drag(event: InputEvent, x: Float, y: Float, pointer: Int) {
                  if (mode == Mode.Edit) {
                    moveBy(x - width * 0.5f, y - height * 0.5f)
                    cp.vec.add(x - width * 0.5f, y - height * 0.5f)
                    pos.vec.add(x - width * 0.5f, y - height * 0.5f)
                  }
                }

                override fun dragStop(event: InputEvent, x: Float, y: Float, pointer: Int) {
                  if (mode == Mode.Edit)
                    color.value = GdxColor.BLUE
                }
              })
            }
            stage.addActor(value)
          }
        }.id
      }
      val tp = terminalPoint(x, y)
      bezier.controlPoints[3] = tp
      get(mBezierCurve).curveNodes.add(tp)
    } else {
      get(mBezierCurve).curveNodes.add(terminalPoint(x, y))
    }
  }
  return this
}

private fun Entity.terminalPoint(x: Float, y: Float): Int {
  return world.entity {
    val cp = add(mCurveControlPoint) {
      vec.set(x, y)
    }
    val c = add(mCircle) {
      radius = 5f
    }
    val color = add(mColor) {
      value = GdxColor.OLIVE
    }
    val pos = add(mPosition) {
      vec.set(x, y)
    }
    add(mActor) {
      value = Actor().apply {
        setSize(c.radius * 2f, c.radius * 2f)
        setPosition(cp.vec.x - c.radius, cp.vec.y - c.radius)
        addListener(object : DragListener() {
          override fun dragStart(event: InputEvent, x: Float, y: Float, pointer: Int) {
            if (mode == Mode.Edit)
              color.value = GdxColor.GOLD
          }

          override fun drag(event: InputEvent, x: Float, y: Float, pointer: Int) {
            if (mode == Mode.Edit) {
              moveBy(x - width * 0.5f, y - height * 0.5f)
              cp.vec.add(x - width * 0.5f, y - height * 0.5f)
              pos.vec.add(x - width * 0.5f, y - height * 0.5f)
            }
          }

          override fun dragStop(event: InputEvent, x: Float, y: Float, pointer: Int) {
            if (mode == Mode.Edit)
              color.value = GdxColor.OLIVE
          }
        })
      }
      stage.addActor(value)
    }
  }.id
}