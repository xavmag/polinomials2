package ru.build23.polinomials

import com.artemis.annotations.Wire
import com.badlogic.gdx.graphics.OrthographicCamera
import ru.build23.polinomials.artemis.BaseDslSystem

class CameraSystem : BaseDslSystem() {

  @Wire(name = "scene_camera")
  lateinit var sceneCamera: OrthographicCamera

  @Wire(name = "world_camera")
  lateinit var worldCamera: OrthographicCamera

  override fun processSystem() {
    sceneCamera.update()
    worldCamera.update()
  }
}