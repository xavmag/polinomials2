package ru.buld23.polynomials

import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import ktx.scene2d.*
import ktx.scene2d.Scene2DSkin.defaultSkin

@Scene2dDsl
class KCanvas(
  styleName: String,
  skin: Skin,
  fbo: FrameBuffer
) : Table(skin), KTable {

  init {
    val style = skin[styleName, KCanvas::class.java]
  }
}

@Scene2dDsl
inline fun <S> KWidget<S>.canvas(
  style: String = defaultStyle,
  skin: Skin = defaultSkin,
  fbo: FrameBuffer,
  init: KCanvas.(S) -> Unit = {}
) : KCanvas = actor(KCanvas(style, skin, fbo), init)