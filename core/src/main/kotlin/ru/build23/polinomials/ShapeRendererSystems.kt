package ru.build23.polinomials

import com.artemis.annotations.Wire
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ru.build23.polinomials.artemis.BaseDslSystem

class ShapeRendererBeginSystem : BaseDslSystem() {

  @Wire
  lateinit var shapeRenderer: ShapeRenderer

  @Wire(name = "world_camera")
  lateinit var camera: OrthographicCamera

  override fun processSystem() {
    shapeRenderer.projectionMatrix = camera.combined
    shapeRenderer.begin()
  }
}


class ShapeRendererEndSystem : BaseDslSystem() {

  @Wire
  lateinit var shapeRenderer: ShapeRenderer

  override fun processSystem() {
    shapeRenderer.end()
  }
}