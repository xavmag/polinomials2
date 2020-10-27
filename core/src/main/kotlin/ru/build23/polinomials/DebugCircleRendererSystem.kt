package ru.build23.polinomials

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.graphics.circle
import ru.build23.polinomials.artemis.IteratingDslSystem
import ru.build23.polinomials.artemis.all
import ru.build23.polinomials.artemis.get
import ru.build23.polinomials.components.Circle
import ru.build23.polinomials.components.Color
import ru.build23.polinomials.components.Position

class DebugCircleRendererSystem : IteratingDslSystem(
  all(Circle::class, Position::class, Color::class)
) {

  @Wire
  lateinit var shapeRenderer: ShapeRenderer

  lateinit var mCircle: ComponentMapper<Circle>
  lateinit var mPosition: ComponentMapper<Position>
  lateinit var mColor: ComponentMapper<Color>

  override fun process(entityId: Int) {
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
    shapeRenderer.color = entityId[mColor].value
    shapeRenderer.circle(entityId[mPosition].vec, entityId[mCircle].radius)
    shapeRenderer.end()
  }
}