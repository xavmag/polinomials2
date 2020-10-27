package ru.build23.polinomials

import com.artemis.annotations.Wire
import com.badlogic.gdx.scenes.scene2d.Stage
import ru.build23.polinomials.artemis.BaseDslSystem

class StageRendererSystem : BaseDslSystem() {

  @Wire
  lateinit var stage: Stage

  override fun processSystem() {
    stage.viewport.apply()
    stage.act()
    stage.draw()
  }
}