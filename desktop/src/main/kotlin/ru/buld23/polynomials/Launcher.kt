package ru.buld23.polynomials

import ru.build23.polinomials.CustomApplicationListener
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

object Launcher {
  @JvmStatic
  fun main(args: Array<String>) {
    val cfg = LwjglApplicationConfiguration()
    cfg.width = 640
    cfg.height = 480
    LwjglApplication(DesktopApplicationListener(), cfg)
  }
}