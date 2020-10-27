package ru.buld23.polynomials

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.kotcrab.vis.ui.VisUI
import ktx.actors.stage
import ktx.scene2d.scene2d
import ktx.scene2d.table
import ktx.scene2d.vis.visLabel
import ru.build23.polinomials.App
import tornadofx.Stylesheet.Companion.label

class DesktopApplicationListener : ApplicationAdapter() {
  private lateinit var batch: SpriteBatch

  private lateinit var app: App

  override fun create() {
    VisUI.load()
    batch = SpriteBatch()
    app = App(batch)
    app.stage.addActor(scene2d.visLabel("Press Q to enter Create mode\nPress E to enter Edit mode") {
      setPosition(25f, 25f)
    })
  }

  override fun resize(width: Int, height: Int) {
    app.resize(width, height)
  }


  override fun render() {
    app.render(Gdx.graphics.deltaTime)
  }

  override fun dispose() {
    VisUI.dispose()
    batch.dispose()
    app.dispose()
  }
}