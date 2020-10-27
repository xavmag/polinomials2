package ru.build23.polinomials

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class CustomApplicationListener : ApplicationAdapter() {
  private lateinit var image: Texture
  private lateinit var batch: SpriteBatch

  override fun create() {
    image = Texture("badlogic.jpg")
  }
}