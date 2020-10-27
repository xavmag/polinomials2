package ru.build23.polinomials

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ru.build23.polinomials.artemis.IteratingDslSystem
import ru.build23.polinomials.artemis.all
import ru.build23.polinomials.artemis.get
import ru.build23.polinomials.components.Color
import ru.build23.polinomials.components.Line

class DebugLineRendererSystem : IteratingDslSystem(
  all(Line::class, Color::class)
) {

  lateinit var mColor: ComponentMapper<Color>
  lateinit var mLine: ComponentMapper<Line>

  @Wire
  lateinit var shapeRenderer: ShapeRenderer

  override fun process(entityId: Int) {
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
    shapeRenderer.color = entityId[mColor].value
    val l = entityId[mLine]
    shapeRenderer.line(l.v0, l.v1)
    shapeRenderer.end()
  }
}