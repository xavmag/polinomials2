package ru.build23.polinomials

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ru.build23.polinomials.artemis.IteratingDslSystem
import ru.build23.polinomials.artemis.all
import ru.build23.polinomials.artemis.get
import ru.build23.polinomials.components.Color
import ru.build23.polinomials.components.CurvePoints

class CurveRendererSystem : IteratingDslSystem(
  all(CurvePoints::class, Color::class)
) {

  lateinit var mPoints: ComponentMapper<CurvePoints>
  lateinit var mColor: ComponentMapper<Color>

  @Wire
  lateinit var shapeRenderer: ShapeRenderer

  override fun process(entityId: Int) {
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
    shapeRenderer.color = entityId[mColor].value
    val values = entityId[mPoints].values.toArray()
    shapeRenderer.polyline(values)
    shapeRenderer.end()
  }
}