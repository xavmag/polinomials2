package ru.build23.polinomials

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ktx.graphics.rect
import ru.build23.polinomials.artemis.IteratingDslSystem
import ru.build23.polinomials.artemis.all
import ru.build23.polinomials.artemis.get
import ru.build23.polinomials.components.Color
import ru.build23.polinomials.components.Position
import ru.build23.polinomials.components.Rect
import ru.build23.polinomials.components.Size

class DebugRectRendererSystem : IteratingDslSystem(
  all(Rect::class, Position::class, Size::class, Color::class)
) {

  lateinit var mColor: ComponentMapper<Color>
  lateinit var mPosition: ComponentMapper<Position>
  lateinit var mSize: ComponentMapper<Size>

  @Wire
  lateinit var shapeRenderer: ShapeRenderer

  override fun process(entityId: Int) {
    shapeRenderer.set(ShapeRenderer.ShapeType.Line)
    shapeRenderer.color = entityId[mColor].value
    val pos = entityId[mPosition].value
    val size = entityId[mSize].value
    shapeRenderer.rect(pos, size)
  }
}